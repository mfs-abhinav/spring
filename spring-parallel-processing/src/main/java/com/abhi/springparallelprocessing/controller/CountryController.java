package com.abhi.springparallelprocessing.controller;

import com.abhi.springparallelprocessing.model.Country;
import com.abhi.springparallelprocessing.service.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Api(value = "CountryController")
@RestController
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @ApiOperation(httpMethod = "GET", value = "Get all European and French speaking countries", response = String.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Countries not found"),
            @ApiResponse(code = 500, message = "The countries could not be fetched")
    })
    @GetMapping("")
    public List<String> getAllEuropeanFrenchSpeakingCountries() throws Throwable {
        CompletableFuture<List<Country>> countriesByLanguageFuture = countryService.getCountriesByLanguage("fr");
        CompletableFuture<List<Country>> countriesByRegionFuture = countryService.getCountriesByRegion("europe");

        System.out.println("Main is running");
        List<String> europeanFrenchSpeakingCountries = new ArrayList<>();
//        try {
//            europeanFrenchSpeakingCountries = new ArrayList<>(countriesByLanguageFuture.get().stream().map(Country::getName).collect(Collectors.toList()));
//            europeanFrenchSpeakingCountries.retainAll(countriesByRegionFuture.get().stream().map(Country::getName).collect(Collectors.toList()));
//        } catch (Throwable e) {
//            throw e.getCause();
//        }

        return europeanFrenchSpeakingCountries;
    }
}
