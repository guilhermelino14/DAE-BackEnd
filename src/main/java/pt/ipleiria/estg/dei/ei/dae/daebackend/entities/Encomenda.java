package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.*;

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
                name = "getAllEncomendasStatusPendetes",
                query = "SELECT e FROM Encomenda e WHERE e.status = :status ORDER BY e.id"
        ),
        @NamedQuery(
                name = "getAllEncomendasStatusNotPendetes",
                query = "SELECT e FROM Encomenda e WHERE e.status != :status ORDER BY e.id"
        ),
        @NamedQuery(
                name = "getAllEncomendasByConsumidorUsername",
                query = "SELECT e FROM Encomenda e WHERE e.consumidor.id = :username ORDER BY e.id"
        ),
        @NamedQuery(
                name = "getAllEncomendasFromHimAndNotPendentes",
                query = "SELECT e FROM Encomenda e WHERE e.operador.username = :operadorUsername OR e.status != :status ORDER BY e.id"
        ),
})
public class Encomenda {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "operador_id")
    private Operador operador;
    @ManyToOne
    @JoinColumn(name = "consumidor_id")
    private Consumidor consumidor;

    @OneToMany(mappedBy = "encomenda", fetch = FetchType.EAGER)
    private List<ProdutoFisico> produtosFisicos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "encomenda_embalagensTransporte",
            joinColumns = @JoinColumn(
                    name = "encomenda_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "embalagemTransporte_id",
                    referencedColumnName = "id"
            )
    )

    private List<EmbalagemTransporte> embalagensTransporte;

    private Date data;

    private EncomendaStatus status;
    private String localizacao;


    public Encomenda() {
        embalagensTransporte = new ArrayList<>();
        produtosFisicos = new ArrayList<>();
    }

    public Encomenda(Operador operador, Consumidor consumidor, EncomendaStatus status, Date data, String localizacao) {
        this.operador = operador;
        this.consumidor = consumidor;
        this.embalagensTransporte = new ArrayList<>();
        this.produtosFisicos = new ArrayList<>();
        this.status = status;
        this.data = data;
        this.localizacao = localizacao;
    }

    public Encomenda(Consumidor consumidor, EncomendaStatus status, Date data, String localizacao) {
        this.consumidor = consumidor;
        this.embalagensTransporte = new ArrayList<>();
        this.produtosFisicos = new ArrayList<>();
        this.status = status;
        this.data = data;
        this.localizacao = localizacao;
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

    public List<ProdutoFisico> getProdutosFisicos() {
        return produtosFisicos;
    }

    public Date getData() {
        return data;
    }

    public EncomendaStatus getStatus() {
        return status;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }


    public void setEmbalagensTransporte(List<EmbalagemTransporte> embalagensTransporte) {
        this.embalagensTransporte = embalagensTransporte;
    }

    public void setProdutosFisicos(List<ProdutoFisico> produtosFisicos) {
        this.produtosFisicos = produtosFisicos;
    }

    public void addProdutoFisico(ProdutoFisico produtoFisico) {
        this.produtosFisicos.add(produtoFisico);
    }

    public void removeProdutoFisico(ProdutoFisico produtoFisico) {
        this.produtosFisicos.remove(produtoFisico);
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setStatus(EncomendaStatus status) {
        this.status = status;
    }

    public void addEmbalagemTransporte(EmbalagemTransporte embalagemTransporte) {
        this.embalagensTransporte.add(embalagemTransporte);
    }

    public void removeEmbalagemTransporte(EmbalagemTransporte embalagemTransporte) {
        this.embalagensTransporte.remove(embalagemTransporte);
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
