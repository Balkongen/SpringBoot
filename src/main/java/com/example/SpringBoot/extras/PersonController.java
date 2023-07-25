package com.example.SpringBoot.extras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

//    private final Logger log = LoggerFactory.getLogger(getClass());


    private Person findPersonByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Person.class);
    }

    @GetMapping("/show_all")
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/get_person/{name}")
    public ResponseEntity<Person> getPerson(@PathVariable String name) {
        Person person = findPersonByName(name);

        if (person == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
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

    @RequestMapping(value = "/createDefault", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Person> addPerson() {
        try {
            Person person = new Person("Ed", 76);
            personRepository.save(person);
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/createNew", produces = "application/json" ,method = {RequestMethod.GET, RequestMethod.POST})
    public Person addPersonWithParameters(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @RequestMapping(value = "/delete/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deletePerson(@PathVariable String name) {
        Person person = findPersonByName(name);

        if (person == null) {
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        personRepository.delete(person);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
