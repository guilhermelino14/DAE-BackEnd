package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;


import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.ProdutoFisico;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProdutoBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private FabricanteBean fabricanteBean;

    public void create(String nome, String categoria, String descricao) {
        Produto produto = new Produto(nome, categoria, descricao);
        produto.setFabricante(fabricanteBean.find("fabricante1"));
        entityManager.persist(produto);
    }

    public List<Produto> getAll() {
        return entityManager.createNamedQuery("getAllProdutos", Produto.class).getResultList();
    }

    public Produto find(int id) throws MyEntityNotFoundException {
        Produto produto = entityManager.find(Produto.class, id);
        if (produto == null) {
            throw new MyEntityNotFoundException("Produto with id " + id + " not found.");
        }
        return produto;
    }


    public void delete(int id) throws MyEntityNotFoundException, MyConstraintViolationException {
        Produto produto = find(id);
        if (!produto.getProdutoFisico().isEmpty()) {
            throw new MyConstraintViolationException("Produto has Produtos Fisicos.");
        }
        entityManager.remove(produto);
    }

    public void addToStock(int id, int total) throws MyEntityNotFoundException {
        Produto produto = find(id);
        for (int i = 0; i < total; i++) {
            ProdutoFisico produtoFisico = new ProdutoFisico(produto);
            entityManager.persist(produtoFisico);
        }
    }
}
