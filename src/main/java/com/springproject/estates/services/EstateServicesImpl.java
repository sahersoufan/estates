package com.springproject.estates.services;


import com.springproject.estates.domain.EstateModel;
import com.springproject.estates.repository.EstateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @Slf4j
public class EstateServicesImpl implements EstateServices{

    @Autowired
    private EstateRepository estateRepository;

    @Override
    public List<EstateModel> getAllEstate() {
        return estateRepository.findAll();
    }

    @Override
    public EstateModel SaveEstate(EstateModel estates) {

       return estateRepository.save(estates);
    }

    @Override
    public void DeletEstate(long id) {
       // log.info("delete estate {}",estateRepository.findById(id).get().getName() );
        estateRepository.deleteById(id);
    }

    @Override
    public EstateModel FindEstate(long id) {
        log.info("find estate {}",estateRepository.findById(id).get().getName() );
        Optional<EstateModel> estateModel=estateRepository.findById(id);
        EstateModel estatefind = null;
       if(estateModel.isEmpty())
         return estatefind;
       else
         estatefind=estateModel.get();
        return estatefind;
    }

    @Override
    public List<EstateModel> EstateIsntSale() {

        log.info("find estate dosent sale " );
        return estateRepository.findAllBySale(false);
    }
}
