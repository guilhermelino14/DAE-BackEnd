package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ProdutoFisicoBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/produtos")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Fabricante"})
public class ProdutoService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private ProdutoBean produtoBean;

    @EJB
    private ProdutoFisicoBean produtoFisicoBean;

    private ProdutoDTO toDTOProduto(Produto produto) {
        var dto =  new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getDescricao(),
                produto.getQuantidade(),
                produto.getTypeOfSensor(),
                produto.isLiquido()
        );
        dto.produtoFisicos = produto.getProdutoFisicos().stream().map(this::toDTOProdutoFisico).collect(Collectors.toList());
        dto.fabricante = produto.getFabricante();
        dto.sensorRoles = produto.getSensorRoles().stream().map(this::toDTOSensorRole).collect(Collectors.toList());
        return dto;
    }

    private List<ProdutoDTO> toDTOsProdutos(List<Produto> produtos) {
        return produtos.stream().map(this::toDTOProduto).collect(Collectors.toList());
    }

    private ProdutoFisicoDTO toDTOProdutoFisico(ProdutoFisico produtoFisico) {
        var dto = new ProdutoFisicoDTO(
                produtoFisico.getReferencia()
        );
        dto.embalagensProduto = toDTOsEmbalagensProduto(produtoFisico.getEmbalagensProduto());
        if (produtoFisico.getEncomenda() != null) {
            dto.encomenda = toDTOEncomenda(produtoFisico.getEncomenda());
        }
        return dto;
    }

    private EmbalagemProdutoDTO toDTOEmbalagemProduto(EmbalagemProduto embalagemProduto) {
        var dto = new EmbalagemProdutoDTO(
                embalagemProduto.getId(),
                embalagemProduto.getNome(),
                embalagemProduto.getAltura(),
                embalagemProduto.getLargura()
        );
        return dto;
    }

    private SensorRoleDTO toDTOSensorRole(SensorRole sensorRole) {
        var dto = new SensorRoleDTO(
                sensorRole.getId(),
                sensorRole.getTypeOfSensor(),
                sensorRole.getVal_max(),
                sensorRole.getVal_min()
        );
        return dto;
    }

    private EncomendaDTO toDTOEncomenda(Encomenda encomenda) {
        var dto = new EncomendaDTO(
                encomenda.getId(),
                encomenda.getOperador(),
                encomenda.getConsumidor(),
                encomenda.getStatus(),
                encomenda.getData(),
                encomenda.getLocalizacao()
        );
        return dto;
    }

    private List<EmbalagemProdutoDTO> toDTOsEmbalagensProduto(List<EmbalagemProduto> embalagensProduto) {
        return embalagensProduto.stream().map(this::toDTOEmbalagemProduto).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    @RolesAllowed({"Fabricante"})
    public List<ProdutoDTO> getAllProdutos() {
        return toDTOsProdutos(produtoBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getProdutoDetails(@PathParam("id") int id) throws MyEntityNotFoundException {
        ProdutoDTO produtoDTO = toDTOProduto(produtoBean.find(id));
        return Response.status(Response.Status.OK).entity(produtoDTO).build();
    }

    @POST
    @Path("/")
    public Response createNewProduto(ProdutoDTO produtoDTO) throws MyEntityNotFoundException {
        produtoBean.create(produtoDTO.getNome(), produtoDTO.getCategoria(), produtoDTO.getDescricao(), produtoDTO.getQuantidade(),produtoDTO.getTypeOfSensor(), securityContext.getUserPrincipal().getName(), produtoDTO.isLiquido());
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteSensor(@PathParam("id") int id) throws MyEntityNotFoundException, MyConstraintViolationException {
        produtoBean.delete(id);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("{id}/addStock/{total}")
    public Response createProdutosFisicosForProduto(@PathParam("id") int id, @PathParam("total") int total) throws MyEntityNotFoundException {
        produtoBean.addToStock(id, total);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{id}/embalagens")
    public Response getProdutoEmbalagens(@PathParam("id") int id) throws MyEntityNotFoundException {
        ProdutoFisicoDTO produtoFisicoDTO = toDTOProdutoFisico(produtoFisicoBean.find(id));
        return Response.status(Response.Status.OK).entity(produtoFisicoDTO.getEmbalagensProduto()).build();
    }

    @PATCH
    @Path("{id}/embalagens/{embalagemId}")
    public Response associarEmbalagemProduto(@PathParam("id") int id, @PathParam("embalagemId") int embalagemId) throws MyEntityNotFoundException {
        ProdutoFisicoDTO produtoFisicoDTO = toDTOProdutoFisico(produtoFisicoBean.find(id));
        produtoFisicoBean.addEmbalagemProduto(produtoFisicoDTO.getReferencia(), embalagemId);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}/embalagens/{embalagemId}")
    public Response desassociarEmbalagemProduto(@PathParam("id") int id, @PathParam("embalagemId") int embalagemId) throws MyEntityNotFoundException {
        ProdutoFisicoDTO produtoFisicoDTO = toDTOProdutoFisico(produtoFisicoBean.find(id));
        produtoFisicoBean.removeProdutoEmbalagem(produtoFisicoDTO.getReferencia(), embalagemId);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("{id}/createRole")
    public Response createSensorRole(@PathParam("id") int id, SensorRoleDTO sensorRoleDTO) throws MyEntityNotFoundException {
        produtoBean.createSensorRole(id, sensorRoleDTO.getTypeOfSensor(), sensorRoleDTO.getVal_max(), sensorRoleDTO.getVal_min());
        return Response.ok("Sensor role created").build();
    }

}
