package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.TypeOfSensor;

import java.util.ArrayList;
import java.util.List;

public class SensorDTO {
    @NotNull
    @CsvBindByName(column = "id")
    @CsvBindByPosition(position = 0)
    private int id;
    @NotNull
    @CsvBindByName(column = "typeOfSensor")
    @CsvBindByPosition(position = 1)
    private TypeOfSensor typeOfSensor;

    public List<ObservacoesDTO> observacoes;

    public List<EmbalagemDTO> embalagens;

    public SensorDTO() {
        this.observacoes = new ArrayList<>();
    }

    public SensorDTO(int id, TypeOfSensor typeOfSensor) {
        this.id = id;
        this.observacoes = new ArrayList<>();
        this.typeOfSensor = typeOfSensor;
    }

    public int getId() {
        return id;
    }

    public TypeOfSensor getTypeOfSensor() {
        return typeOfSensor;
    }

    public List<ObservacoesDTO> getObservacoes() {
        return observacoes;
    }

    public List<EmbalagemDTO> getEmbalagens() {
        return embalagens;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTypeOfSensor(TypeOfSensor typeOfSensor) {
        this.typeOfSensor = typeOfSensor;
    }

    public void setObservacoes(List<ObservacoesDTO> observacoes) {
        this.observacoes = observacoes;
    }

    public void setEmbalagens(List<EmbalagemDTO> embalagens) {
        this.embalagens = embalagens;
    }
}
