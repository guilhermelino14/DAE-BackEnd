package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;


import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProdutoBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private FabricanteBean fabricanteBean;

    public void create(String nome, String categoria, String descricao, int quantidade, TypeOfSensor typeOfSensor) {
        Produto produto = new Produto(nome, categoria, descricao, quantidade, typeOfSensor);
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
        if (!produto.getProdutoFisicos().isEmpty()) {
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

    public boolean associateFabricante(int id, String username) throws MyEntityNotFoundException {
        Produto produto = entityManager.find(Produto.class, id);
        Fabricante fabricante = fabricanteBean.find(username);
        if ( produto != null && fabricante != null) {
            produto.setFabricante(fabricante);
            fabricante.addProduto(produto);
            return true;
        }
        return false;
    }

}
