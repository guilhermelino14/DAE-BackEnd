package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class NotificacaoDTO {
    @NotNull
    private int id;
    @NotNull
    private String mensagem;
    @NotNull
    private boolean lida;

    @NotNull
    private Date data;

    public NotificacaoDTO() {
    }

    public NotificacaoDTO(int id, String mensagem, Date data, boolean lida) {
        this.id = id;
        this.mensagem = mensagem;
        this.lida = lida;
        this.data = data;

    }

    public int getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Date getData() {
        return data;
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

    public void setData(Date data) {
        this.data = data;
    }
}
