package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Sensor;

import java.util.Date;

public class ObservacoesDTO {
    @NotNull
    private int id;

    @NotNull
    private String observacao;

    @NotNull
    private Date data;

    private int sensor;

    public ObservacoesDTO() {
    }

    public ObservacoesDTO(int id, String observacao, Date data) {
        this.id = id;
        this.observacao = observacao;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public String getObservacao() {
        return observacao;
    }

    public Date getData() {
        return data;
    }

    public int getSensor() {
        return sensor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setSensor(int sensor) {
        this.sensor = sensor;
    }
}
