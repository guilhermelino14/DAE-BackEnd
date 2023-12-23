package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EmbalagemProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.SensorType;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.ForbiddenException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/sensores")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Operador", "Fabricante"})
public class SensorService {
    @EJB
    private SensorBean sensorBean;

    @Context
    private SecurityContext securityContext;

    private SensorDTO toDTONoSensores(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getNome(),
                sensor.getDescricao()
        );
    }

    private List<SensorDTO> toDTOsNoSensores(List<Sensor> sensors) {
        return sensors.stream().map(this::toDTONoSensores).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<SensorDTO> getAllSensores() {
        System.out.println(securityContext.isUserInRole("Operador"));
        if (securityContext.isUserInRole("Operador"))
            return toDTOsNoSensores(sensorBean.getAll(SensorType.OPERADOR));
        if (securityContext.isUserInRole("Fabricante"))
            return toDTOsNoSensores(sensorBean.getAll(SensorType.FABRICANTE));
        return toDTOsNoSensores(new LinkedList<>());
    }

    @GET
    @Path("/available")
    public List<SensorDTO> getAllSensoresAvailable() {
        if (securityContext.isUserInRole("Operador"))
            return toDTOsNoSensores(sensorBean.getAllAvailable(SensorType.OPERADOR));
        if (securityContext.isUserInRole("Fabricante"))
            return toDTOsNoSensores(sensorBean.getAllAvailable(SensorType.FABRICANTE));
        return toDTOsNoSensores(new LinkedList<>());
    }

    @GET
    @Path("{id}")
    public SensorDTO getSensorById(@PathParam("id") int id) throws MyEntityNotFoundException {
        SensorDTO sensorDTO = toDTONoSensores(sensorBean.find(id));
        return sensorDTO;
    }

    @POST
    @Path("/")
    public Response createNewSensor(SensorDTO sensorDTO) {
        if (securityContext.isUserInRole("Operador"))
            sensorBean.create(sensorDTO.getNome(), sensorDTO.getDescricao(), SensorType.OPERADOR);
        else if (securityContext.isUserInRole("Fabricante"))
            sensorBean.create(sensorDTO.getNome(), sensorDTO.getDescricao(), SensorType.FABRICANTE);
        else
            return Response.status(Response.Status.FORBIDDEN).build();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    public Response updateSensor(@PathParam("id") int id, SensorDTO sensorDTO) throws MyEntityNotFoundException {
        boolean response = sensorBean.update(id, sensorDTO.getNome(), sensorDTO.getDescricao());
        if (!response) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteSensor(@PathParam("id") int id) throws MyEntityNotFoundException {
        boolean response = sensorBean.delete(id);
        if (!response) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }
}
