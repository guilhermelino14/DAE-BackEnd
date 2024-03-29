package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllObservacoes",
                query = "SELECT o FROM Observacoes o " // JPQL
        ),
        @NamedQuery(
                name = "getObservacoesBySensorId",
                query = "SELECT o FROM Observacoes o WHERE o.sensor.id = :id" // JPQL
        ),
        @NamedQuery(
                name = "deleteObservacoesBySensorId",
                query = "DELETE FROM Observacoes o WHERE o.sensor.id = :id" // JPQL
        )
})
public class Observacoes {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @NotNull
    private double value;

    @NotNull
    private String medida;

    @NotNull
    private String observacao;


    @NotNull
    private Date data;

    public Observacoes() {
    }

    public Observacoes(Sensor sensor, double value, String medida, String observacao, Date data) {
        this.sensor = sensor;
        this.value = value;
        this.medida = medida;
        this.observacao = observacao;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public String getObservacao() {
        return observacao;
    }

    public Date getData() {
        return data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }
}
