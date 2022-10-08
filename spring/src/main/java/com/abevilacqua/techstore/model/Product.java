package com.abevilacqua.techstore.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@ToString
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;
    private String name;
    private String description;
    private double price;
    @Version private long version;

}
