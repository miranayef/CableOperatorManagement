package com.example.SAPproject2.Controllers;

import com.example.SAPproject2.Entities.*;
import com.example.SAPproject2.Entities.Packets;
import com.example.SAPproject2.Services.PacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "packets")
public class PacketController {
    private final PacketService packetService;

    @Autowired
    public PacketController(PacketService packetService) {
        this.packetService = packetService;
    }

    @GetMapping
    public List<Packets> getAllPackets()
    {
        return packetService.getAllPackets();
    }

    @GetMapping(path = "{id}")
    public Packets getPacket(@PathVariable("id") int id){
        return packetService.getPacketById(id);
    }

    @DeleteMapping(path = "{id}")
    private void deletePacket(@PathVariable("id") int id)
    {
        packetService.delete(id);
    }

    @PostMapping
    private ResponseEntity<String> addNewPacket(@RequestBody Packets packets)
    {
        packetService.saveOrUpdate(packets);
        return ResponseEntity.ok("Added packet successfully");
    }

    //adding keys for many to one rlt with category
    @PostMapping(path = "/{id}/category/{category_id}")
    private void AssignCategory(
            @PathVariable int id,
            @PathVariable int category_id
    ){
        packetService.assignCategory(id,category_id);
    }

    //linking many to many rlt with TVChannel
    @PostMapping("/{id}/TVchannels/{channel_id}")
    private void assignTVchannel(
            @PathVariable int id,
            @PathVariable int channel_id
    ) {
        packetService.assignTVchannels(id, channel_id);
    }

    @PutMapping(path = "{id}")
    public void updatePacket(@PathVariable("id") int id,
                             @RequestParam(required = false) String name,
                              @RequestParam(required = false) BigDecimal price,
                               @RequestParam(required = false) Category category,
                               @RequestParam(required = false) List<TVchannels> tVchannels,
                               @RequestParam(required = false)  List<Contract> contracts){
        packetService.updatePacket(id, name,
               price,
                category,tVchannels,contracts);
    }
}
