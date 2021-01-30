package com.ennbou.jmsspring;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;
import javax.jms.Topic;

@SpringBootApplication
@EnableJms
public class JmsspringApplication {

  public static void main(String[] args) {
    SpringApplication.run(JmsspringApplication.class, args);
  }

  @Bean
  public Queue queue() {
    return new ActiveMQQueue("queue1");
  }

  @Bean
  public Topic topic() {
    return new ActiveMQTopic("topic1");
  }

}


@Component
class MyJMSListeners {

  @JmsListener(destination = "queue1")
  public void receive(String message) {
    System.out.println("queue1 reçoit 📩 : "+message);
  }
}

@RestController
class MyRestController {
  private final JmsTemplate jmsTemplate;
  private final Queue queue;

  public MyRestController(JmsTemplate jmsTemplate, Queue queue) {
    this.jmsTemplate = jmsTemplate;
    this.queue = queue;
  }

  @GetMapping("/sendMessage")
  @ResponseBody
  public String send(@RequestParam(name = "message") String message) {
    System.out.println();
    System.out.println("message : " + message);
    System.out.println("-----------------------------------");
    jmsTemplate.convertAndSend(queue, message);
    return "Message envoyé ✔";
  }
}
