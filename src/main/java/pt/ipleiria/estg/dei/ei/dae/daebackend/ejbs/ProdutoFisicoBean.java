package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.ProdutoFisico;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProdutoFisicoBean {
    @PersistenceContext
    private EntityManager entityManager;

    public ProdutoFisico create(Produto produto) {
        ProdutoFisico produtoFisico = new ProdutoFisico(produto);
        entityManager.persist(produtoFisico);
        return produtoFisico;
    }

    public void createMany(Produto produto, int embalagemProdutoId, Encomenda encomenda) throws MyEntityNotFoundException {
        for (int i = 0; i < produto.getQuantidade(); i++) {
            ProdutoFisico produtoFisico = new ProdutoFisico(produto);
            entityManager.persist(produtoFisico);
            addEmbalagemProduto(produtoFisico.getReferencia(), embalagemProdutoId);
            encomenda.addProdutoFisico(produtoFisico);
            produtoFisico.addEncomenda(encomenda);
        }
    }

    public ProdutoFisico find(int referencia) throws MyEntityNotFoundException {
        ProdutoFisico produtoFisico = entityManager.find(ProdutoFisico.class, referencia);
        if(produtoFisico == null){
            throw new MyEntityNotFoundException("ProdutoFisico not found");
        }
        return  produtoFisico;
    }

    public List<ProdutoFisico> getAll() {
        TypedQuery<ProdutoFisico> query = entityManager.createNamedQuery("getAllProdutosFisicos", ProdutoFisico.class);
        return query.getResultList();
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

    public void removeProdutoEmbalagem(int referencia, int embalagemId) throws MyEntityNotFoundException {
        ProdutoFisico produtoFisico = find(referencia);
        EmbalagemProduto embalagemProduto = entityManager.find(EmbalagemProduto.class, embalagemId);
        if (embalagemProduto == null) {
            throw new MyEntityNotFoundException("EmbalagemProduto with id: " + embalagemId + " not found");
        }
        produtoFisico.removeEmbalagemProduto(embalagemProduto);
        embalagemProduto.removeProdutoFisico(produtoFisico);
    }

}
