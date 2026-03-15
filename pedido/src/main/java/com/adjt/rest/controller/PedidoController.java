package com.adjt.rest.controller;

import com.adjt.core.model.Pedido;
import com.adjt.core.usecase.AtualizarPedidoUseCase;
import com.adjt.core.usecase.CancelarPedidoUseCase;
import com.adjt.core.usecase.CriarPedidoUseCase;
import com.adjt.core.usecase.ObterPedidoUseCase;
import com.adjt.gprc.ClienteGrpcUseCase;
import com.adjt.pgto.PagamentoService;
import com.adjt.rest.dto.request.PedidoRequest;
import com.adjt.rest.dto.response.PedidoResponse;
import com.adjt.rest.interceptor.UserContext;
import com.adjt.rest.mapper.PedidoRestMapper;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.Blocking;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pedido")
@RolesAllowed("ROLE_CLIENTE")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoController {

   private final AtualizarPedidoUseCase atualizarPedidoUseCase;
   private final CancelarPedidoUseCase cancelarPedidoUseCase;
   private final CriarPedidoUseCase criarPedidoUseCase;
   private final ObterPedidoUseCase obterPedidoUseCase;
   private final PedidoRestMapper pedidoRestMapper;

   private final ClienteGrpcUseCase clienteGrpcUseCase;
   private final UserContext userContext;
   private final PagamentoService pagamentoService;

   public PedidoController( AtualizarPedidoUseCase atualizarPedidoUseCase,
                            CancelarPedidoUseCase cancelarPedidoUseCase,
                           CriarPedidoUseCase criarPedidoUseCase,
                           ObterPedidoUseCase obterPedidoUseCase,
                           PedidoRestMapper pedidoRestMapper,
                           ClienteGrpcUseCase clienteGrpcUseCase,
                           UserContext userContext,
                           PagamentoService pagamentoService) {

      this.atualizarPedidoUseCase = atualizarPedidoUseCase;
      this.cancelarPedidoUseCase = cancelarPedidoUseCase;
      this.criarPedidoUseCase = criarPedidoUseCase;
      this.obterPedidoUseCase = obterPedidoUseCase;
      this.pedidoRestMapper = pedidoRestMapper;
      this.pagamentoService = pagamentoService;

      this.clienteGrpcUseCase = clienteGrpcUseCase;
      this.userContext = userContext;
   }

   @POST
   @Path("/criar")
   @Blocking
   public Response criar(@Valid PedidoRequest request) {

      try {

         Boolean valido = this.clienteGrpcUseCase.validarUsuarioLogado(
                 userContext.getKeycloakId(),
                 request.idCliente
         ).await().indefinitely();

         if (!Boolean.TRUE.equals(valido)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
         }

         Pedido pedido = this.pedidoRestMapper.toModel(request);
         Pedido salvo = this.criarPedidoUseCase.run(pedido);

         try (Response pgtoResponse = pagamentoService.processarPagamento(
                 salvo.getValorTotal().longValue(),
                 salvo.getId().toString(),
                 salvo.getIdCliente().toString()
         ).await().indefinitely()) {

            int statusPagamento = pgtoResponse.getStatus();

            salvo.setStatusCode(statusPagamento);
            this.atualizarPedidoUseCase.run(salvo.getId(), statusPagamento);
         }

         PedidoResponse response = this.pedidoRestMapper.toResponse(salvo);
         return Response.status(Response.Status.CREATED).entity(response).build();

      } catch (Exception e) {

         Log.error("Falha ao processar pedido: " + e.getMessage());
         return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                 .entity("Serviço de pagamento indisponível ou erro interno.")
                 .build();
      }
   }

   @GET
   @Path("obter/{id}")
   public Response obter(@PathParam("id") Long id) {
      Pedido pedido = this.obterPedidoUseCase.run(id);
      PedidoResponse response = this.pedidoRestMapper.toResponse(pedido);

      return Response.ok(response).build();
   }

   @DELETE
   @Path("cancelar/{id}")
   public Response cancelar(@PathParam("id") Long id) {
      this.cancelarPedidoUseCase.run(id);
      return Response.noContent().build();
   }
}
