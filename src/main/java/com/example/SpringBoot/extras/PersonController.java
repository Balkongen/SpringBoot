package com.example.SpringBoot.extras;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;


    @GetMapping("/show_all")
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/show_ages")
    public int getAges() {
        List<Person> persons = personRepository.findAll();

        int totAges = 0;
        for (Person p : persons) {
            totAges += p.getAge();
        }
        return totAges;
    }

    @RequestMapping(value = "/create", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Person> addPerson() {
        try {
            Person person = new Person("Ed", 76);
            personRepository.save(person);
            return new ResponseEntity<>(person, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.err.println("Error");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
