package com.example.SAPproject2.Services;

import com.example.SAPproject2.Entities.*;
import com.example.SAPproject2.Exceptions.NotFoundException;
import com.example.SAPproject2.Repositories.CategoryRepository;
import com.example.SAPproject2.Repositories.PacketRepository;
import com.example.SAPproject2.Repositories.TVChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class PacketService {
    private final PacketRepository packetRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TVChannelRepository tvChannelRepository;


    @Autowired
    public PacketService(PacketRepository packetRepository) {
        this.packetRepository = packetRepository;
    }

    public List<Packets> getAllPackets()
    {
        return (List<Packets>) packetRepository.findAll();
    }


    public Packets getPacketById(int id) {
        boolean exists = packetRepository.existsById(id);
        if(!exists){
            throw new NotFoundException("packet with id "+ id + " doesn't exist");
        }
        return packetRepository.findById(id).get();
    }

    public void delete(int id) {
        boolean exists = packetRepository.existsById(id);
        if(!exists){
            throw new NotFoundException("packet with id "+ id + " doesn't exist");

        }
        packetRepository.deleteById(id);
    }

    public void saveOrUpdate(Packets packets) {
        packetRepository.save(packets);
    }

    @Transactional
    public void updatePacket(int id, String name,
                            BigDecimal price,
                             Category category, List<TVchannels> tVchannels, List<Contract> contracts){
        Packets packets  = packetRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "packets with id " + id + " doesn't exist"));
        if (name != null && name.length() > 0 && !Objects.equals(packets.getName(), name)) {
            packets.setName(name);
        }
        if (price != null &&  !Objects.equals(packets.getPacketPrice(), price)) {
            packets.setPacketPrice(price);
        }
        if (category != null && !Objects.equals(packets.getCategory(), category)) {
            packets.setCategory(category);
        }
        if (tVchannels != null && !Objects.equals(packets.getTVchannels(), tVchannels)) {
            packets.setTVchannels(tVchannels);
        }
        if (contracts != null && !Objects.equals(packets.getContracts(), contracts)) {
            packets.setContracts(contracts);
        }
    }

    public void assignCategory(int id, int category_id) {
        Packets packets  = packetRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "packet with id " + id + " doesn't exist"));
        Category category = categoryRepository.findById(category_id).orElseThrow(() -> new NotFoundException(
                "category with id " + category_id + " doesn't exist"));
        packets.setCategory(category);
        packets.setPacketPrice(category.price);
        packetRepository.save(packets);
    }

    public void assignTVchannels(int id, int channel_id) {
        Packets packets  = packetRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "packet with id " + id + " doesn't exist"));
        TVchannels tVchannels = tvChannelRepository.findById(channel_id).orElseThrow(() -> new NotFoundException(
                "TV channel with id " + channel_id + " doesn't exist"));
        packets.tVchannels.add(tVchannels);
        if(packets.getPacketPrice() != null && !"".equals(packets.getPacketPrice())  )
        {
            BigDecimal sum = packets.getPacketPrice().add(tVchannels.price);
            packets.setPacketPrice(sum);
        }
        else{
            packets.setPacketPrice(tVchannels.price);
        }
        packetRepository.save(packets);
    }


}