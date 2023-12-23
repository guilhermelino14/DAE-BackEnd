package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllObservacoes",
                query = "SELECT o FROM Observacoes o " // JPQL
        )
})
public class Observacoes {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @NotNull
    private String observacao;

    @NotNull
    private Date data;

    public Observacoes() {
    }

    public Observacoes(Sensor sensor, String observacao, Date data) {
        this.sensor = sensor;
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
}
