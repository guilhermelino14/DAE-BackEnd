package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import java.io.Serializable;

public class CsvDTO implements Serializable {
    String csv;

    public CsvDTO() {
    }

    public CsvDTO(String csv) {
        this.csv = csv;
    }

    public String getCsv() {
        return csv;
    }

    public void setCsv(String csv) {
        this.csv = csv;
    }
}
