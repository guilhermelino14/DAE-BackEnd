package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.TypeOfSensor;

public class SensorRoleDTO {
    @NotNull
    public int id;

    public ProdutoDTO produto;
    public TypeOfSensor typeOfSensor;
    public double val_max;
    public double val_min;

    public SensorRoleDTO() {
    }

    public SensorRoleDTO(int id, ProdutoDTO produto, TypeOfSensor typeOfSensor, double val_max, double val_min) {
        this.id = id;
        this.produto = produto;
        this.typeOfSensor = typeOfSensor;
        this.val_max = val_max;
        this.val_min = val_min;
    }

    public int getId() {
        return id;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public TypeOfSensor getTypeOfSensor() {
        return typeOfSensor;
    }

    public double getVal_max() {
        return val_max;
    }

    public double getVal_min() {
        return val_min;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

    public void setTypeOfSensor(TypeOfSensor typeOfSensor) {
        this.typeOfSensor = typeOfSensor;
    }

    public void setVal_max(double val_max) {
        this.val_max = val_max;
    }

    public void setVal_min(double val_min) {
        this.val_min = val_min;
    }
}
