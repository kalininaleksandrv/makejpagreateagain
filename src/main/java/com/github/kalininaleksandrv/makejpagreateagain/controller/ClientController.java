package com.github.kalininaleksandrv.makejpagreateagain.controller;

import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/")
public class ClientController {

    private final ClientService clientService;

    @GetMapping(path = "client/{id}")
    public ResponseEntity<Client> clientById(@PathVariable Integer id) {
        var foundedClient = clientService.findClientById(id);
        return foundedClient.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NO_CONTENT));
    }

    @GetMapping(path = "clients")
    public ResponseEntity<Iterable<Client>> clients() {
        var foundedClients = clientService.findAll();
        long count = StreamSupport.stream(foundedClients.spliterator(), false).count();
        if(count==0){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(foundedClients, HttpStatus.OK);
    }

    @PostMapping(path = "client")
    public ResponseEntity<Client> newClient(@RequestBody Client client) {
        var savedPerson = clientService.saveClient(client);
        return new ResponseEntity<>(savedPerson, HttpStatus.OK);
    }
}
