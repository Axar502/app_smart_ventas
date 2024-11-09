package com.smart_ventas.app_smart_ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smart_ventas.app_smart_ventas.model.Producto;
import com.smart_ventas.app_smart_ventas.service.ProductoService;

@Controller
@RequestMapping("/productos") // Aquí cambiamos la raíz para evitar el conflicto con clientes
public class ControladorProducto {

    @Autowired
    private ProductoService productoService;

    // Método para la página de inicio
    @GetMapping("/")
    public String index() {
        return "index"; // Página de inicio
    }

    @GetMapping
    public String listarProductos(Model model) {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        model.addAttribute("productos", productos);
        return "productos"; // Vista con la lista de productos
    }

    @PostMapping
    public String agregarProducto(Producto producto, RedirectAttributes redirectAttributes) {
        try {
            productoService.guardarProducto(producto);
            redirectAttributes.addFlashAttribute("successMessage", "Producto agregado con éxito.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/productos"; // Redirige a la lista de productos
    }

    // Método para eliminar un producto
    @GetMapping("/eliminar")
    public String eliminarProducto(@RequestParam("codigo_producto") Long codigoProducto, RedirectAttributes redirectAttributes) {
        try {
            productoService.eliminarProducto(codigoProducto); // Elimina el producto por ID
            redirectAttributes.addFlashAttribute("successMessage", "Producto eliminado con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el producto.");
        }
        return "redirect:/productos"; // Redirige a la lista de productos
    }

    // Método para mostrar el formulario de edición con los datos del producto
    @GetMapping("/editar")
    public String editarProducto(@RequestParam("codigo_producto") Long codigoProducto, Model model) {
        Producto producto = productoService.obtenerProductoPorId(codigoProducto); // Recupera el producto por ID
        if (producto != null) {
            model.addAttribute("producto", producto); // Pasamos el producto a la vista
            return "editar_producto"; // Vista para editar el producto
        }
        return "redirect:/productos"; // Si no se encuentra el producto, redirige a productos
    }

    // Método para actualizar el producto
    @PostMapping("/editar")
    public String actualizarProducto(Producto producto, RedirectAttributes redirectAttributes) {
        try {
            productoService.guardarProducto(producto); // Reutilizamos el método guardar para actualizar
            redirectAttributes.addFlashAttribute("successMessage", "Producto actualizado con éxito.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/productos"; // Redirige a la lista de productos
    }
}
