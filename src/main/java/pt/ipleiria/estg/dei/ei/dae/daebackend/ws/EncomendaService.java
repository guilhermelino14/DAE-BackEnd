package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ConsumidorBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.EncomendaBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.OperadorBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ProdutoFisicoBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/encomendas")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Operador", "Consumidor", "Fabricante"})
public class EncomendaService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private EncomendaBean encomendaBean;
    @EJB
    private ConsumidorBean consumidorBean;
    @EJB
    private OperadorBean operadorBean;
    @EJB
    private ProdutoFisicoBean produtoFisicoBean;

    private EncomendaDTO toDTO(Encomenda encomenda) {
        var dto = new EncomendaDTO(
                encomenda.getId(),
                encomenda.getOperador(),
                encomenda.getConsumidor(),
                encomenda.getStatus(),
                encomenda.getData()
        );
        dto.produtosFisicos = encomenda.getProdutosFisicos().stream().map(this::toDTO).collect(Collectors.toList());
        dto.embalagensTransporte = encomenda.getEmbalagensTransporte().stream().map(this::toDTO).collect(Collectors.toList());
        return dto;
    }

    private ProdutoFisicoDTO toDTO(ProdutoFisico produtoFisico) {
        var dto = new ProdutoFisicoDTO(
                produtoFisico.getReferencia()
        );
        dto.produto = toDTO(produtoFisico.getProduto());
        dto.embalagensProduto = produtoFisico.getEmbalagensProduto().stream().map(this::toDTO).collect(Collectors.toList());
        return dto;
    }

    private ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getDescricao()
        );
    }

    private EmbalagemTransporteDTO toDTO(EmbalagemTransporte embalagemTransporte) {
        var dto = new EmbalagemTransporteDTO(
                embalagemTransporte.getId(),
                embalagemTransporte.getNome(),
                embalagemTransporte.getAltura(),
                embalagemTransporte.getLargura()
        );
        return dto;
    }

    private EmbalagemProdutoDTO toDTO(EmbalagemProduto embalagemProduto) {
        var dto = new EmbalagemProdutoDTO(
                embalagemProduto.getId(),
                embalagemProduto.getNome(),
                embalagemProduto.getAltura(),
                embalagemProduto.getLargura()
        );
        return dto;
    }

    private ObservacoesDTO toDTO(Observacoes observacoes) {
        var dto = new ObservacoesDTO(
                observacoes.getId(),
                observacoes.getObservacao(),
                observacoes.getData()
        );
        return dto;
    }


    private List<EncomendaDTO> toDTOs(List<Encomenda> encomendas) {
        return encomendas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<ProdutoFisicoDTO> toDTOsProdutoFisicos(List<ProdutoFisico> produtoFisicos) {
        return produtoFisicos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<ProdutoDTO> toDTOsProdutos(List<Produto> produtos) {
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<EmbalagemTransporteDTO> toDTOsEmbalagensTransporte(List<EmbalagemTransporte> embalagensTransporte) {
        return embalagensTransporte.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<EmbalagemProdutoDTO> toDTOsEmbalagensProduto(List<EmbalagemProduto> embalagensProduto) {
        return embalagensProduto.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<ObservacoesDTO> toDTOsObservacoes(List<Observacoes> observacoes) {
        return observacoes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<EncomendaDTO> getAll() {
        return toDTOs(encomendaBean.getAll());
    }

    @POST
    @Path("/")
    public Response createEncomenda(List<Produto> produtos) throws MyEntityNotFoundException{

        List<ProdutoFisico> produtosEncomenda= new ArrayList<>();
        List<Integer> produtoIds = new ArrayList<>();
        for (Produto produto : produtos) {
            produtoIds.add(produto.getId());
        }
        List<ProdutoFisico> produtosFisicosFound = produtoFisicoBean.findProdutosFisicosByProdutoIds(produtoIds);
        for (Produto produto : produtos){
            // ir a lista buscar o primeiro produto fisico com o id do produto
            for (ProdutoFisico produtoFisico : produtosFisicosFound){
                if (produtoFisico.getProduto().getId() == produto.getId()){
                    produtosEncomenda.add(produtoFisico);
                    produtosFisicosFound.remove(produtoFisico);
                    break;
                }
            }
        }
        if (produtosEncomenda.size() != produtoIds.size()){
                throw new MyEntityNotFoundException("Encomenda não pode ser criada, não existe stock para todos os produtos");
        }

//        SE EXISTIR STOCK, CRIAMOS A ENCOMENDA
        String username = securityContext.getUserPrincipal().getName();
        Consumidor consumidorFinded = consumidorBean.find(username);
        Operador operadorFinded = operadorBean.find("operador1");

        encomendaBean.create(operadorFinded, consumidorFinded);
        Encomenda encomenda = encomendaBean.getAll().get(encomendaBean.getAll().size() - 1);

        for (ProdutoFisico produtoFisico : produtosEncomenda) {
            encomendaBean.addProduct(encomenda.getId(), produtoFisico.getReferencia());
        }

        return Response.ok("Encomenda criada com sucesso!").build();




//        // CRIAR UMA LISTA DE ITEMS VAZIA
//        List<ProdutoFisico> produtoFisicos= new ArrayList<>();
//        // RECEBEMOS OS PRODUTOS POR PARAMETRO E VERIFICAMOS SE EXISTE STOCK (PRODUTOS FISICOS)
//        for (Produto produto : produtos) {
//            // FALTA VERIFICAR O STOCK
//            ProdutoFisico productFinded = produtoFisicoBean.findFirstProdutoFisicoByProdutoId(produto.getId());
//            if (productFinded == null) {
//                throw new MyEntityNotFoundException("Produto com o id " + produto.getId() + " não tem stock");
//            }
//            produtoFisicos.add(productFinded);
//            System.out.println(produto.getId());
//        }
//        //SE EXISTIR STOCK, CRIAMOS A ENCOMENDA
//        String username = securityContext.getUserPrincipal().getName();
//        Consumidor consumidorFinded = consumidorBean.find(username);
//        Operador operadorFinded = operadorBean.find("operador1");
//
//        encomendaBean.create(operadorFinded, consumidorFinded);
//        Encomenda encomenda = encomendaBean.getAll().get(encomendaBean.getAll().size() - 1);
//
//        //ADICIONAMOS OS PRODUTOS FISICOS A ENCOMENDA
//        for (ProdutoFisico produtoFisico : produtoFisicos) {
//            encomendaBean.addProduct(encomenda.getId(), produtoFisico.getReferencia());
//        }
//
//        return Response.ok("Encomenda criada com sucesso!").build();
    }

    @GET
    @Path("{id}")
    public Response getEncomendaDetails(@PathParam("id") int id) throws MyEntityNotFoundException {
        EncomendaDTO encomendaDTO = toDTO(encomendaBean.find(id));
        if (encomendaDTO == null){
            throw new MyEntityNotFoundException("Encomenda com o id " + id + " não existe");
        }
        return Response.status(Response.Status.OK).entity(encomendaDTO).build();
    }

    @PUT
    @Path("{id}/status/{status}")
    public Response updateEncomendaStatus(@PathParam("id") int id, @PathParam("status") EncomendaStatus status) throws MyEntityNotFoundException {
        Encomenda encomenda = encomendaBean.find(id);
        if (encomenda == null){
            throw new MyEntityNotFoundException("Encomenda com o id " + id + " não existe");
        }
        encomendaBean.updateStatus(id, status);
        return Response.ok("Encomenda atualizada com sucesso!").build();
    }

    @GET
    @Path("{id}/observacoes")
    public Response getEncomendaObservacoes(@PathParam("id") int id) throws MyEntityNotFoundException {
        Encomenda encomenda = encomendaBean.find(id);
        List<Observacoes> observacoes = new ArrayList<>();
        List<EmbalagemTransporte> embalagensTransporte = encomenda.getEmbalagensTransporte();
        List<ProdutoFisico> produtoFisicos = encomenda.getProdutosFisicos();
        for (EmbalagemTransporte embalagemTransporte : embalagensTransporte) {
            List<Sensor> sensores = embalagemTransporte.getSensores();
            for (Sensor sensor : sensores) {
                observacoes.addAll(sensor.getObservacoes());
            }
        }
        for (ProdutoFisico produtoFisico : produtoFisicos) {
            List<EmbalagemProduto> embalagensProduto = produtoFisico.getEmbalagensProduto();
            for (EmbalagemProduto embalagemProduto : embalagensProduto) {
                List<Sensor> sensores = embalagemProduto.getSensores();
                for (Sensor sensor : sensores) {
                    observacoes.addAll(sensor.getObservacoes());
                }
            }
        }
        return Response.ok(toDTOsObservacoes(observacoes)).build();
    }
}
