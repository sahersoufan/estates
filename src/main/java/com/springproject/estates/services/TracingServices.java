package com.springproject.estates.services;

import com.springproject.estates.domain.Tracing;

public interface TracingServices {

    Tracing save(Tracing tracing);
    Tracing find(long id);
}
