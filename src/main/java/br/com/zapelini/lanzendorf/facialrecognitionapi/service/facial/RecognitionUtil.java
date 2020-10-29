package br.com.zapelini.lanzendorf.facialrecognitionapi.service.facial;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Foto;
import nu.pattern.OpenCV;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.transferlearning.TransferLearningHelper;
import org.deeplearning4j.zoo.PretrainedType;
import org.deeplearning4j.zoo.ZooModel;
import org.deeplearning4j.zoo.model.VGG16;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor;
import org.nd4j.linalg.factory.Nd4j;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.bytedeco.opencv.global.opencv_core.CV_32SC1;
import static org.bytedeco.opencv.global.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.opencv.global.opencv_imgcodecs.IMREAD_GRAYSCALE;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.cvCvtColor;
import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvLoadImage;

public abstract class RecognitionUtil {

    private static final String PATH_FOTO = "C:\\facial-api\\";
    private static final String PATH_CLASSIFIER = PATH_FOTO + "classifierLBPH.yml";
    private static final String PATH_FOTO_TREINAMENTO = PATH_FOTO + "treinamento\\";

    private static final double fatorEscala = 1.05;
    private static final int minimoVizinhos = 7;
    private static final Size minFaces = new Size(100, 100);
    private static final Size maxFaces = new Size(1080, 1080);
    private static TransferLearningHelper transferLearningHelper;
    private static NativeImageLoader nativeImageLoader;
    private static DataNormalization scaler;

    private static ComputationGraph objComputationGraph;

    private static FaceRecognizer faceRecognizer;

