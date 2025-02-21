/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 12:17:59
 * @ Modified time: 2025-02-10 11:25:26
 */

package com.molerodev.clase.demo_spring.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.molerodev.clase.demo_spring.dto.ClienteDTO;
import com.molerodev.clase.demo_spring.entity.Cliente;
import com.molerodev.clase.demo_spring.repository.ClienteRepository;


@Service
public class ClienteService implements IService<Cliente, ClienteDTO>{


     @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelmapper;


    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("No existe ese cliente"));
    }

    @Override
    public List<Cliente> findAll() {
       return clienteRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
         if(clienteRepository.existsById(id)){
            clienteRepository.deleteById(id);
         }
    }

    @Override
    public void deleteAll() {
        clienteRepository.deleteAll();
    }

    @Override
    public Cliente update(Cliente cliente) {
        Cliente clienteToUpdate = clienteRepository.findById(cliente.getIdCli())
        .orElseThrow(() -> new RuntimeException("No puede ser el cliente nulo"));
        
        modelmapper.map(cliente, clienteToUpdate);

        return clienteToUpdate;
    }

    
    @Override
    public ClienteDTO convertToDTO (Cliente cliente){

       return modelmapper.map(cliente, ClienteDTO.class);
    }

    @Override
    public Cliente convertToEntity(ClienteDTO dto) {
       
        return modelmapper.map(dto, Cliente.class);
    }


    
    public Optional<Cliente> findByNombre(String nombre){
        return clienteRepository.findByNombre(nombre);
    }

    public Optional<Cliente> findByTelefono(String telefono){
        return clienteRepository.findByTelefono(telefono);
    }
    
    

}
