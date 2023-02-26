package com.github.kalininaleksandrv.makejpagreateagain.controller;

import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.model.ContactInfo;
import com.github.kalininaleksandrv.makejpagreateagain.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/")
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @GetMapping(path = "client/{id}")
    public ResponseEntity<Client> clientById(@PathVariable Integer id) {
        log.info("### requested client with id {}", id);
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

    @PatchMapping(path = "client")
    public ResponseEntity<Client> newClientWithAge(@RequestBody Client client, int age) {
        var savedPerson = clientService.saveClientWithAge(client, age);
        return new ResponseEntity<>(savedPerson, HttpStatus.OK);
    }

    @GetMapping(path = "contacts")
    public ResponseEntity<List<ContactInfo>> contacts(){
        return new ResponseEntity<>(clientService.findAllContacts(), HttpStatus.OK);
    }
}
