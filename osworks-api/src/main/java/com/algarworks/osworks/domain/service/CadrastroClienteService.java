package com.algarworks.osworks.domain.service;

import com.algarworks.osworks.domain.exception.NegocioException;
import com.algarworks.osworks.domain.model.Cliente;
import com.algarworks.osworks.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadrastroClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());

        if(clienteExistente != null && !clienteExistente.equals(cliente)) {
            throw new NegocioException("Já existe um cliente usando esse email");
        }

        return clienteRepository.save(cliente);
    }

    public void excluir(Long clienteId) {

        clienteRepository.deleteById(clienteId);
    }
}
