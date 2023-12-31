package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemProduto;
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

    public List<ProdutoFisico> findProdutosFisicosByProdutoIds(List<Integer> produtoIds) {
        return entityManager.createNamedQuery("getAllProdutosFisicosByProdutoIds", ProdutoFisico.class)
                .setParameter("produtoIds", produtoIds)
                .getResultList();
    }
    public List<ProdutoFisico> findProdutosFisicosByProdutoId(int produtoId) {
        return entityManager.createNamedQuery("getAllProdutosFisicosByProdutoId", ProdutoFisico.class)
                .setParameter("produtoId", produtoId)
                .getResultList();
    }


//    public List<ProdutoFisico> findFirstProdutoFisicoByProdutoId(int produtoId) throws MyEntityNotFoundException {
//        List<ProdutoFisico> query = entityManager.createNamedQuery("getAllProdutosFisicosByProdutoId", ProdutoFisico.class)
//                .setParameter("produtoId", produtoId)
//                .getResultList();
//
//        if (query.isEmpty()) {
//            throw new MyEntityNotFoundException("Produto with produtoId: " + produtoId + " dont have stock");
//        }
//        return query;
//    }

    public long getCountProdutosFisicosByProdutoIdWithoutEncomenda(int produtoId) {
        return entityManager.createNamedQuery("getCountProdutosFisicosByProdutoIdWithoutEncomenda", Long.class)
                .setParameter("produtoId", produtoId)
                .getSingleResult();
    }

    public void addEmbalagemProduto(int referencia, int embalagemProdutoId) throws MyEntityNotFoundException {
        ProdutoFisico produtoFisico = find(referencia);
        EmbalagemProduto embalagemProduto = entityManager.find(EmbalagemProduto.class, embalagemProdutoId);
        if (embalagemProduto == null) {
            throw new MyEntityNotFoundException("EmbalagemProduto with id: " + embalagemProdutoId + " not found");
        }
        produtoFisico.addEmbalagemProduto(embalagemProduto);
        embalagemProduto.addProdutoFisico(produtoFisico);
    }

}
