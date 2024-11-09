package com.smart_ventas.app_smart_ventas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_ventas.app_smart_ventas.model.Producto;
import com.smart_ventas.app_smart_ventas.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

  // Obtener un producto por ID
  public Producto obtenerProductoPorId(Long codigoProducto) {
    Optional<Producto> producto = productoRepository.findById(codigoProducto);
    return producto.orElse(null); // Si no encuentra el producto, retorna null
}

// Eliminar un producto por ID
public void eliminarProducto(Long codigoProducto) {
    productoRepository.deleteById(codigoProducto);
}

// Agrega más métodos si es necesario
}

  

