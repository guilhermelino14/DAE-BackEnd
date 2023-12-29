package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllEncomendas",
                query = "SELECT e FROM Encomenda e ORDER BY e.id"
        ),
        @NamedQuery(
                name = "getAllEncomendasByConsumidorUsername",
                query = "SELECT e FROM Encomenda e WHERE e.consumidor.username = :username" // JPQL
        )
})
public class Encomenda {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "operador_id")
    private Operador operador;
    @ManyToOne
    @JoinColumn(name = "username")
    private Consumidor consumidor;

    //ManyToMany embalagens reutilizadas
    @OneToMany(mappedBy = "encomenda")
    private List<EmbalagemTransporte> embalagensTransporte;

    @OneToMany(mappedBy = "encomenda")
    private List<ProdutoFisico> produtos;

    private Date date;

    private EncomendaStatus status;

    public Encomenda() {
        embalagensTransporte = new ArrayList<>();
        produtos = new ArrayList<>();
    }

    public Encomenda(Operador operador, Consumidor consumidor, Date date, EncomendaStatus status) {
        this.operador = operador;
        this.consumidor = consumidor;
        this.embalagensTransporte = new ArrayList<>();
        this.produtos = new ArrayList<>();
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Operador getOperador() {
        return operador;
    }

    public Consumidor getConsumidor() {
        return consumidor;
    }

    public List<EmbalagemTransporte> getEmbalagensTransporte() {
        return embalagensTransporte;
    }

    public List<ProdutoFisico> getProdutos() {
        return produtos;
    }

    public Date getDate() {
        return date;
    }

    public EncomendaStatus getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public void setConsumidor(Consumidor consumidor) {
        this.consumidor = consumidor;
    }

    public void setEmbalagensTransporte(List<EmbalagemTransporte> embalagensTransporte) {
        this.embalagensTransporte = embalagensTransporte;
    }

    public void setProdutos(List<ProdutoFisico> produtos) {
        this.produtos = produtos;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(EncomendaStatus status) {
        this.status = status;
    }

    public void addProduto(Produto produto) {
        produtos.add(produto);
    }

    public void removeProduto(Produto produto) {
        produtos.remove(produto);
    }
}
