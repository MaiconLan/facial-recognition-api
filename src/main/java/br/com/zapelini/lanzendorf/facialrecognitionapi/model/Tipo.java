package br.com.zapelini.lanzendorf.facialrecognitionapi.model;

public enum Tipo {

    SEMESTRAL,
    ANUAL,
    BIMESTRAL,
    TRIMESTRAL;

    public static Tipo parse(String tipo) {
        for (Tipo value : values()) {
            if(value.name().equals(tipo))
                return value;
        }
        return null;
    }
}
