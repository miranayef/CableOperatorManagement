package com.example.SAPproject2.Controllers;

import com.example.SAPproject2.Entities.*;
import com.example.SAPproject2.Entities.Reports;
import com.example.SAPproject2.Services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "reports")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<Reports> getAllReports()
    {
        return reportService.getAllReports();
    }

    @GetMapping(path = "{id}")
    public Reports getReport(@PathVariable("id") int id){
        return reportService.getReportById(id);
    }

    @DeleteMapping(path = "{id}")
    private void deleteReport(@PathVariable("id") int id)
    {
        reportService.delete(id);
    }

    @PostMapping
    private ResponseEntity<String> addNewReport(@RequestBody Reports reports)
    {
        reportService.saveOrUpdate(reports);
        return ResponseEntity.ok("Added contract successfully");
    }

    @PutMapping(path = "{id}")
    public void updateReport(@PathVariable("id") int id,
                               @RequestParam(required = false) BigDecimal monthlyPrice,
                               @RequestParam(required = false) Customers customers
    ){
        reportService.updateReport(id, monthlyPrice, customers);
    }
}