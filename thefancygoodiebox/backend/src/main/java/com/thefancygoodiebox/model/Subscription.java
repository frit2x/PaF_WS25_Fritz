package com.thefancygoodiebox.model;

import java.math.BigDecimal;
import jakarta.persistence.*;

/**
 * JPA Entity, die die SUBSCRIPTION-Tabelle repräsentiert.
 * Wird automatisch von Hibernate zu einer DB-Tabelle gemappt (spring.jpa.hibernate.ddl-auto=update).
 */
@Entity
@Table(name = "subscription")  // Expliziter Tabellenname (sonst wäre es "subscription")
public class Subscription {

    @Id  // Primärschlüssel
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-Increment ID (H2/MySQL-kompatibel)
    private Long id;

    // Einfache String-Felder → VARCHAR(255) in DB
    private String name;
    private String description;

    @Column(precision = 10, scale = 2)  // DECIMAL(10,2) → z.B. 99999999.99
    private BigDecimal price;

    @Column(name = "image_url")  // Spaltenname angepasst (snake_case für DB-Konvention)
    private String imageUrl;

    private String category;  // z.B. "basic", "premium", "family" für Filterung

    //
    // JPA/Hibernate ERFORDERLICHE Konstruktoren
    //
    public Subscription() {}  // No-Args Constructor - OBLIGATORISCH für JPA!

    // All-args Constructor für einfache Instanziierung (optional, aber praktisch)
    public Subscription(String name, String description, BigDecimal price,
                        String imageUrl, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    //
    // GETTER & SETTER (könnten mit Lombok @Data ersetzt werden)
    //
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

