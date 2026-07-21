package com.example.portfolio.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.portfolio.service.HoldingService;

@RestController
@RequestMapping("/api")
public class PortfolioController {

    private final HoldingService holdingService;

    // Spring automatically injects the HoldingService bean here
    public PortfolioController(HoldingService holdingService) {
        this.holdingService = holdingService;
    }
    
    @GetMapping("/portfolio")
    public double portfolio() {
        return holdingService.buy();
    }

     @PostMapping("/portfolio")
    public String PostPortfolio() {
        return "Portfolio";
    }

    @DeleteMapping("/portfolio")
    public String DeletePortfolio() {
        return "Portfolio";
    }
}
