package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.ConsumidorDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.NotificacaoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ConsumidorBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.NotificacaoBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Consumidor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Notificacao;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/consumidor")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class ConsumidorService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private ConsumidorBean consumidorBean;
    @EJB
    private NotificacaoBean notificacaoBean;

    private ConsumidorDTO toDTO(Consumidor consumidor) {
        return new ConsumidorDTO(
                consumidor.getUsername(),
                consumidor.getName(),
                consumidor.getEmail(),
                consumidor.getMorada()
        );
    }

    private EncomendaDTO toDTOEncomenda(Encomenda encomenda) {
        return new EncomendaDTO(
                encomenda.getId(),
                encomenda.getOperador(),
                encomenda.getConsumidor(),
                encomenda.getStatus(),
                encomenda.getData(),
                encomenda.getLocalizacao()
        );
    }

    private NotificacaoDTO toDTONotificacao(Notificacao notificacao) {
        return new NotificacaoDTO(
                notificacao.getId(),
                notificacao.getMensagem(),
                notificacao.getData()
        );
    }

    private List<ConsumidorDTO> toDTOs(List<Consumidor> consumidores) {
        return consumidores.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<EncomendaDTO> toDTOsEncomenda(List<Encomenda> encomendas) {
        return encomendas.stream().map(this::toDTOEncomenda).collect(Collectors.toList());
    }

    private List<NotificacaoDTO> toDTOsNotificacao(List<Notificacao> notificacoes) {
        return notificacoes.stream().map(this::toDTONotificacao).collect(Collectors.toList());
    }

    @GET
    @Path("{username}")
    public Response getConsumidor(@PathParam("username") String username) {
        var consumidor = consumidorBean.find(username);
        return Response.ok(toDTO(consumidor)).build();
    }

    @GET
    @Path("{username}/encomendas")
    public Response getEncomendas(@PathParam("username") String username) {
         List< Encomenda> encomendas = consumidorBean.getAllEncomendasFromConsumidor(username);
        return Response.ok(toDTOsEncomenda(encomendas)).build();
    }

    @GET
    @Path("{username}/notificacoes")
    public Response getNotificacoes(@PathParam("username") String username) {
        List<Notificacao> notificacoes = notificacaoBean.getAllFromConsumidor(username);
        return Response.ok(toDTOsNotificacao(notificacoes)).build();
    }

    @PUT
    @Path("{username}/notificacoes/{id}/lida")
    public Response lida(@PathParam("username") String username, @PathParam("id") int id) {
        notificacaoBean.lida(id);
        return Response.ok().build();
    }

}
