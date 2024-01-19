package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.CsvDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.ProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.EncomendaBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.providers.CSVExporter;
import pt.ipleiria.estg.dei.ei.dae.daebackend.providers.CSVImporter;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/csv")
@Produces({MediaType.TEXT_PLAIN})
@Consumes({MediaType.TEXT_PLAIN})
@Authenticated
@RolesAllowed({"Fabricante"})

public class CsvService {

    @EJB
    private EncomendaBean encomendaBean;

    @EJB
    private SensorBean sensorBean;

    @EJB
    private ProdutoBean produtoBean;

    private EncomendaDTO toDTO(Encomenda encomenda) {
        return new EncomendaDTO(
                encomenda.getId(),
                encomenda.getOperador(),
                encomenda.getConsumidor(),
                encomenda.getStatus(),
                encomenda.getData(),
                encomenda.getLocalizacao());
    }

    private SensorDTO toDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getTypeOfSensor()
        );
    }

    private ProdutoDTO toDTO(Produto produto){
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getDescricao(),
                produto.getQuantidade(),
                produto.getTypeOfSensor()
        );
    }

    private List<EncomendaDTO> toDTOs(List<Encomenda> encomendas) {
        return encomendas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<SensorDTO> toDTOsSensors(List<Sensor> sensors) {
        return sensors.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<ProdutoDTO> toDTOsProdutos(List<Produto> produtos) {
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/encomendas")
    public Response getEncomendasCSV() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<EncomendaDTO> encomendaDTOS = toDTOs(encomendaBean.getAll());
        CSVExporter<EncomendaDTO> exporter = new CSVExporter<EncomendaDTO>();
        return Response.ok(exporter.generateCSV(encomendaDTOS, new EncomendaDTO())).build();
    }
    @GET
    @Path("/sensores")
    public Response getSensoresCSV() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<SensorDTO> sensorDTOS = toDTOsSensors(sensorBean.getAll());
        CSVExporter<SensorDTO> exporter = new CSVExporter<SensorDTO>();
        return Response.ok(exporter.generateCSV(sensorDTOS, new SensorDTO())).build();
    }

    @POST
    @Path("/sensores")
    public Response createSensoresCSV(CsvDTO csvDTO) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        CSVImporter<SensorDTO> importer = new CSVImporter<>();
        List<SensorDTO> sensorDTOS = importer.importManyFromCSV(csvDTO.getCsv(), new SensorDTO());
        for (SensorDTO sensorDTO : sensorDTOS) {
            sensorBean.create(sensorDTO.getTypeOfSensor());
        }
        return Response.ok().build();
    }


    @GET
    @Path("/produtos")
    public Response getProdutosCSV() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<ProdutoDTO> produtoDTOS = toDTOsProdutos(produtoBean.getAll());
        CSVExporter<ProdutoDTO> exporter = new CSVExporter<ProdutoDTO>();
        return Response.ok(exporter.generateCSV(produtoDTOS, new ProdutoDTO())).build();
    }

}
