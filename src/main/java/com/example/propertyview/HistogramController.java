package com.example.propertyview;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * GET /property-view/histogram/{param}
 * param: brand | city | country | amenities
 */
@RestController
@RequestMapping("/property-view")
public class HistogramController {

    private final HistogramService histogramService;

    public HistogramController(HistogramService histogramService) {
        this.histogramService = histogramService;
    }

    @GetMapping("/histogram/{param}")
    public Map<String, Long> histogram(@PathVariable String param) {
        return histogramService.histogram(param);
    }
}
