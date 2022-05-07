package com.example.SAPproject2.Controllers;

import com.example.SAPproject2.Entities.*;
import com.example.SAPproject2.Entities.TVchannels;
import com.example.SAPproject2.Services.TVChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "TVchannels")
public class TVChannelController {

    private final TVChannelService tvchannelService;

    @Autowired
    public TVChannelController(TVChannelService tvchannelService) {
        this.tvchannelService = tvchannelService;
    }

    @GetMapping
    public List<TVchannels> getAllTVchannels()
    {
        return tvchannelService.getAllTVchannels();
    }

    @GetMapping(path = "{id}")
    public TVchannels getTVchannel(@PathVariable("id") int id){
        return tvchannelService.getTVchannelById(id);
    }

    @DeleteMapping(path = "{id}")
    private void deleteBook(@PathVariable("id") int id)
    {
        tvchannelService.delete(id);
    }

    @PostMapping
    private ResponseEntity<String> addNewTVchannel(@RequestBody TVchannels tVchannels)
    {
        tvchannelService.saveOrUpdate(tVchannels);
        return ResponseEntity.ok("Added contract successfully");
    }

    @PostMapping(path = "/{id}/category/{category_id}")
    private void AssignCategory(
            @PathVariable int id,
            @PathVariable int category_id
    ){
        tvchannelService.assignCategory(id,category_id);
    }


    @PutMapping(path = "{id}")
    public void updateTVchannel(@PathVariable("id") int id,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) BigDecimal price,
                               @RequestParam(required = false) Category category,
                               @RequestParam(required = false) List<Providers> providers,
                               @RequestParam(required = false) List<Packets> packets){
        tvchannelService.updateTVchannel(id, name, price, category,  providers, packets);
    }
}
