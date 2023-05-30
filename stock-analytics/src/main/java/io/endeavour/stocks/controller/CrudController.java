package io.endeavour.stocks.controller;

import io.endeavour.stocks.entity.PersonEntity;
import io.endeavour.stocks.service.CrudService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/crud")
public class CrudController {
    private final CrudService crudService;

    public CrudController(CrudService crudService) {
        this.crudService = crudService;
    }

    @GetMapping("/person")
    public List<PersonEntity> getPersons() {
        return crudService.getPersons();
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<PersonEntity> getPerson(@PathVariable("id") Integer id) {
        Optional<PersonEntity> optionalPerson = crudService.getPerson(id);
        return ResponseEntity.of(optionalPerson);
    }

    @PostMapping("/person")
    public PersonEntity savePerson(@RequestBody PersonEntity person) {
        if (person.getPersonId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return crudService.savePerson(person);
    }

    @PutMapping("/person/{id}")
    public PersonEntity updatePerson(@RequestBody PersonEntity person,
                                     @PathVariable("id") Integer id) {
        if (person.getPersonId() == null || !id.equals(person.getPersonId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return crudService.savePerson(person);
    }

    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable("id") Integer id) {
        crudService.deletePerson(id);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<String> handleException(EmptyResultDataAccessException e) {
        return ResponseEntity.badRequest().body("Id does not exist");
    }
}
