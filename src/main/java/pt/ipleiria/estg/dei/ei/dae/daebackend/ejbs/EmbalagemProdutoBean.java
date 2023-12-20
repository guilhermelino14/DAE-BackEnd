package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Embalagem;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class EmbalagemProdutoBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String nome, double altura, double largura){
        entityManager.persist(new EmbalagemProduto(nome, altura, largura));
    }

    public List<EmbalagemProduto> getAll()  {
        return entityManager.createNamedQuery("getAllEmbalagens", EmbalagemProduto.class).getResultList();
    }

    public EmbalagemProduto find(String nome) throws MyEntityNotFoundException {
        EmbalagemProduto student = entityManager.find(EmbalagemProduto.class, nome);
        if(student == null){
            throw new MyEntityNotFoundException(
                    "Embalagem with name '" + nome + "' not found"
            );
        }
        return student;
    }
}
