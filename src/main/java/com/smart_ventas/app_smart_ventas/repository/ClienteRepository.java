package com.smart_ventas.app_smart_ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_ventas.app_smart_ventas.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Puedes agregar métodos personalizados si es necesario
}
