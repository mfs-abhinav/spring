package com.abhi.springparallelprocessing.service;

import com.abhi.springparallelprocessing.model.Country;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class CountryService {

    RestTemplate restTemplate = new RestTemplate();

    @Async
    public CompletableFuture<List<Country>> getCountriesByLanguage(String language) {
//        String url = "https://restcountries.eu/rest/v2/lang/" + language + "?fields=name";
//        Country[] response = restTemplate.getForObject(url, Country[].class);

        Country[] response = (Country[]) IntStream.range(0, 10).mapToObj(i -> new Country("country" + i)).toArray();
        return CompletableFuture.completedFuture(Arrays.asList(response));


    }

    @Async
    public CompletableFuture<List<Country>> getCountriesByRegion(String region) {
//        String url = "https://restcountries.eu/rest/v2/region/" + region + "?fields=name";
//        Country[] response = restTemplate.getForObject(url, Country[].class);
        Country[] response = (Country[]) IntStream.range(5, 10).mapToObj(i -> new Country("country" + i)).toArray();
        return CompletableFuture.completedFuture(Arrays.asList(response));
    }
}
