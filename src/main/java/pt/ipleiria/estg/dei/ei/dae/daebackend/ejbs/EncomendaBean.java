package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Consumidor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Operador;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.ProdutoFisico;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class EncomendaBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Operador operador, Consumidor consumidor) {
        entityManager.persist(new Encomenda(operador, consumidor));
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
}
