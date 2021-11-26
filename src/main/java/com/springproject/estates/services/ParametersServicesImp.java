package com.springproject.estates.services;

import com.springproject.estates.domain.Parameters;
import com.springproject.estates.repository.ParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParametersServicesImp  implements  ParametersServices{
    @Autowired
    private ParametersRepository parametersRepository;

    @Override
    public Parameters findbyname(String name) {
        return parametersRepository.findByName(name);
    }
}
