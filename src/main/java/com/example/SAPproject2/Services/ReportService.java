package com.example.SAPproject2.Services;

import com.example.SAPproject2.Entities.Customers;
import com.example.SAPproject2.Entities.Reports;
import com.example.SAPproject2.Exceptions.NotFoundException;
import com.example.SAPproject2.Repositories.ContractRepository;
import com.example.SAPproject2.Repositories.CustomerRepository;
import com.example.SAPproject2.Repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ContractRepository contractRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Reports> getAllReports()
    {
        return (List<Reports>) reportRepository.findAll();
    }


    public Reports getReportById(int id) {
        boolean exists = reportRepository.existsById(id);
        if(!exists){
            throw new NotFoundException("report with id "+ id + " doesn't exist");
        }
        return reportRepository.findById(id).get();
    }

    public void delete(int id) {
        boolean exists = reportRepository.existsById(id);
        if(!exists){
            throw new NotFoundException("report with id "+ id + " doesn't exist");

        }
        reportRepository.deleteById(id);
    }

    public void saveOrUpdate(Reports reports) {

        reportRepository.save(reports);
    }

    @Transactional
    public void updateReport(int id, BigDecimal monthlyPrice, Customers customers){
        Reports reports  = reportRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "reports with id " + id + " doesn't exist"));
        if (monthlyPrice != null  && !Objects.equals(reports.getMonthlyPayment(), monthlyPrice)) {
            reports.setMonthlyPayment(monthlyPrice);
        }
        if (customers != null && !Objects.equals(reports.getCustomers(), customers)) {
            reports.setCustomers(customers);
        }

    }

//    public void AssignCustomer(int id, int customer_id) {
//        Reports reports = reportRepository.findById(id).get();
//        Customers customers = customerRepository.findById(customer_id).get();
//        reports.setMonthlyPayment(contractRepository.findById(customer_id).get().getPackets().getPacketPrice());
//        reports.setCustomers(customers);
//        reportRepository.save(reports);
//    }
}

