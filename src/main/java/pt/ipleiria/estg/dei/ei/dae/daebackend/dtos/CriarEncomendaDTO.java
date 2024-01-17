package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.TypeOfSensor;

import java.util.ArrayList;
import java.util.List;

public class CriarEncomendaDTO {
    @NotNull
    private boolean has_sensor;

    private TypeOfSensor typeOfSensor;

    @NotNull
    private List<ProdutoDTO> produtos;

    public CriarEncomendaDTO() {
        this.produtos = new ArrayList<>();
    }

    public CriarEncomendaDTO(boolean has_sensor,TypeOfSensor typeOfSensor, List<ProdutoDTO> produtos) {
        this.has_sensor = has_sensor;
        this.typeOfSensor = typeOfSensor;
        this.produtos = new ArrayList<>();
    }

    public boolean isHas_sensor() {
        return has_sensor;
    }

    public TypeOfSensor getTypeOfSensor() {
        return typeOfSensor;
    }

    public List<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setHas_sensor(boolean has_sensor) {
        this.has_sensor = has_sensor;
    }

    public void setTypeOfSensor(TypeOfSensor typeOfSensor) {
        this.typeOfSensor = typeOfSensor;
    }

}
