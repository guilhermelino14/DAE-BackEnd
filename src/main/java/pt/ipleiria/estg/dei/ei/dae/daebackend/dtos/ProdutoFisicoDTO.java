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

    public List<EmbalagemProduto> embalagens;
    public ProdutoFisicoDTO() {
    }

    public ProdutoFisicoDTO(int referencia) {
        this.referencia = referencia;
        this.embalagens = new ArrayList<>();
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

    public List<EmbalagemProduto> getEmbalagens() {
        return new ArrayList<>(embalagens);
    }

    public void setEmbalagens(List<EmbalagemProduto> embalagens) {
        this.embalagens = embalagens;
    }
}
