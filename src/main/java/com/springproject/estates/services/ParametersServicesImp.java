package com.springproject.estates.services;

import com.springproject.estates.domain.EstateModel;
import com.springproject.estates.domain.Parameters;
import com.springproject.estates.repository.ParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParametersServicesImp  implements  ParametersServices{
    @Autowired
    private ParametersRepository parametersRepository;

    @Override
    public Parameters findbyname(String name) {
        return parametersRepository.findByName(name);
    }

    @Override
    public List<Parameters> getAllParamter() {
        return parametersRepository.findAll();
    }

    @Override
    public Parameters SaveParameter(Parameters parameters) {
        return parametersRepository.save(parameters);
    }

    @Override
    public void DeleteParameter(Long id) {
        parametersRepository.deleteById(id);
    }

    @Override
    public Parameters FindParameter(long id) {
        Optional<Parameters> parameters=parametersRepository.findById(id);
        Parameters parameterfind = null;
        if(parameters.isEmpty())
            return parameterfind;
        else
            parameterfind=parameters.get();
        return parameterfind;
    }
}
