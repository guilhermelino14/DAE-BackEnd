package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class EncomendaBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private NotificacaoBean notificacaoBean;

    public void create(Operador operador, Consumidor consumidor) {
        entityManager.persist(new Encomenda(operador, consumidor, EncomendaStatus.PENDENTE, new Date(), "Fabrica"));
    }

    public Encomenda find(int id) throws MyEntityNotFoundException {
        Encomenda encomenda = entityManager.find(Encomenda.class, id);
        if (encomenda == null) {
            throw new MyEntityNotFoundException("Produto with id " + id + " not found.");
        }
        return encomenda;
    }

    public List<Encomenda> getAll() {
        return entityManager.createNamedQuery("getAllEncomendas", Encomenda.class).getResultList();
    }

    public boolean addProduct(int id, int productReference) {
        Encomenda encomenda = entityManager.find(Encomenda.class, id);
        ProdutoFisico produtoFisico = entityManager.find(ProdutoFisico.class, productReference);
        if (encomenda != null && produtoFisico != null) {
            encomenda.addProdutoFisico(produtoFisico);
            produtoFisico.addEncomenda(encomenda);
            return true;
        }
        return false;
    }

    public List<Encomenda> getEncomendasPendentes() {
        return entityManager.createNamedQuery("getAllEncomendasStatusPendetes", Encomenda.class).setParameter("status", EncomendaStatus.PENDENTE).getResultList();
    }

    public List<Encomenda> getEncomendasNotPendentes() {
        return entityManager.createNamedQuery("getAllEncomendasStatusNotPendetes", Encomenda.class).setParameter("status", EncomendaStatus.PENDENTE).getResultList();
    }

    public void updateStatus(int id, EncomendaStatus status) throws MyEntityNotFoundException {
        Encomenda encomenda = find(id);
        if (status == EncomendaStatus.CONFIRMACAO){
            encomenda.setLocalizacao("Armazem");
        }
        if (status == EncomendaStatus.EM_TRANSITO){
            encomenda.setLocalizacao("Rua do Quim");
            notificacaoBean.create("Encomenda numero "+ encomenda.getId() +" enviada com sucesso, encomenda encontra-se em Rua do Quim", encomenda.getConsumidor());
        }
        if (status == EncomendaStatus.ENTREGUE){
            encomenda.setLocalizacao("Rua do xico esperto");
            notificacaoBean.create("Encomenda numero "+ encomenda.getId() +" entregue com sucesso", encomenda.getConsumidor());
        }
        encomenda.setStatus(status);
        entityManager.merge(encomenda);
    }
}
