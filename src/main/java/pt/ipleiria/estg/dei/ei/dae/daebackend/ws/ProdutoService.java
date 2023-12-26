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
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.ProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.ProdutoFisicoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ProdutoFisicoBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.ProdutoFisico;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.SensorType;
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
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getDescricao()
        );
    }

    private List<ProdutoDTO> toDTOsProdutos(List<Produto> produtos) {
        return produtos.stream().map(this::toDTOProduto).collect(Collectors.toList());
    }

    private ProdutoFisicoDTO toDTOProdutoFisico(ProdutoFisico produtoFisico) {
        return new ProdutoFisicoDTO(
                produtoFisico.getReferencia()
        );
    }

    private List<ProdutoFisicoDTO> toDTOsProdutosFisicos(List<ProdutoFisico> produtosFisicos) {
        return produtosFisicos.stream().map(this::toDTOProdutoFisico).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ProdutoDTO> getAllProdutos() {
        return toDTOsProdutos(produtoBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getProdutoDetails(@PathParam("id") int id) throws MyEntityNotFoundException {
        ProdutoDTO produtoDTO = toDTOProduto(produtoBean.find(id));
        List<ProdutoFisicoDTO> produtosFisicoDTO = toDTOsProdutosFisicos(produtoFisicoBean.findProdutosFisicosByProdutoId(id));
        JsonObjectBuilder response = Json.createObjectBuilder();
        response.add("id", produtoDTO.getId());
        response.add("nome", produtoDTO.getNome());
        response.add("categoria", produtoDTO.getCategoria());
        response.add("descricao", produtoDTO.getDescricao());
        JsonArrayBuilder produtosFisicos = Json.createArrayBuilder();
        for (ProdutoFisicoDTO produtoFisicoDTO : produtosFisicoDTO) {
            JsonObjectBuilder produtoFisico = Json.createObjectBuilder();
            produtoFisico.add("referencia", produtoFisicoDTO.getReferencia());
            produtosFisicos.add(produtoFisico);
        }
        response.add("produtosFisicos", produtosFisicos);
        return Response.status(Response.Status.OK).entity(response.build()).build();
    }

    @POST
    @Path("/")
    public Response createNewProduto(ProdutoDTO produtoDTO) {
        produtoBean.create(produtoDTO.getNome(), produtoDTO.getCategoria(), produtoDTO.getDescricao());
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

}
