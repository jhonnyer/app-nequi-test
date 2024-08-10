package com.nequi.app.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SucursalPresentar {
	private String id;
    private String nombre;
    private String direccion;
    private List<Producto> productos;
}
