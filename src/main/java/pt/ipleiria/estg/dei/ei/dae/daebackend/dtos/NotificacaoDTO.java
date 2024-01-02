package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;

public class NotificacaoDTO {
    @NotNull
    private int id;
    @NotNull
    private String mensagem;
    @NotNull
    private boolean lida;

    public NotificacaoDTO() {
        lida = false;
    }

    public NotificacaoDTO(int id,String mensagem) {
        this.id = id;
        this.mensagem = mensagem;
        this.lida = false;
    }

    public int getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public boolean isLida() {
        return lida;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }
}
