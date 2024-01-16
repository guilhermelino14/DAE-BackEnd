package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

@Entity
@NamedQueries(
        {
                @NamedQuery(
                        name = "getAllSensorRoles",
                        query = "SELECT sr FROM SensorRoles sr"
                )
        }
)
public class SensorRoles {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Produto produto;

    private TypeOfSensor typeOfSensor;

    private double val_max;

    private double val_min;

    public SensorRoles() {
    }

    public SensorRoles(Produto produto, TypeOfSensor typeOfSensor, double val_max, double val_min) {
        this.produto = produto;
        this.typeOfSensor = typeOfSensor;
        this.val_max = val_max;
        this.val_min = val_min;
    }

    public int getId() {
        return id;
    }

    public Produto getProduto() {
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

    public void setProduto(Produto produto) {
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
