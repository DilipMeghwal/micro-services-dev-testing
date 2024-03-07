package com.vpl.ms.controllers;

import com.vpl.ms.entity.Country;
import com.vpl.ms.dto.ResponseMessage;
import com.vpl.ms.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountryService countryService;

    @GetMapping("/getCountries")
    public ResponseEntity<List<Country>> getCountries() {
        try {
            return new ResponseEntity<>(countryService.getAllCountries(), HttpStatus.FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCountries/{id}")
    public ResponseEntity<Country> getCountry(@PathVariable(value = "id") int id) {
        try {
            return new ResponseEntity<>(countryService.getCountry(id), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCountries/countryName")
    public ResponseEntity<Country> getCountryByName(@RequestParam(value = "name") String countryName) {
        try {
            return new ResponseEntity<>(countryService.getCountryByName(countryName), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addCountry")
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {
        try {
            return new ResponseEntity<>(countryService.addCountry(country), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/updateCountry")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country) {
        try {
            Country ex = countryService.getCountry(country.getId());
            ex.setCountryName(country.getCountryName());
            ex.setCountryCapital(country.getCountryCapital());
            Country updated = countryService.updateCountry(ex);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/deleteCountry/{id}")
    public ResponseEntity<ResponseMessage> deleteCountry(@PathVariable(value = "id") int id) {
        try {
            return new ResponseEntity<>(countryService.deleteCountry(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
