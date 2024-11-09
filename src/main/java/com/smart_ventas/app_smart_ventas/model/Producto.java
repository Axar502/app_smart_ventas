package com.smart_ventas.app_smart_ventas.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "producto")  // Opcional, pero ayuda a que sea explícito
public class Producto {

    @Id
    @Column(name = "codigo_producto") // Mapea la columna "codigo_producto" en la base de datos
    private Integer codigoProducto; // El código del producto será ingresado manualmente como Integer

    private String nombreProducto;

    private BigDecimal precioUnitario; // Tipo BigDecimal para precios con decimales

    private Long cantidadProducto;

}
