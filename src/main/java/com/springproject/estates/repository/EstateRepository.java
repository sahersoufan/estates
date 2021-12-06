package com.springproject.estates.repository;

import com.springproject.estates.domain.EstateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstateRepository extends JpaRepository<EstateModel,Long> {
    void deleteById(long id);
    List<EstateModel> findAllBySale(Boolean sale);
}
