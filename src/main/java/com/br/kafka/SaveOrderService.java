package com.br.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SaveOrderService {

    @Inject
    ObjectMapper objectMapper;

    @Transactional
    public void saveOrder(String message) throws JsonProcessingException {
        var order = objectMapper.readValue(message, OrderBlock.class);
        System.out.println("Order saved: " + order);
        order.persist();
    }

}
