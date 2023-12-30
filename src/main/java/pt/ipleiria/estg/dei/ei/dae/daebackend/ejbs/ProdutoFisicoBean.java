package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.ProdutoFisico;

import java.util.List;

@Stateless
public class ProdutoFisicoBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(Produto produto) {
        entityManager.persist(new ProdutoFisico(produto));
    }

    public ProdutoFisico find(int referencia) {
        return entityManager.find(ProdutoFisico.class, referencia);
    }

    public List<ProdutoFisico> findProdutosFisicosByProdutoId(int produtoId) {
        return entityManager.createNamedQuery("getAllProdutosFisicosByProdutoId", ProdutoFisico.class)
                .setParameter("produtoId", produtoId)
                .getResultList();
    }

    public ProdutoFisico findFirstProdutoFisicoByProdutoId(int produtoId) {
        return entityManager.createNamedQuery("getAllProdutosFisicosByProdutoId", ProdutoFisico.class)
                .setParameter("produtoId", produtoId)
                .getSingleResult();
    }


}
