package com.checkout.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Product implements Item {
    private int id;
    private String name;
    private String description;
    private double price;
}
