package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.ObservacoesDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ObservacoesBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Observacoes;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/observacoes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Operador", "Fabricante"})
public class ObservacoesService {
    @EJB
    private ObservacoesBean observacoesBean;

    @Context
    private SecurityContext securityContext;

    private ObservacoesDTO toDTONoObservacoes(Observacoes observacao) {
        return new ObservacoesDTO(
                observacao.getId(),
                observacao.getObservacao(),
                observacao.getData()
        );
    }

    private List<ObservacoesDTO> toDTOsNoObservacoes(List<Observacoes> observacoes) {
        return observacoes.stream().map(this::toDTONoObservacoes).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ObservacoesDTO> getAllObservacoes() {
        return toDTOsNoObservacoes(observacoesBean.getAll());
    }
}
