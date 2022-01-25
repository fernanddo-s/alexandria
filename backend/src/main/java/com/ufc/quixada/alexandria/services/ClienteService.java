package com.ufc.quixada.alexandria.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufc.quixada.alexandria.dto.ClienteDTO;
import com.ufc.quixada.alexandria.dto.ClienteUpdateForm;
import com.ufc.quixada.alexandria.entities.Cliente;
import com.ufc.quixada.alexandria.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClienteDTO> findAll(Pageable pageable) {
		Page<Cliente> result = repository.findAll(pageable);
		Page<ClienteDTO> page = result.map(x -> new ClienteDTO(x));
		return page;
	}
	
	@Transactional(readOnly = true)
	public ClienteDTO findById(Long id) {
		Cliente result = repository.findById(id).get();
		ClienteDTO dto = new ClienteDTO(result);
		return dto;
	}
	
	@Transactional(readOnly = true)
	public ClienteDTO salvar(ClienteDTO dto) {
		
        Cliente cliente;
        
        	cliente = new Cliente();
        	cliente.setNome(dto.getNome());
        	cliente.setCpf(dto.getCpf());
        	cliente.setEndereco(dto.getEndereco());
        	cliente.setEmail(dto.getEmail());
        	cliente = repository.save(cliente);	
 
        return new ClienteDTO(cliente);
    }
	
	@Transactional
	public void deletar(Long id) {
			repository.deleteById(id);
	}
	
	@Transactional
	public ClienteDTO updateById(ClienteUpdateForm form, Long id) {
		Optional<Cliente> op = repository.findById(id);
		if(op.isPresent()) {
			Cliente obj = op.get();
			if (form.getNome() != null) {
				obj.setNome(form.getNome());
				obj.setEndereco(form.getEndereco());
			}
			repository.save(obj);
			return new ClienteDTO(obj);
		}
		return null;
	}
	
}
