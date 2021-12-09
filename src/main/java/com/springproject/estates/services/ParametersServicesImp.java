package com.springproject.estates.services;


import com.springproject.estates.domain.Parameters;
import com.springproject.estates.repository.ParametersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service @Slf4j
public class ParametersServicesImp  implements  ParametersServices{
    @Autowired
    private ParametersRepository parametersRepository;

    @Cacheable(value="cacheParameter")
    @Override
    public Parameters findbyname(String name) {
        log.info("find parameter {} ",parametersRepository.findByName(name).getName());
        return parametersRepository.findByName(name);
    }

    @Cacheable(value="cacheAllParameter")
    @Override
    public List<Parameters> getAllParamter() {
        log.info("fetching all parameter");
        return parametersRepository.findAll();
    }

      @Caching(evict = {
              @CacheEvict(value="cacheParameter",allEntries=true),
              @CacheEvict(value="cacheAllParameter",allEntries=true)
      })
    @Override
    public Parameters SaveParameter(Parameters parameters) {
        return parametersRepository.save(parameters);
    }

    @Override
    public void DeleteParameter(Long id) {
        log.info("delete parameter {}",parametersRepository.findById(id).get().getName());
        parametersRepository.deleteById(id);
    }


    @Override
    public Parameters FindParameter(long id) {
        log.info("find parameter {} ",parametersRepository.findById(id).get().getName());
        Optional<Parameters> parameters=parametersRepository.findById(id);
        Parameters parameterfind = null;
        if(parameters.isEmpty())
            return parameterfind;
        else
            parameterfind=parameters.get();
        return parameterfind;
    }
}
