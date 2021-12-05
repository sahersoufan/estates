package com.springproject.estates.services;


import com.springproject.estates.domain.Parameters;

import java.util.List;

public interface ParametersServices {
    Parameters findbyname(String name);
    List<Parameters>getAllParamter();
    Parameters SaveParameter(Parameters parameters);
    void DeleteParameter(Long id);
    Parameters FindParameter(long id);;
}
