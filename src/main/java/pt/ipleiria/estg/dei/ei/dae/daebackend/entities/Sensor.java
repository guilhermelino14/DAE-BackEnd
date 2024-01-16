package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSensores",
                query = "SELECT s FROM Sensor s " // JPQL
        )
})
public class Sensor {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeOfSensor typeOfSensor;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "sensores_embalagens",
            joinColumns = @JoinColumn(
                    name = "sensor_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "embalagem_id",
                    referencedColumnName = "id"
            )
    )
    private List<Embalagem> embalagens;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Observacoes> observacoes;

    public Sensor() {
        this.embalagens = new ArrayList<>();
        this.observacoes = new ArrayList<>();
    }

    public Sensor(TypeOfSensor typeOfSensor) {
        this.embalagens = new ArrayList<>();
        this.observacoes = new ArrayList<>();
        this.typeOfSensor = typeOfSensor;
    }

    public int getId() {
        return id;
    }

    public TypeOfSensor getTypeOfSensor() {
        return typeOfSensor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTypeOfSensor(TypeOfSensor typeOfSensor) {
        this.typeOfSensor = typeOfSensor;
    }

    public void addEmbalagem(Embalagem embalagem) {
        this.embalagens.add(embalagem);
    }

    public void removeEmbalagem(Embalagem embalagem) {
        this.embalagens.remove(embalagem);
    }

    public List<Embalagem> getEmbalagens() {
        return embalagens;
    }

    public void setEmbalagens(List<Embalagem> embalagens) {
        this.embalagens = embalagens;
    }

    public List<Observacoes> getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(List<Observacoes> observacoes) {
        this.observacoes = observacoes;
    }
}
