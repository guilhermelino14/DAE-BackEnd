package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Table(name = "embalagens")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public class Embalagem {
    @Id  @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String nome;

    @NotNull
    private double altura;
    @NotNull
    private double largura;

    @ManyToMany(mappedBy = "embalagens",  fetch = FetchType.EAGER)
    private List<Sensor> sensores;

    public Embalagem() {
        this.sensores = new ArrayList<>();
    }

    public Embalagem(String nome,double altura, double largura) {
        this.nome = nome;
        this.altura = altura;
        this.largura = largura;
        this.sensores = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public double getAltura() {
        return altura;
    }

    public double getLargura() {
        return largura;
    }

    public int getId() {
        return id;
    }

    public List<Sensor> getSensores() {
        return sensores;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSensores(List<Sensor> sensores) {
        this.sensores = sensores;
    }

    public void addSensor(Sensor sensor) {
        this.sensores.add(sensor);
    }

    public void removeSensor(Sensor sensor) {
        this.sensores.remove(sensor);
    }
}
