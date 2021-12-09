package com.springproject.estates.repository;

import com.springproject.estates.domain.EstateModel;
import com.springproject.estates.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
}
