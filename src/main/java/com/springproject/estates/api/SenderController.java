package com.springproject.estates.api;


import com.springproject.estates.Configuration.QueueSender;
import com.springproject.estates.domain.EstateModel;
import com.springproject.estates.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.amqp.core.Binding;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;


@Controller
@Slf4j
public class SenderController {


    @Autowired
    private QueueSender queueSender;

    @GetMapping(value = "/Sender")
    public String viewSendPage(){
        return "Sender/home";
    }
    @RequestMapping(value = "/sendmessage", method= RequestMethod.POST)
    public String send(@ModelAttribute Message message) {
        queueSender.send(message);
        log.info("sending message");
        return "redirect:/estate";
    }

}
