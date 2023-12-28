package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.ConsumidorDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ConsumidorBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Consumidor;
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

    private ConsumidorDTO toDTO(Consumidor consumidor) {
        return new ConsumidorDTO(
                consumidor.getUsername(),
                consumidor.getName(),
                consumidor.getEmail(),
                consumidor.getMorada()
        );
    }

    private List<ConsumidorDTO> toDTOs(List<Consumidor> consumidores) {
        return consumidores.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("{username}")
    public Response getConsumidor(@PathParam("username") String username) {
        var consumidor = consumidorBean.find(username);
        return Response.ok(toDTO(consumidor)).build();
    }

}
