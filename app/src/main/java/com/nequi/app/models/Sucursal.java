package com.nequi.app.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "sucursales")
public class Sucursal {

	@Id
    private String id;
    private String nombre;
    private String direccion;
   
    @Field("franquiciaId")
    private String franquiciaId;
}