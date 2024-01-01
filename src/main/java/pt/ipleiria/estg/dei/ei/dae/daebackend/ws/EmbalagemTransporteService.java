package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EmbalagemProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EmbalagemTransporteDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.EmbalagemTransporteBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ObservacoesBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;
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
    @EJB
    private ObservacoesBean observacoesBean;

    private EmbalagemTransporteDTO toDTO(EmbalagemTransporte embalagemTransporte) {
        var dto = new EmbalagemTransporteDTO(
                embalagemTransporte.getId(),
                embalagemTransporte.getNome(),
                embalagemTransporte.getAltura(),
                embalagemTransporte.getLargura()
        );
        dto.encomendas = embalagemTransporte.getEncomendas().stream().map(this::toDTOEncomendas).collect(Collectors.toList());
        dto.sensores = embalagemTransporte.getSensores().stream().map(this::toDTONoSensores).collect(Collectors.toList());
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

    private SensorDTO toDTONoSensores(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getNome(),
                sensor.getDescricao()
        );
    }
    private List<EmbalagemTransporteDTO> toDTOs(List<EmbalagemTransporte> embalagensTransporte) {
        return embalagensTransporte.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<EncomendaDTO> toDTOsEncomendas(List<Encomenda> encomendas) {
        return encomendas.stream().map(this::toDTOEncomendas).collect(Collectors.toList());
    }

    private List<SensorDTO> sensorToDTOs(List<Sensor> subjects) {
        return subjects.stream().map(this::toDTONoSensores).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<EmbalagemTransporteDTO> getEmbalagensTransporte() {
        return toDTOs(embalagemTransporteBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getEmbalagemTransporteDetails(@PathParam("id") int id) {
        EmbalagemTransporte embalagemTransporte = embalagemTransporteBean.find(id);
        if (embalagemTransporte != null) {
            return Response.status(Response.Status.OK).entity(toDTO(embalagemTransporte)).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Embalagem de transporte não encontrada").build();
    }

    @POST
    @Path("/")
    public Response createNewEmbalagemTransporte(EmbalagemTransporteDTO embalagemTransporteDTO){
        embalagemTransporteBean.create(
                embalagemTransporteDTO.getNome(),
                embalagemTransporteDTO.getAltura(),
                embalagemTransporteDTO.getLargura()
        );
        return Response.ok("Embalagem de transporte criada com sucesso").build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteEmbalagemTransporte(@PathParam("id") int id) {
        embalagemTransporteBean.remove(id);
        return Response.ok("Embalagem de transporte removida com sucesso").build();
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

    @POST
    @Path("{id}/sensor/{idSensor}")
    public Response addSensor(@PathParam("id") int id, @PathParam("idSensor") int idSensor) throws MyEntityNotFoundException {
        embalagemTransporteBean.associarEmbalagemAoSensor(id, idSensor);
        return Response.ok("Sensor adicionado com sucesso").build();
    }

    @DELETE
    @Path("{id}/sensor/{idSensor}")
    public Response removeSensor(@PathParam("id") int id, @PathParam("idSensor") int idSensor) throws MyEntityNotFoundException {
        embalagemTransporteBean.desassociarEmbalagemAoSensor(id, idSensor);
        observacoesBean.deleteWhereSensorId(idSensor);
        return Response.ok("Sensor removido com sucesso").build();
    }
}
