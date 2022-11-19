package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public PersonRepository personRepository;

    public Optional<Person> getPersonById(Long id){
        try {
            return personRepository.findById(id);
        }catch (Exception e){
            logger.error("Something went wrong: "+e);
            return Optional.empty();
        }
    }

    public boolean createPerson(Person person){
        try {
            Person res = personRepository.save(person);
            logger.info("Result: "+res);
            return true;
        }catch (Exception e){
            logger.error("Something went wrong: "+e);
            return false;
        }
    }

    public List<Person> getAllPerson(){
        List<Person> results;
        try {
            results = personRepository.findAll();
        }catch (Exception e){
            logger.error("Something went wrong: "+e);
            results = new ArrayList<Person>();
        }
        return results;
    }

    public boolean removePersonById(Long id){
        try {
            personRepository.deleteById(id);
            return true;
        }catch (Exception e){
            logger.error("Something went wrong: "+e);
            return false;
        }
    }
}
