package com.springproject.estates.repository;

import com.springproject.estates.domain.Parameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametersRepository extends JpaRepository<Parameters,Long> {
    Parameters findByName(String name);
}
