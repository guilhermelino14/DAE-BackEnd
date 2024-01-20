package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Fabricante;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.SensorRole;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.TypeOfSensor;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDTO {
    @NotNull
    @CsvBindByName(column = "id")
    @CsvBindByPosition(position = 0)
    private int id;
    @NotNull
    @CsvBindByName(column = "nome")
    @CsvBindByPosition(position = 1)
    private String nome;
    @NotNull
    @CsvBindByName(column = "categoria")
    @CsvBindByPosition(position = 2)
    private String categoria;
    @NotNull
    @CsvBindByName(column = "descricao")
    @CsvBindByPosition(position = 3)
    private String descricao;

    public Fabricante fabricante;

    public List<ProdutoFisicoDTO> produtoFisicos;

    @CsvBindByName(column = "quantidade")
    @CsvBindByPosition(position = 4)
    private int quantidade;
    @CsvBindByName(column = "isLiquido")
    @CsvBindByPosition(position = 6)
    private boolean isLiquido;

    @CsvBindByName(column = "typeOfSensor")
    @CsvBindByPosition(position = 5)
    public TypeOfSensor typeOfSensor;

    public List<SensorRoleDTO> sensorRoles;


    public ProdutoDTO() {
        this.produtoFisicos = new ArrayList<>();
        this.sensorRoles = new ArrayList<>();
    }

    public ProdutoDTO(int id, String nome, String categoria, String descricao, int quantidade, TypeOfSensor typeOfSensor, boolean isLiquido) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.produtoFisicos = new ArrayList<>();
        this.sensorRoles = new ArrayList<>();
        this.quantidade = quantidade;
        this.typeOfSensor = typeOfSensor;
        this.isLiquido = isLiquido;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public List<ProdutoFisicoDTO> getProdutoFisicos() {
        return produtoFisicos;
    }

    public TypeOfSensor getTypeOfSensor() {
        return typeOfSensor;
    }

    public List<SensorRoleDTO> getSensorRoles() {
        return sensorRoles;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setProdutoFisicos(List<ProdutoFisicoDTO> produtoFisicos) {
        this.produtoFisicos = produtoFisicos;
    }

    public void setTypeOfSensor(TypeOfSensor typeOfSensor) {
        this.typeOfSensor = typeOfSensor;
    }

    public void setSensorRoles(List<SensorRoleDTO> sensorRoles) {
        this.sensorRoles = sensorRoles;
    }

    public boolean isLiquido() {
        return isLiquido;
    }

    public void setLiquido(boolean liquido) {
        isLiquido = liquido;
    }
}