    static {
        try {
            Random rng = new Random(123);
            VGG16 zooModel = VGG16.builder()
                    .numClasses(200)
                    .seed(rng.nextInt())
                    .build();

            OpenCV.loadLocally();
            objComputationGraph = (ComputationGraph) zooModel.initPretrained(PretrainedType.VGGFACE);
            transferLearningHelper = new TransferLearningHelper(objComputationGraph, "pool4");
            nativeImageLoader = new NativeImageLoader(224, 224, 3);
            scaler = new VGG16ImagePreProcessor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void extrairFace(Foto foto) {
        String pathFoto = PATH_FOTO.concat(foto.getNome()).concat(".").concat(foto.getExtensao());
        File file = new File(pathFoto);

        if (!file.exists())
            return;

        CascadeClassifier classifier = new CascadeClassifier();
        classifier.load("./src/main/resources/recognition/haarcascade_frontalface_alt.xml");
        Mat fotoMat = Imgcodecs.imread(pathFoto);

        MatOfRect rostosDetectados = new MatOfRect();
        classifier.detectMultiScale(fotoMat, rostosDetectados, fatorEscala, minimoVizinhos, 0, minFaces, maxFaces);
        System.out.println(String.format("Detected %s faces",
                rostosDetectados.toArray().length));

        File path = new File(PATH_FOTO_TREINAMENTO);

        if (!path.exists())
            path.mkdir();

        // Drawing boxes
        int i = 0;
        for (Rect reconhecido : rostosDetectados.toArray()) {
            i++;
            String nomeFoto = foto.getNome() + "." + foto.getExtensao();

            Mat rostoReconhecido = fotoMat.submat(reconhecido);
            Imgcodecs.imwrite(PATH_FOTO_TREINAMENTO.concat(nomeFoto), rostoReconhecido);
            /* pintar retangulo
            Imgproc.rectangle(
                    fotoMat,                                               // where to draw the box
                    new Point(reconhecido.x, reconhecido.y),                            // bottom left
                    new Point(reconhecido.x + reconhecido.width, reconhecido.y + reconhecido.height), // top right
                    new Scalar(0, 0, 255),
                    3                                                     // RGB colour
            );
             */
        }
        System.out.println("Image Processed");
    }

    public static double[] extrairCaracteristicas(Foto foto) throws IOException {
        return extrairCaracteristicas(getArquivoFisicoTreinado(foto));
    }

    public static double[] extrairCaracteristicas(File file) throws IOException {
        if (!file.exists())
            return null;

        INDArray imageMatrix = nativeImageLoader.asMatrix(file);
        scaler.transform(imageMatrix);

        DataSet objDataSet = new DataSet(imageMatrix, Nd4j.create(new float[]{0, 0}));
        DataSet objFeaturized = transferLearningHelper.featurize(objDataSet);
        INDArray featuresArray = objFeaturized.getFeatures();

        int reshapeDimension = 1;
        for (long dimension : featuresArray.shape()) {
            reshapeDimension *= dimension;
        }

        featuresArray = featuresArray.reshape(1, reshapeDimension);

        return featuresArray.data().asDouble();
    }

    public static void trainPhotos(List<Foto> fotos) {
        int size = 0;
        for (Foto foto : fotos) {
            File file = getArquivoFisicoTreinado(foto);
            if (file.exists())
                size++;
        }

        MatVector photos = new MatVector(size);
        org.bytedeco.opencv.opencv_core.Mat labels = new org.bytedeco.opencv.opencv_core.Mat(size, 1, CV_32SC1);
        IntBuffer labelsBuffer = labels.createBuffer();

        int counter = 0;
        for (Foto foto : fotos) {
            File file = getArquivoFisicoTreinado(foto);
            if (!file.exists())
                continue;
            org.bytedeco.opencv.opencv_core.Mat photo = imread(file.getAbsolutePath(), IMREAD_GRAYSCALE);
            int idP = Integer.parseInt(foto.getAluno().getIdAluno().toString());
            opencv_imgproc.resize(photo, photo, new org.bytedeco.opencv.opencv_core.Size(1920, 1072));

            photos.put(counter, photo);
            labelsBuffer.put(counter, idP);
            counter++;
        }

        faceRecognizer = LBPHFaceRecognizer.create(1, 8, 8, 8, 12);
        faceRecognizer.train(photos, labels);
        faceRecognizer.save(PATH_CLASSIFIER);
    }

    public static long testPhoto(File file) {
        LBPHFaceRecognizer recognizer = LBPHFaceRecognizer.create();
        recognizer.read(PATH_CLASSIFIER);
        IplImage testImage = cvLoadImage(file.getAbsolutePath());
        IplImage greyTestImage = IplImage.create(testImage.width(), testImage.height(), IPL_DEPTH_8U, 1);
        cvCvtColor(testImage, greyTestImage, CV_BGR2GRAY);
        recognizer.setThreshold(80);

        CascadeClassifier classifier = new CascadeClassifier();
        classifier.load("./src/main/resources/recognition/haarcascade_frontalface_alt.xml");
        org.opencv.core.Mat fotoMat = Imgcodecs.imread(file.getAbsolutePath());

        Imgcodecs.imwrite(PATH_FOTO_TREINAMENTO.concat("RECONHECIDO.png"), fotoMat);

        MatOfRect rostosDetectados = new MatOfRect();
        classifier.detectMultiScale(fotoMat, rostosDetectados, fatorEscala, minimoVizinhos, 0, minFaces, maxFaces);
        System.out.println(String.format("Detected %s faces",
                rostosDetectados.toArray().length));

        int id = -1;
        for (Rect reconhecido : rostosDetectados.toArray()) {
            org.opencv.core.Mat rostoReconhecido = fotoMat.submat(reconhecido);
            Imgcodecs.imwrite(PATH_FOTO_TREINAMENTO.concat("RECONHECIDO.png"), rostoReconhecido);
            org.bytedeco.opencv.opencv_core.Mat faceCapturada = imread(PATH_FOTO_TREINAMENTO.concat("RECONHECIDO.png"), IMREAD_GRAYSCALE);

            opencv_imgproc.resize(faceCapturada, faceCapturada, new org.bytedeco.opencv.opencv_core.Size(200, 200));
            IntPointer rotulo = new IntPointer(1);
            DoublePointer confidence = new DoublePointer(1);
            recognizer.predict(faceCapturada, rotulo, confidence);
            id = rotulo.get(0);
        }

        return id;
    }

    public static File getArquivoFisicoTreinado(Foto foto) {
        String pathFoto = PATH_FOTO_TREINAMENTO + foto.getNome() + "." + foto.getExtensao();
        return new File(pathFoto);
    }

    public static List<Long> testMultipleFaces(File file) {
        LBPHFaceRecognizer recognizer = LBPHFaceRecognizer.create();
        recognizer.read(PATH_CLASSIFIER);
        IplImage testImage = cvLoadImage(file.getAbsolutePath());
        IplImage greyTestImage = IplImage.create(testImage.width(), testImage.height(), IPL_DEPTH_8U, 1);
        cvCvtColor(testImage, greyTestImage, CV_BGR2GRAY);
        recognizer.setThreshold(80);

        CascadeClassifier classifier = new CascadeClassifier();
        classifier.load("./src/main/resources/recognition/haarcascade_frontalface_alt.xml");
        org.opencv.core.Mat fotoMat = Imgcodecs.imread(file.getAbsolutePath());

        Imgcodecs.imwrite(PATH_FOTO_TREINAMENTO.concat("RECONHECIDO.png"), fotoMat);

        MatOfRect rostosDetectados = new MatOfRect();
        classifier.detectMultiScale(fotoMat, rostosDetectados, fatorEscala, minimoVizinhos, 0, minFaces, maxFaces);
        System.out.println(String.format("Detected %s faces",
                rostosDetectados.toArray().length));

        List<Long> idAlunos = new ArrayList<>();
        for (Rect reconhecido : rostosDetectados.toArray()) {
            org.opencv.core.Mat rostoReconhecido = fotoMat.submat(reconhecido);
            Imgcodecs.imwrite(PATH_FOTO_TREINAMENTO.concat("RECONHECIDO.png"), rostoReconhecido);
            org.bytedeco.opencv.opencv_core.Mat faceCapturada = imread(PATH_FOTO_TREINAMENTO.concat("RECONHECIDO.png"), IMREAD_GRAYSCALE);

            opencv_imgproc.resize(faceCapturada, faceCapturada, new org.bytedeco.opencv.opencv_core.Size(200, 200));
            IntPointer rotulo = new IntPointer(1);
            DoublePointer confidence = new DoublePointer(1);
            recognizer.predict(faceCapturada, rotulo, confidence);
            idAlunos.add(Long.parseLong(String.valueOf(rotulo.get(0))));
        }

        return idAlunos;
    }
}
