package ru.fioktistov.sample.redis.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.fioktistov.sample.redis.service.IncService;

@RestController
@RequestMapping(IncRest.ROOT_PATH)
public class IncRest {

    static final String ROOT_PATH = "/public";
    static final String METHOD_GET_VALUE = "/{id}/value";
    static final String METHOD_INC_VALUE = "/{id}/value/add";

    @Autowired
    IncService service;

    @PutMapping(value = METHOD_INC_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Mono<Long> inc(@PathVariable String id) {
        return service.inc(id);
    }

    @GetMapping(value = METHOD_GET_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Mono<Long> get(@PathVariable String id) {
        return service.get(id);
    }

}
