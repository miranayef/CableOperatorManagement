package com.example.SAPproject2.Controllers;

import com.example.SAPproject2.Repositories.PacketRepository;
import com.example.SAPproject2.Repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "changePrices")
public class ManipulatePrices {
    @Autowired
    PacketRepository packetRepository;
    @Autowired
    ReportRepository reportRepository;

    @PutMapping(path = "minus/{percentage}")
    void discount(@PathVariable("percentage") BigDecimal percentage){
        BigDecimal percentageFinal = percentage.subtract(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(-1)));
        packetRepository.discount(percentageFinal);
        reportRepository.discount(percentageFinal);
    }

    @PutMapping(path = "plus/{percentage}")
    void raisePrices(@PathVariable("percentage") BigDecimal percentage){
        BigDecimal percentageFinal = percentage.divide(BigDecimal.valueOf(100));
        packetRepository.raisePrices(percentageFinal);
        reportRepository.raisePrices(percentageFinal);
    }
}
