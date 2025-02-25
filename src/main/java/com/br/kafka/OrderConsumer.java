package com.br.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.*;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class OrderConsumer {

    @Inject
    ObjectMapper objectMapper;

//    @Incoming("order-in")
//    @Acknowledgment(Acknowledgment.Strategy.POST_PROCESSING)
//    public Uni<CompletionStage<Void>> consumer(Message<String> message) throws JsonProcessingException {
//        System.out.println("Received: " + message.getPayload());
//
//        var order = objectMapper.readValue(message.getPayload(), Order.class);
//
//        return Panache.withTransaction(order::persist)
//                .onFailure().recoverWithItem(() -> {
//                    System.out.println("Error on persisting order");
//                    return null;
//                })
//                .onItem()
//                .transform(entity -> message.ack());
//    }

    @Inject
    private SaveOrderService saveOrderService;

    @Incoming("order-in")
    @Acknowledgment(Acknowledgment.Strategy.POST_PROCESSING)
    @Blocking
    public CompletionStage<Void> consumerBlocking(Message<String> message) throws JsonProcessingException {
        System.out.println("Received: " + message.getPayload());
        try {
            saveOrderService.saveOrder(message.getPayload());
            return message.ack();
        } catch (Exception e) {
            System.out.println("Error on persisting order");
            throw e;
        }
    }
}
