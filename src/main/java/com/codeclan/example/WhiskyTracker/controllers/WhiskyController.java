package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Distillery;
import com.codeclan.example.WhiskyTracker.models.Whisky;
import com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WhiskyController {

    @Autowired
    WhiskyRepository whiskyRepository;

    @GetMapping(value = "/whiskies/{id}")
    public ResponseEntity getWhisky(@PathVariable Long id){
        return new ResponseEntity<>(whiskyRepository.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/whiskies")
    public ResponseEntity<List<Whisky>> findWhiskiesFilterByYear(
            @RequestParam(name = "year",required = false) Integer year,
            @RequestParam(name = "name",required = false) String name,
            @RequestParam(name = "age",required = false) Integer age,
            @RequestParam(name = "region",required = false) String region)
    {
        if(year != null){
            return new ResponseEntity<>(whiskyRepository.findByYear(year),HttpStatus.OK);
        }
        if(name != null && age !=null){
            List<Whisky> findWhisky = whiskyRepository.findByDistilleryNameAndAge(name,age);
            return new ResponseEntity<>(findWhisky, HttpStatus.OK);
        }
        if(region != null){
            return new ResponseEntity<>(whiskyRepository.findByDistilleryRegion(region),HttpStatus.OK);
        }
        return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);
    }
}
