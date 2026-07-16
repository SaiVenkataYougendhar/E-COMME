package com.swiftcart.controller;

import com.swiftcart.repository.AnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    private final AnalyticsRepository analyticsRepository;

    @Autowired
    public DashboardController(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    @GetMapping("/analytics")
    public ResponseEntity<?> getAnalytics() {
        BigDecimal totalSales = analyticsRepository.getTotalSales();
        BigDecimal totalRefunds = analyticsRepository.getTotalRefunds();
        BigDecimal totalLosses = analyticsRepository.getTotalLosses();
        BigDecimal totalCogs = analyticsRepository.getTotalCogs();

        // Net Profit = Sales - COGS - Refunds - Losses
        BigDecimal netProfit = totalSales.subtract(totalCogs).subtract(totalRefunds).subtract(totalLosses);

        Map<String, Object> response = new HashMap<>();
        response.put("totalSales", totalSales);
        response.put("totalRefunds", totalRefunds);
        response.put("totalLosses", totalLosses);
        response.put("totalCogs", totalCogs);
        response.put("netProfit", netProfit);

        return ResponseEntity.ok(response);
    }
}
