package com.smart_ventas.app_smart_ventas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.smart_ventas.app_smart_ventas.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
