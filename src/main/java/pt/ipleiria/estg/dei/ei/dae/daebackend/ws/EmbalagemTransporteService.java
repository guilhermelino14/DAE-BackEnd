package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EmbalagemProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EmbalagemTransporteDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.EmbalagemTransporteBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/embalagensTransporte")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Operador"})
public class EmbalagemTransporteService {

    @EJB
    private EmbalagemTransporteBean embalagemTransporteBean;

    private EmbalagemTransporteDTO toDTO(EmbalagemTransporte embalagemTransporte) {
        var dto = new EmbalagemTransporteDTO(
                embalagemTransporte.getId(),
                embalagemTransporte.getNome(),
                embalagemTransporte.getAltura(),
                embalagemTransporte.getLargura()
        );
        dto.encomendas = embalagemTransporte.getEncomendas().stream().map(this::toDTOEncomendas).collect(Collectors.toList());
        return dto;
    }

    private EncomendaDTO toDTOEncomendas(Encomenda encomenda) {
        return new EncomendaDTO(
                encomenda.getId(),
                encomenda.getOperador(),
                encomenda.getConsumidor(),
                encomenda.getStatus(),
                encomenda.getData()
        );
    }
    private List<EmbalagemTransporteDTO> toDTOs(List<EmbalagemTransporte> embalagensTransporte) {
        return embalagensTransporte.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<EncomendaDTO> toDTOsEncomendas(List<Encomenda> encomendas) {
        return encomendas.stream().map(this::toDTOEncomendas).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<EmbalagemTransporteDTO> getEmbalagensTransporte() {
        return toDTOs(embalagemTransporteBean.getAll());
    }

    @POST
    @Path("{id}/addEncomenda/{idEncomenda}")
    public Response addEncomenda(@PathParam("id") int id, @PathParam("idEncomenda") int idEncomenda) throws Exception {
        embalagemTransporteBean.addEncomenda(id, idEncomenda);
        return Response.ok("Encomenda adicionada com sucesso").build();
    }

    @DELETE
    @Path("{id}/removeEncomenda/{idEncomenda}")
    public Response removeEncomenda(@PathParam("id") int id, @PathParam("idEncomenda") int idEncomenda) {
        embalagemTransporteBean.removeEncomenda(id, idEncomenda);
        return Response.ok("Encomenda removida com sucesso").build();
    }

}
