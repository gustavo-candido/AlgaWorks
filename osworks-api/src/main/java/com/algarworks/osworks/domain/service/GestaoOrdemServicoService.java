package com.algarworks.osworks.domain.service;

import com.algarworks.osworks.api.model.Comentario;
import com.algarworks.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.algarworks.osworks.domain.exception.NegocioException;
import com.algarworks.osworks.domain.model.Cliente;
import com.algarworks.osworks.domain.model.OrdemServico;
import com.algarworks.osworks.domain.model.StatusOrdemServico;
import com.algarworks.osworks.domain.repository.ClienteRepository;
import com.algarworks.osworks.domain.repository.ComentarioRepository;
import com.algarworks.osworks.domain.repository.OrdemServicoRepository;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestaoOrdemServicoService {
    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public OrdemServico criar(OrdemServico ordemServico) {
        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                          .orElseThrow(() -> new NegocioException("Cliente não encontrado"));

        ordemServico.setCliente(cliente);
        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());

        return ordemServicoRepository.save(ordemServico);
    }

    public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
        OrdemServico ordemServico = buscarOrdemServico(ordemServicoId);
        Comentario comentario = new Comentario();
        comentario.setDataEnvio(OffsetDateTime.now());
        comentario.setDescricao(descricao);
        comentario.setOrdemServico(ordemServico);

        return comentarioRepository.save(comentario);
    }

    public void finalizar(Long ordemServicoId) {
        OrdemServico ordemServico = buscarOrdemServico(ordemServicoId);

        ordemServico.finalizar();

        ordemServicoRepository.save(ordemServico);
    }

    private OrdemServico buscarOrdemServico(Long ordemServicoId) {
        return ordemServicoRepository.findById(ordemServicoId)
            .orElseThrow(
                () -> new EntidadeNaoEncontradaException("Ordem de servico não encontrada"));
    }
}
