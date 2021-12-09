package com.springproject.estates.services;

import com.springproject.estates.domain.Message;
import com.springproject.estates.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServicesImpl implements MessageServices{

    @Autowired
    MessageRepository messageRepository;
    @Override
    public Message Save(Message message) {
        return messageRepository.save(message);
    }
}
