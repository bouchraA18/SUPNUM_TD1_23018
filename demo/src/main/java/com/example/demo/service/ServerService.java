package com.example.demo.service;

import com.example.demo.model.Server;
import com.example.demo.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServerService {
    
    @Autowired
    private ServerRepository serverRepository;
    
    public Server createServer(Server server) {
        if (serverRepository.existsByName(server.getName())) {
            throw new RuntimeException("Un serveur avec ce nom existe déjà");
        }
        return serverRepository.save(server);
    }
    
    public List<Server> listServers() {
        return serverRepository.findAll();
    }
    
    public Server renameServer(Long id, String newName) {
        Optional<Server> serverOpt = serverRepository.findById(id);
        if (serverOpt.isPresent()) {
            Server server = serverOpt.get();
            server.setName(newName);
            return serverRepository.save(server);
        }
        throw new RuntimeException("Serveur non trouvé avec l'ID: " + id);
    }
    
    public Boolean getServerStatus(Long id) {
        Optional<Server> serverOpt = serverRepository.findById(id);
        if (serverOpt.isPresent()) {
            return serverOpt.get().getStatus();
        }
        throw new RuntimeException("Serveur non trouvé avec l'ID: " + id);
    }
    
    public Server startServer(Long id) {
        Optional<Server> serverOpt = serverRepository.findById(id);
        if (serverOpt.isPresent()) {
            Server server = serverOpt.get();
            server.setStatus(true);
            return serverRepository.save(server);
        }
        throw new RuntimeException("Serveur non trouvé avec l'ID: " + id);
    }
    
    public Server stopServer(Long id) {
        Optional<Server> serverOpt = serverRepository.findById(id);
        if (serverOpt.isPresent()) {
            Server server = serverOpt.get();
            server.setStatus(false);
            return serverRepository.save(server);
        }
        throw new RuntimeException("Serveur non trouvé avec l'ID: " + id);
    }
    
    public void deleteServer(Long id) {
        Optional<Server> serverOpt = serverRepository.findById(id);
        if (serverOpt.isPresent()) {
            Server server = serverOpt.get();
            if (server.getStatus()) {
                throw new RuntimeException("Impossible de supprimer un serveur en cours d'exécution");
            }
            serverRepository.delete(server);
        } else {
            throw new RuntimeException("Serveur non trouvé avec l'ID: " + id);
        }
    }
}