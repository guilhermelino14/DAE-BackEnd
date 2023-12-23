package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Produto;

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

    public Produto find(int id) {
        return entityManager.find(Produto.class, id);
    }


}
