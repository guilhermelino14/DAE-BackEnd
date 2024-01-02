package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Consumidor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Notificacao;

import java.util.List;

@Stateless
public class NotificacaoBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String mensagem, Consumidor consumidor){
        Notificacao notificacao = new Notificacao(mensagem);
        if (consumidor != null){
            notificacao.setConsumidor(consumidor);
            consumidor.addNotificacao(notificacao);
        }
        entityManager.persist(notificacao);
    }

    public List<Notificacao> getAll(){
        return entityManager.createNamedQuery("getAllNotificacoes", Notificacao.class).getResultList();
    }

    public List<Notificacao> getAllFromConsumidor(String username){
        return entityManager.createNamedQuery("getAllNotificacoesFromConsumidor", Notificacao.class).setParameter("username", username).getResultList();
    }

    public void lida(int id){
        Notificacao notificacao = entityManager.find(Notificacao.class, id);
        notificacao.setLida(true);
    }

    public void delete(int id){
        Notificacao notificacao = entityManager.find(Notificacao.class, id);
        entityManager.remove(notificacao);
    }
}
