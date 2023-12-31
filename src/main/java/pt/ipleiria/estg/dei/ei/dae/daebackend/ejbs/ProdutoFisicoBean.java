package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.ProdutoFisico;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProdutoFisicoBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(Produto produto) {
        entityManager.persist(new ProdutoFisico(produto));
    }

    public ProdutoFisico find(int referencia) throws MyEntityNotFoundException {
        ProdutoFisico produtoFisico = entityManager.find(ProdutoFisico.class, referencia);
        if(produtoFisico == null){
            throw new MyEntityNotFoundException("ProdutoFisico not found");
        }
        return  produtoFisico;
    }

    public List<ProdutoFisico> findProdutosFisicosByProdutoId(int produtoId) {
        return entityManager.createNamedQuery("getAllProdutosFisicosByProdutoId", ProdutoFisico.class)
                .setParameter("produtoId", produtoId)
                .getResultList();
    }

    public ProdutoFisico findFirstProdutoFisicoByProdutoId(int produtoId) throws MyEntityNotFoundException {
        List<ProdutoFisico> query = entityManager.createNamedQuery("getAllProdutosFisicosByProdutoId", ProdutoFisico.class)
                .setParameter("produtoId", produtoId)
                .getResultList();

        if (query.isEmpty()) {
            throw new MyEntityNotFoundException("Produto with produtoId: " + produtoId + " dont have stock");
        }
        return query.get(0);
    }

    public long getCountProdutosFisicosByProdutoIdWithoutEncomenda(int produtoId) {
        return entityManager.createNamedQuery("getCountProdutosFisicosByProdutoIdWithoutEncomenda", Long.class)
                .setParameter("produtoId", produtoId)
                .getSingleResult();
    }

}
