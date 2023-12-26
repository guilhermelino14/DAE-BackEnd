package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.ejb.Stateless;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Encomenda;

import java.util.ArrayList;
import java.util.List;

public class ConsumidorDTO {
    @NotNull
    private String username;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String morada;

    private List<Encomenda> encomendas;

    public ConsumidorDTO() {
        this.encomendas = new ArrayList<>();
    }

    public ConsumidorDTO(String username, String name, String email, String morada) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.morada = morada;
        this.encomendas = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMorada() {
        return morada;
    }

    public List<Encomenda> getEncomendas() {
        return encomendas;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setEncomendas(List<Encomenda> encomendas) {
        this.encomendas = encomendas;
    }
}
