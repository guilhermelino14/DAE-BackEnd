package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Table(name = "embalagens")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public class Embalagem {
    @Id
    private int id;
    @NotNull
    private double altura;
    @NotNull
    private double largura;

    public Embalagem() {
    }

    public Embalagem(double altura, double largura) {
        this.altura = altura;
        this.largura = largura;
    }

    public double getAltura() {
        return altura;
    }

    public double getLargura() {
        return largura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }
}
