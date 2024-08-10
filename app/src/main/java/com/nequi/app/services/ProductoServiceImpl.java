package com.nequi.app.services;

import java.util.Collections;
import java.util.Comparator;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nequi.app.interfaces.IProductoService;
import com.nequi.app.models.Producto;
import com.nequi.app.models.ProductoConSucursal;
import com.nequi.app.models.SucursalPresentar;
import com.nequi.app.repository.ProductoRepository;
import com.nequi.app.repository.SucursalRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements IProductoService{
	@Autowired
    private ProductoRepository productoRepository;
	
	@Autowired
    private SucursalRepository sucursalRepository;
	
	@Override
    public Flux<Producto> obtenerProductosPorSucursalId(String sucursalId) {
		ObjectId objectId = new ObjectId(sucursalId);
        return productoRepository.findBySucursalId(objectId);
    }

    @Override
    public Mono<Producto> obtenerProductoPorId(String id) {
        return productoRepository.findById(id);
    }

    @Override
    public Mono<Producto> crearProducto(String sucursalId, Producto producto) {
        return sucursalRepository.findById(sucursalId)
            .flatMap(sucursal -> {
                producto.setSucursalId(sucursalId);
                return productoRepository.save(producto);
            })
            .switchIfEmpty(Mono.error(new RuntimeException("Sucursal no encontrada")));
    }
    
    @Override
    public Mono<Producto> actualizarProducto(String sucursalId, String productoId, Producto productoActualizado) {
        return productoRepository.findById(productoId)
            .flatMap(productoExistente -> {
                if (productoExistente.getSucursalId().equals(sucursalId)) {
                    productoExistente.setNombre(productoActualizado.getNombre());
                    productoExistente.setStock(productoActualizado.getStock());
                    return productoRepository.save(productoExistente);
                } else {
                    return Mono.error(new IllegalArgumentException("El producto no pertenece a la sucursal especificada."));
                }
            });
    }

    @Override
    public Mono<Void> eliminarProducto(String sucursalId, String productoId) {
        return productoRepository.findById(productoId)
            .flatMap(producto -> {
                if (producto.getSucursalId().equals(sucursalId)) {
                    return productoRepository.deleteById(productoId);
                } else {
                    return Mono.empty(); // Manejar el caso cuando el producto no pertenece a la sucursal
                }
            });
    }
    
    @Override
    public Flux<ProductoConSucursal> obtenerProductoConMasStockPorSucursal(String franquiciaId) {
    	ObjectId objectId = new ObjectId(franquiciaId);
        return sucursalRepository.findByFranquiciaId(objectId)
            .flatMap(sucursal ->
                productoRepository.findBySucursalId(sucursal.getId())
                    .collectList()
                    .flatMapMany(productos -> {
                    	Producto productoConMasStock = productos.stream()
                                .max(Comparator.comparingInt(Producto::getStock))
                                .orElse(null);
                    	if (productoConMasStock != null) {
                    		SucursalPresentar sucursalConProducto = new SucursalPresentar(
                                sucursal.getId(),
                                sucursal.getNombre(),
                                sucursal.getDireccion(),
                                Collections.singletonList(productoConMasStock)
                            );
                            return Flux.just(new ProductoConSucursal(sucursalConProducto));
                        } else {
                            return Flux.empty();
                        }
                    })
            );
    }
}
