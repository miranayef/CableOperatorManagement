package com.example.SAPproject2.Controllers;

import com.example.SAPproject2.Entities.*;
import com.example.SAPproject2.Entities.Providers;
import com.example.SAPproject2.Services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "providers")
public class ProviderController {
    private final ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping
    public List<Providers> getAllProviders()
    {
        return providerService.getAllProviders();
    }

    @GetMapping(path = "{id}")
    public Providers getProvider(@PathVariable("id") int id){
        return providerService.getProviderById(id);
    }

    @DeleteMapping(path = "{id}")
    private void deleteProvider(@PathVariable("id") int id)
    {
        providerService.delete(id);
    }

    @PostMapping
    private ResponseEntity<String> addNewProvider(@RequestBody Providers providers)
    {
        providerService.saveOrUpdate(providers);
        return ResponseEntity.ok("Added contract successfully");
    }

    //linking many to many rlt with TVChannel
    @PostMapping("/{id}/TVchannels/{channel_id}")
    private void assignTVchannelToProvider(
            @PathVariable int id,
            @PathVariable int channel_id
    ) {
        providerService.assignTVchannelToProvider(id, channel_id);
    }

    @PutMapping(path = "{id}")
    public void updateProvider(@PathVariable("id") int id,
                             @RequestParam(required = false) String name,
                               @RequestParam(required = false)  List<Contract> contracts,
                             @RequestParam(required = false) List<TVchannels> tVchannels
                             ){
        providerService.updateProvider(id, name, contracts, tVchannels);
    }
}