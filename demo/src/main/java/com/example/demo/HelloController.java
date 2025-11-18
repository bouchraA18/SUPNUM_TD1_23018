package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String accueil() {
        return "🎉 Bienvenue dans mon application Spring Boot ! ✅";
    }
    
    @GetMapping("/bonjour")
    public String bonjour() {
        return "👋 Bonjour ! Comment allez-vous ?";
    }
    
    @GetMapping("/heure")
    public String heure() {
        return "⏰ Il est actuellement : " + java.time.LocalTime.now();
    }
    
    @GetMapping("/test")
    public String test() {
        return "✅ Le serveur fonctionne parfaitement !";
    }
}
