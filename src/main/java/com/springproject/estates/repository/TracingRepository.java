package com.springproject.estates.repository;


import com.springproject.estates.domain.Tracing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TracingRepository extends JpaRepository<Tracing,Long>{
}
