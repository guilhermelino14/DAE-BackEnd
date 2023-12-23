package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;

public class ProdutoFisicoDTO {
    @NotNull
    private int referencia;

    public ProdutoFisicoDTO() {
    }

    public ProdutoFisicoDTO(int referencia) {
        this.referencia = referencia;
    }

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }
}
