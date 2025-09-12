package br.edu.atitus.greeting_service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.greeting_service.configs.GreetingConfig;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @Value("${greeting-service.greeting}")
    private String greeting;

    @Value("${greeting-service.default-name}")
    private String defaultName;

    private final GreetingConfig config;

    public GreetingController(GreetingConfig config) {
        super();
        this.config = config;
    }

    // Mantém o método existente com @RequestParam
    @GetMapping
    public ResponseEntity<String> greet(
            @RequestParam(required = false) String name){
        String greetingReturn = config.getGreeting();
        String nameReturn = name != null ? name : config.getDefaultName();
        String textReturn = String.format("%s, %s!!!", greetingReturn, nameReturn);

        return ResponseEntity.ok(textReturn);
    }

    // Novo endpoint GET /greeting/{name}
    @GetMapping("/{name}")
    public ResponseEntity<String> greetPath(@PathVariable String name) {
        String greetingReturn = config.getGreeting();
        String nameReturn = (name != null && !name.isEmpty()) ? name : config.getDefaultName();
        String textReturn = String.format("%s, %s!!!", greetingReturn, nameReturn);

        return ResponseEntity.ok(textReturn);
    }

    // Novo endpoint POST /greeting
    @PostMapping
    public ResponseEntity<String> greetPost(@RequestBody NameRequest request) {
        System.out.println("Recebido: " + request.getName());
        String name = request.getName();
        String greetingReturn = config.getGreeting();
        String nameReturn = (name != null && !name.isEmpty()) ? name : config.getDefaultName();
        String textReturn = String.format("%s, %s!!!", greetingReturn, nameReturn);

        return ResponseEntity.ok(textReturn);
    }
}
