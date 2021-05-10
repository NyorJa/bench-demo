package com.example.demo.service;

import com.example.demo.exceptions.ExistingEmailException;
import com.example.demo.model.Person;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public interface PersonService {

    Person findByName(String name) throws EntityNotFoundException;

    Optional<Person> findByAddress(String address);

    void createPerson(Person build) throws ExistingEmailException;

    List<Person> getAllPersons();

    void deletePerson(Long id);

    void updatePerson(Long id, String name, String email);
}
