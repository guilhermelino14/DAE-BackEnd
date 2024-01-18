package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Consumidor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.TypeOfSensor;

import java.util.ArrayList;
import java.util.List;

public class CriarEncomendaDTO {
    @NotNull
    private boolean has_sensor;

    private TypeOfSensor typeOfSensor;

    @NotNull
    private List<ProdutoDTO> produtos;

    @NotNull
    private String consumidor_username;


    public CriarEncomendaDTO() {
        this.produtos = new ArrayList<>();
    }

    public CriarEncomendaDTO(boolean has_sensor,TypeOfSensor typeOfSensor, String consumidor_username, List<ProdutoDTO> produtos) {
        this.has_sensor = has_sensor;
        this.typeOfSensor = typeOfSensor;
        this.produtos = new ArrayList<>();
        this.consumidor_username = consumidor_username;
    }

    public boolean isHas_sensor() {
        return has_sensor;
    }

    public TypeOfSensor getTypeOfSensor() {
        return typeOfSensor;
    }

    public List<ProdutoDTO> getProdutos() {
        return new ArrayList<>(produtos);
    }

    public void setHas_sensor(boolean has_sensor) {
        this.has_sensor = has_sensor;
    }

    public void setTypeOfSensor(TypeOfSensor typeOfSensor) {
        this.typeOfSensor = typeOfSensor;
    }

    public void setProdutos(List<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }

    public String getConsumidor_username() {
        return consumidor_username;
    }

    public void setConsumidor_username(String consumidor_username) {
        this.consumidor_username = consumidor_username;
    }
}
