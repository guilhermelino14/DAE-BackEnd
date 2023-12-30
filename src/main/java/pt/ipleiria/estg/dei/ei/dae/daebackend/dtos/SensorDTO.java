package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SensorDTO {
    @NotNull
    private int id;
    @NotNull
    private String nome;
    @NotNull
    private String descricao;

    public List<ObservacoesDTO> observacoes;

    public List<EmbalagemDTO> embalagens;

    public SensorDTO() {
        this.observacoes = new ArrayList<>();
    }

    public SensorDTO(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.observacoes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setObservacoes(List<ObservacoesDTO> observacoes) {
        this.observacoes = observacoes;
    }

    public void setEmbalagens(List<EmbalagemDTO> embalagens) {
        this.embalagens = embalagens;
    }
}
