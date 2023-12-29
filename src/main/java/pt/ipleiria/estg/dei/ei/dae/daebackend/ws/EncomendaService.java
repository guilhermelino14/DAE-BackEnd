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
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.ProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ConsumidorBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.EncomendaBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.OperadorBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.Date;
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
                encomenda.getConsumidor(),
                encomenda.getDate(),
                encomenda.getStatus()
        );
    }

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

        encomendaBean.create(operadorFinded, consumidorFinded, new Date(), EncomendaStatus.PENDENTE);

        // retrun a response with status 201 and the newly created encomenda
        return Response.ok("Encomenda criada com sucesso!").build();
    }

    @GET
    @Path("{id}")
    public Response getEncomendasDetails(@PathParam("id") int id) throws MyEntityNotFoundException {
        EncomendaDTO encomendaDTO = toDTO(encomendaBean.find(id));
        List<ProdutoDTO> produtosDTO = toDTOsProdutos(encomendaDTO.getProdutos());
        JsonObjectBuilder response = Json.createObjectBuilder();
        response.add("id", encomendaDTO.getId());
        response.add("date", encomendaDTO.getDate().toString());
        response.add("status", encomendaDTO.getStatus().toString());
        JsonArrayBuilder produtos = Json.createArrayBuilder();
        for (ProdutoDTO produtoDTO : produtosDTO) {
            JsonObjectBuilder produtoFisico = Json.createObjectBuilder();
            produtoFisico.add("id", produtoDTO.getId());
            produtoFisico.add("nome", produtoDTO.getNome());
            produtoFisico.add("categoria", produtoDTO.getCategoria());
            produtoFisico.add("descricao", produtoDTO.getDescricao());
            produtos.add(produtoFisico);
        }
        response.add("produtos", produtos);
        return Response.status(Response.Status.OK).entity(response.build()).build();
    }
}
