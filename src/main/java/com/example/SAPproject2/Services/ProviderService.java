package com.example.SAPproject2.Services;

import com.example.SAPproject2.Entities.Contract;
import com.example.SAPproject2.Entities.Providers;
import com.example.SAPproject2.Entities.TVchannels;
import com.example.SAPproject2.Exceptions.NotFoundException;
import com.example.SAPproject2.Repositories.ProviderRepository;
import com.example.SAPproject2.Repositories.TVChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class ProviderService {
    private final ProviderRepository providerRepository;

    @Autowired
    TVChannelRepository tvChannelRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public List<Providers> getAllProviders()
    {
        return (List<Providers>) providerRepository.findAll();
    }


    public Providers getProviderById(int id) {
        boolean exists = providerRepository.existsById(id);
        if(!exists){
            throw new NotFoundException("provider with id "+ id + " doesn't exist");
        }
        return providerRepository.findById(id).get();
    }

    public void delete(int id) {
        boolean exists = providerRepository.existsById(id);
        if(!exists){
            throw new NotFoundException("packet with id "+ id + " doesn't exist");

        }
        providerRepository.deleteById(id);
    }

    public void saveOrUpdate(Providers providers) {
        providerRepository.save(providers);
    }

    @Transactional
    public void updateProvider(int id, String name, List<Contract> contracts, List<TVchannels> tVchannels){
        Providers providers  = providerRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "providers with id " + id + " doesn't exist"));
        if (name != null && name.length() > 0 && !Objects.equals(providers.getName(), name)) {
            providers.setName(name);
        }
        if (contracts != null && !Objects.equals(providers.getContracts(), contracts)) {
            providers.setContracts(contracts);
        }
        if (tVchannels != null && !Objects.equals(providers.getTVchannels(), tVchannels)) {
            providers.setTVchannels(tVchannels);
        }

    }

    public void assignTVchannelToProvider(int id, int channel_id) {
        Providers providers  = providerRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "provider with id " + id + " doesn't exist"));;
        TVchannels tVchannels = tvChannelRepository.findById(channel_id).orElseThrow(() -> new NotFoundException(
                "TV channel with id " + channel_id + " doesn't exist"));
        providers.tVchannels.add(tVchannels);
        providerRepository.save(providers);
    }
}

