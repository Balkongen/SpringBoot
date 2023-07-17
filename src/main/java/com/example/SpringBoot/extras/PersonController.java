package com.example.SpringBoot.extras;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    // TODO test post
//    @PostMapping("/add")
//    public String addPerson(@RequestBody Person person) {
//        personRepository.save(person);
//        return "Saved";
//    }
}
