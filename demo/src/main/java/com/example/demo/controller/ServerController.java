package com.example.demo.controller;

import com.example.demo.model.Server;
import com.example.demo.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class ServerController {
    
    @Autowired
    private ServerService serverService;
    
    @PostMapping
    public ResponseEntity<Server> createServer(@RequestBody Server server) {
        try {
            Server createdServer = serverService.createServer(server);
            return ResponseEntity.ok(createdServer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Server>> listServers() {
        List<Server> servers = serverService.listServers();
        return ResponseEntity.ok(servers);
    }
    
    @PutMapping("/{id}/rename")
    public ResponseEntity<Server> renameServer(@PathVariable Long id, @RequestParam String newName) {
        try {
            Server server = serverService.renameServer(id, newName);
            return ResponseEntity.ok(server);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}/status")
    public ResponseEntity<Boolean> getServerStatus(@PathVariable Long id) {
        try {
            Boolean status = serverService.getServerStatus(id);
            return ResponseEntity.ok(status);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/start")
    public ResponseEntity<Server> startServer(@PathVariable Long id) {
        try {
            Server server = serverService.startServer(id);
            return ResponseEntity.ok(server);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/stop")
    public ResponseEntity<Server> stopServer(@PathVariable Long id) {
        try {
            Server server = serverService.stopServer(id);
            return ResponseEntity.ok(server);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServer(@PathVariable Long id) {
        try {
            serverService.deleteServer(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}