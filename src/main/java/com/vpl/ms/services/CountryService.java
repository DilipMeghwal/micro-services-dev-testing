package com.vpl.ms.services;

import com.vpl.ms.dto.ResponseMessage;
import com.vpl.ms.entity.Country;
import com.vpl.ms.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountry(int id) {
        return countryRepository.findById(id).orElse(null);
    }

    public Country getCountryByName(String countyName) {
        List<Country> countries = countryRepository.findAll();
        Country country = null;
        for (Country c : countries) {
            if (c.getCountryName().equals(countyName)){
                country = c;
            }
        }
        return country;
    }

    public Country addCountry(Country newCountry) {
        newCountry.setId(getMaxSize());
        countryRepository.save(newCountry);
        return newCountry;
    }

    public Country updateCountry(Country newCountry) {
        countryRepository.save(newCountry);
        return newCountry;
    }

    public ResponseMessage deleteCountry(int id) {
        countryRepository.deleteById(id);
        ResponseMessage res = new ResponseMessage();
        res.setMessage("Country Deleted");
        res.setId(id);
        return res;
    }

    public int getMaxSize(){
        return countryRepository.findAll().size() + 1;
    }

}
