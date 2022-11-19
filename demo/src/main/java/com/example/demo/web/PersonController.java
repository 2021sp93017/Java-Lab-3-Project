package com.example.demo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequestMapping("/person")
public class PersonController {

    @Autowired
    public PersonService personSrv;

    @PostMapping
    public HttpEntity<HashMap<String, String>> postPerson(@RequestBody Person person){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        HashMap<String, String> res = new HashMap<>();
        String info = "Something went wrong";
        if(personSrv.createPerson(person)){
            info =  "Entry created";
            status = HttpStatus.CREATED;
        }
        res.put("info", info);
        return new ResponseEntity<>(res, status);
    }

    @GetMapping
    public HttpEntity<HashMap<String, List<Person>>> listPerson(){
        List<Person> pList = personSrv.getAllPerson();
        HashMap<String, List<Person>> res = new HashMap<>();
        res.put("data", pList);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public HttpEntity<HashMap<String, Object>> getPersonById(@PathVariable long id){
        HttpStatus status = HttpStatus.NOT_FOUND;
        Optional<Person> result = personSrv.getPersonById(id);
        HashMap<String, Object> res = new HashMap<>();
        String info =  "Id not found";
        if(result.isPresent()){
            info = "Data fetched successfully";
            status = HttpStatus.OK;
            res.put("data", result.get());
        }
        res.put("info", info);
        return new ResponseEntity<HashMap<String, Object>>(res, status);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<HashMap<String, String>> deletePersonById(@PathVariable long id){
        HashMap<String, String> res = new HashMap<>();
        String info = "Id not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(personSrv.removePersonById(id)){
            status = HttpStatus.OK;
            info = "Entry removed successfully";
        }
        res.put("info", info);
        return new ResponseEntity<>(res, status);
    }

}
