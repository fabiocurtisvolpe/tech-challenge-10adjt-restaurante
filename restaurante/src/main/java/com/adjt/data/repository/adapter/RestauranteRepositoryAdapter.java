package com.adjt.data.repository.adapter;

import com.adjt.core.model.Cardapio;
import com.adjt.core.model.Endereco;
import com.adjt.core.model.Restaurante;
import com.adjt.core.port.RestaurantePort;
import com.adjt.core.util.MensagemUtil;
import com.adjt.data.entity.CardapioEntity;
import com.adjt.data.entity.EnderecoEntity;
import com.adjt.data.entity.RestauranteEntity;
import com.adjt.data.mapper.CardapioMapper;
import com.adjt.data.mapper.EnderecoMapper;
import com.adjt.data.mapper.RestauranteMapper;
import com.adjt.data.repository.jpa.RestauranteRepository;
import com.adjt.data.repository.jpa.TipoCozinhaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class RestauranteRepositoryAdapter implements RestaurantePort<Restaurante> {

    private final RestauranteRepository repository;
    private final TipoCozinhaRepository tipoCozinhaRepository;

    private final RestauranteMapper mapper;
    private final EnderecoMapper enderecoMapper;
    private final CardapioMapper cardapioMapper;

    public RestauranteRepositoryAdapter(RestauranteRepository repository,
                                        TipoCozinhaRepository tipoCozinhaRepository,
                                        RestauranteMapper mapper,
                                        EnderecoMapper enderecoMapper,
                                        CardapioMapper cardapioMapper) {
        this.repository = repository;
        this.tipoCozinhaRepository = tipoCozinhaRepository;

        this.mapper = mapper;
        this.enderecoMapper = enderecoMapper;
        this.cardapioMapper = cardapioMapper;
    }

    @Transactional
    @Override
    public Restaurante criar(Restaurante model) {

        RestauranteEntity entity = this.mapper.toEntity(model);
        this.repository.persistAndFlush(entity);
        return this.mapper.toModel(entity);
    }

    @Transactional
    @Override
    public Restaurante atualizar(Restaurante model) {

        RestauranteEntity entity = repository.findById(model.getId());
        Objects.requireNonNull(entity, MensagemUtil.NAO_FOI_POSSIVEL_EXECUTAR_OPERACAO);

        entity.nome = model.getNome();
        entity.descricao = model.getDescricao();
        entity.horarioFuncionamento = model.getHorarioFuncionamento();
        entity.tipoCozinha = tipoCozinhaRepository.findById(model.getId());

        this.atualizarEnderecos(entity, model.getEnderecos());
        this.atualizarCardapio(entity, model.getCardapios());

        return mapper.toModel(entity);
    }

    private void atualizarEnderecos(RestauranteEntity entity, List<Endereco> enderecosModel) {

        if ((enderecosModel == null) || (enderecosModel.isEmpty())) return;

        entity.enderecos.removeIf(existing ->
                enderecosModel.stream().noneMatch(m -> m.getId() != null && m.getId().equals(existing.id))
        );

        for (Endereco model : enderecosModel) {
            if (model.getId() != null) {
                entity.enderecos.stream()
                        .filter(e -> e.id.equals(model.getId()))
                        .findFirst()
                        .ifPresent(existing -> {
                            existing.rua = model.getRua();
                            existing.bairro = model.getBairro();
                            existing.cep = model.getCep();
                            existing.complemento = model.getComplemento();
                            existing.numero = model.getNumero();
                            existing.cidade = model.getCidade();
                            existing.uf = model.getUf();
                            existing.observacao = model.getObservacao();
                        });
            } else {
                EnderecoEntity novaEntity = enderecoMapper.toEntity(model);
                entity.addEndereco(novaEntity);
            }
        }
    }

    private void atualizarCardapio(RestauranteEntity entity, List<Cardapio> cardapiosModel) {

        if ((cardapiosModel == null) || (cardapiosModel.isEmpty())) return;

        entity.cardapios.removeIf(existing ->
                cardapiosModel.stream().noneMatch(m -> m.getId() != null && m.getId().equals(existing.id))
        );

        for (Cardapio model : cardapiosModel) {
            if (model.getId() != null) {
                entity.cardapios.stream()
                        .filter(e -> e.id.equals(model.getId()))
                        .findFirst()
                        .ifPresent(existing -> {
                            existing.nome = model.getNome();
                            existing.descricao = model.getDescricao();
                            existing.foto = model.getFoto();
                            existing.disponivel = model.getDisponivel();
                        });
            } else {
                CardapioEntity novaEntity = cardapioMapper.toEntity(model);
                entity.addCardapio(novaEntity);
            }
        }
    }

    @Transactional
    @Override
    public Boolean excluir(Long id) {
        return this.repository.deleteById(id);
    }

    @Transactional
    @Override
    public Restaurante obterPorId(Long id) {
        RestauranteEntity entity = this.repository.findById(id);
        return mapper.toModel(entity);
    }

    @Override
    public List<Restaurante> listar(int page, int size, Long idTipoCozinha, String nome) {
        StringBuilder query = new StringBuilder("1=1");

        // Construção dinâmica de HQL
        java.util.Map<String, Object> params = new java.util.HashMap<>();

        if (idTipoCozinha != null) {
            query.append(" and tipoCozinha.id = :idTipoCozinha");
            params.put("idTipoCozinha", idTipoCozinha);
        }

        if (nome != null && !nome.isEmpty()) {
            query.append(" and nome ilike :nome"); // ilike para busca case-insensitive no Postgres
            params.put("nome", "%" + nome + "%");
        }

        return this.repository.find(query.toString(), params)
                .page(page, size)
                .list()
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}
