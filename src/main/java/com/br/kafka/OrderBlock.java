package com.br.kafka;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "orders")
public class OrderBlock extends PanacheEntity {
    public UUID external_id = UUID.randomUUID();
    public String clientId;
    public String product;
    public String status;
    public BigDecimal totalAmount;
}
