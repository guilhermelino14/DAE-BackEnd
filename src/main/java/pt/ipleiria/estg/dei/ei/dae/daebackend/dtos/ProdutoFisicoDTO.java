package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Embalagem;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoFisicoDTO {
    @NotNull
    private int referencia;

    public ProdutoDTO produto;

    public List<EmbalagemProdutoDTO> embalagensProduto;
    public ProdutoFisicoDTO() {
        this.embalagensProduto = new ArrayList<>();
    }

    public ProdutoFisicoDTO(int referencia) {
        this.referencia = referencia;
        this.embalagensProduto = new ArrayList<>();
    }

    public int getReferencia() {
        return referencia;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

    public List<EmbalagemProdutoDTO> getEmbalagensProduto() {
        return embalagensProduto;
    }

    public void setEmbalagensProduto(List<EmbalagemProdutoDTO> embalagensProduto) {
        this.embalagensProduto = embalagensProduto;
    }
}
