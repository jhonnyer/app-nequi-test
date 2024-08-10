package com.nequi.app.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "productos")
public class Producto {

    private String id;
    private String nombre;
    private int stock;
    
    @Field("sucursalId")
    private String sucursalId;
}