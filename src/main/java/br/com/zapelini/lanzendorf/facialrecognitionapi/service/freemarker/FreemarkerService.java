package br.com.zapelini.lanzendorf.facialrecognitionapi.service.freemarker;

import br.com.zapelini.lanzendorf.facialrecognitionapi.config.VariavelAmbiente;
import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import com.lowagie.text.DocumentException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Map;

@Service
public class FreemarkerService {

    public String getTemplatePadrao(String arquivo, Map<String, Object> parametros) throws ApiException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        try {
            cfg.setClassForTemplateLoading(FreemarkerService.class, "/freemarker");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);//.RETHROW
            cfg.setClassicCompatible(true);
            Template temp = cfg.getTemplate(arquivo);

            StringWriter stringWriter = new StringWriter();
            temp.process(parametros, stringWriter);

            return stringWriter.toString();
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
            throw new ApiException("Erro ao gerar o arquivo");
        }
    }

    public byte[] gerarArquivo(String arquivo, Map<String, Object> parametros) throws ApiException, IOException, DocumentException {
        String conteudo = getTemplatePadrao(arquivo, parametros);
        String nomeArquivo = VariavelAmbiente.DIRETORIO_TEMPORARIO + "presencas.pdf";

        OutputStream outputStream = new FileOutputStream(nomeArquivo);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(conteudo);
        renderer.layout();
        renderer.createPDF(outputStream, false);
        renderer.finishPDF();

        return FileUtils.readFileToByteArray(new File(nomeArquivo));
    }

}
