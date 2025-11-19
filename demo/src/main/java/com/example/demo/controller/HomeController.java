package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "<h1>🚀 Server Monitoring API</h1>" +
               "<p>Bienvenue dans l'API de gestion des serveurs</p>" +
               "<p>Accédez à la documentation : <a href='/swagger-ui.html'>Swagger UI</a></p>" +
               "<p>Endpoints disponibles :</p>" +
               "<ul>" +
               "<li>POST /api/servers - Créer un serveur</li>" +
               "<li>GET /api/servers - Lister les serveurs</li>" +
               "<li>PUT /api/servers/{id}/start - Démarrer un serveur</li>" +
               "<li>PUT /api/servers/{id}/stop - Arrêter un serveur</li>" +
               "<li>DELETE /api/servers/{id} - Supprimer un serveur</li>" +
               "</ul>";
    }
}