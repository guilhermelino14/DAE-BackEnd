package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EmbalagemProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.ProdutoFisicoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.EmbalagemProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.EncomendaBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.FabricanteBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/fabricante")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class FabricanteService {

    @EJB
    private FabricanteBean fabricanteBean;

    @Context
    private SecurityContext securityContext;

    @EJB
    private EncomendaBean encomendaBean;

    private EncomendaDTO toDTO(Encomenda encomenda) {
        var dto = new EncomendaDTO(
                encomenda.getId(),
                encomenda.getOperador(),
                encomenda.getConsumidor(),
                encomenda.getStatus(),
                encomenda.getData()
        );
        dto.produtosFisicos = encomenda.getProdutosFisicos().stream().map(this::toDTO).collect(Collectors.toList());
        return dto;
    }

    private ProdutoFisicoDTO toDTO(ProdutoFisico produtoFisico) {
        return new ProdutoFisicoDTO(
                produtoFisico.getReferencia()
        );
    }

    private List<EncomendaDTO> toDTOs(List<Encomenda> encomendas) {
        return encomendas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public Response getAllFabricantes() {
        return Response.ok("ok").build();
    }

    @GET
    @Path("/encomendas")
    public List<EncomendaDTO> getEncomendasPendentes() {
        return toDTOs(encomendaBean.getEncomendasPendentes());
    }

}
