
package com.thefancygoodiebox.infrastructure;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class ProductEntity {

    @Id
    private UUID id;
    private String name;
    private BigDecimal price;

    protected ProductEntity() {}

    public ProductEntity(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
}
