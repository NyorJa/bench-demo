package com.example.demo.service.impl;

import com.example.demo.exceptions.ExistingEmailException;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person findByName(String name) {
        return personRepository.findByName(name).orElse(null);
    }

    @Override
    public Optional<Person> findByAddress(String address) {
        return Optional.empty();
    }

    @Override
    public void createPerson(Person person) throws ExistingEmailException {
        Optional<Person> p = personRepository.findByEmail(person.getEmail());
        if (p.isPresent()) {
            throw new ExistingEmailException("Email: " + p.get().getEmail() + " is existing");
        }
        personRepository.save(person);
    }
}
