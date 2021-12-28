package fr.clementperrin.notier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class MainController
 * Created on 25/12/2021
 *
 * @author Clément PERRIN <contact@clementperrin.fr>
 */
@RestController
@RequestMapping("/")
public class MainController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.application.version}")
    private String applicationVersion;

    @Autowired
    private RequestMappingHandlerMapping requestHandlerMapping;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> index() {
        Map<String,Object> response = new HashMap<>();


        List<String> endpoints = new ArrayList<>();
        this.requestHandlerMapping.getHandlerMethods()
                                  .forEach((requestMappingInfo, handlerMethod) -> endpoints.add(requestMappingInfo.getActivePatternsCondition()
                                                                                                                  .toString()
                                                                                                                  .replace(
                                                                                                                          "[",
                                                                                                                          ""
                                                                                                                  )
                                                                                                                  .replace(
                                                                                                                          "]",
                                                                                                                          ""
                                                                                                                  )));
        response.put("endpoints", endpoints);
        response.put("version", this.applicationVersion);
        response.put("name", this.applicationName);
        return response;
    }
}
