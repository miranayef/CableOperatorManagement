package com.example.SAPproject2.Services;

import com.example.SAPproject2.Entities.*;
import com.example.SAPproject2.Exceptions.NotFoundException;
import com.example.SAPproject2.Repositories.CategoryRepository;
import com.example.SAPproject2.Repositories.TVChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class TVChannelService {
    private final TVChannelRepository tvChannelRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    public TVChannelService(TVChannelRepository tvChannelRepository) {
        this.tvChannelRepository = tvChannelRepository;
    }

    public List<TVchannels> getAllTVchannels()
    {
        return (List<TVchannels>) tvChannelRepository.findAll();
    }


    public TVchannels getTVchannelById(int id) {
        boolean exists = tvChannelRepository.existsById(id);
        if(!exists){
            throw new NotFoundException("provider with id "+ id + " doesn't exist");
        }
        return tvChannelRepository.findById(id).get();
    }

    public void delete(int id) {
        boolean exists = tvChannelRepository.existsById(id);
        if(!exists){
            throw new NotFoundException("packet with id "+ id + " doesn't exist");

        }
        tvChannelRepository.deleteById(id);
    }

    public void saveOrUpdate(TVchannels tVchannels) {
        tvChannelRepository.save(tVchannels);
    }


    @Transactional
    public void updateTVchannel(int id, String name, BigDecimal price, Category category, List<Providers> providers, List<Packets> packets){
        TVchannels tVchannels  = tvChannelRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "tvchannels with id " + id + " doesn't exist"));
        if (name != null && name.length() > 0 && !Objects.equals(tVchannels.getName(), name)) {
            tVchannels.setName(name);
        }
        if (price != null && !Objects.equals(tVchannels.getPrice(), price)) {
            tVchannels.setPrice(price);
        }
        if (providers != null && !Objects.equals(tVchannels.getProviders(), providers)) {
            tVchannels.setProviders(providers);
        }
        if (packets != null && !Objects.equals(tVchannels.getPackets(), packets)) {
            tVchannels.setPackets(packets);
        }
    }

    public void assignCategory(int id, int category_id) {
       TVchannels tVchannels = tvChannelRepository.findById(id).orElseThrow(() -> new NotFoundException(
               "TV channel with id " + id + " doesn't exist"));
        Category category = categoryRepository.findById(category_id).orElseThrow(() -> new NotFoundException(
                "category with id " + category_id + " doesn't exist"));
        tVchannels.setCategory(category);
        //make the price of a category - the price of all channel -10% discount
        if(category.getPrice() != null && !"".equals(category.getPrice())  )
        {
            BigDecimal sum = category.getPrice().add(tVchannels.price.multiply(BigDecimal.valueOf(0.9)));
            category.setPrice(sum);
        }
        else{
            category.setPrice(tVchannels.price.multiply(BigDecimal.valueOf(0.9)));
        }
        tvChannelRepository.save(tVchannels);
    }
}

