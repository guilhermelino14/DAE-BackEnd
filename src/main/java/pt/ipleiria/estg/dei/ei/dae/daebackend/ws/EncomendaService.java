package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ConsumidorBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.EncomendaBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.OperadorBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Consumidor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Operador;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/encomendas")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Operador", "Consumidor"})
public class EncomendaService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private EncomendaBean encomendaBean;
    @EJB
    private ConsumidorBean consumidorBean;
    @EJB
    private OperadorBean operadorBean;

    private EncomendaDTO toDTO(Encomenda encomenda) {
        return new EncomendaDTO(
                encomenda.getId(),
                encomenda.getOperador(),
                encomenda.getConsumidor()
        );
    }

    private List<EncomendaDTO> toDTOs(List<Encomenda> encomendas) {
        return encomendas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<EncomendaDTO> getAll() {
        return toDTOs(encomendaBean.getAll());
    }

    @GET
    @Path("{username}")
    public List<EncomendaDTO> getEncomendaFromUser(@PathParam("username") String username){
        return toDTOs(encomendaBean.getEncomendaByConsumidorUsername(username));
    }


    @POST
    @Path("/")
    public Response createEncomenda() {
        String username = securityContext.getUserPrincipal().getName();
        Consumidor consumidorFinded = consumidorBean.find(username);
        Operador operadorFinded = operadorBean.find("operador1");

        encomendaBean.create(operadorFinded, consumidorFinded);

        // retrun a response with status 201 and the newly created encomenda
        return Response.ok("Encomenda criada com sucesso!").build();
    }
}
