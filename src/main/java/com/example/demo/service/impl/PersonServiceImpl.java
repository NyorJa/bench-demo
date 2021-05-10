package com.example.demo.service.impl;

import com.example.demo.exceptions.ExistingEmailException;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<Person> getAllPersons() {
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
    }

    @Override
    public void deletePerson(Long id) {
        boolean exists = personRepository.existsById(id);
        if (!exists) {
            throw new IllegalArgumentException("Person id: " + id + " not existing");
        }

        personRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updatePerson(Long id, String name, String email) {
        Person person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Person with id " + id + " not existing"));
        if(StringUtils.isNotBlank(name) && !StringUtils.equals(person.getName(), name)) {
            person.setName(name);
        }

        if(StringUtils.isNotBlank(email) && !StringUtils.equals(person.getEmail(), email)) {
            Optional<Person> p = personRepository.findByEmail(person.getEmail());
            if (p.isPresent()) {
                throw new ExistingEmailException("Email is taken");
            }
            person.setEmail(email);
        }

        personRepository.save(person);
    }
}
