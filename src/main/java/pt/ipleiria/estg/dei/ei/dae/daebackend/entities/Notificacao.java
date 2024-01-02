package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllNotificacoes",
                query = "SELECT n FROM Notificacao n ORDER BY n.id"
        ),
        @NamedQuery(
                name = "getAllNotificacoesFromConsumidor",
                query = "SELECT n FROM Notificacao n WHERE n.consumidor.id = :username ORDER BY n.id"
        ) // JPQL
})
public class Notificacao {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "consumidor_id")
    private Consumidor consumidor;

    private boolean lida;

    public Notificacao() {
        lida = false;
    }

    public Notificacao(String mensagem) {
        this.mensagem = mensagem;
        this.lida = false;
    }

    public int getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Consumidor getConsumidor() {
        return consumidor;
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

    public void setConsumidor(Consumidor consumidor) {
        this.consumidor = consumidor;
    }
}
