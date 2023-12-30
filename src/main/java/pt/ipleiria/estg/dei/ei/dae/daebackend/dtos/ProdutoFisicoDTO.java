package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Produto;

public class ProdutoFisicoDTO {
    @NotNull
    private int referencia;

    public ProdutoDTO produto;

    public ProdutoFisicoDTO() {
    }

    public ProdutoFisicoDTO(int referencia) {
        this.referencia = referencia;
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
}
