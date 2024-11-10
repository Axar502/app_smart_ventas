package com.smart_ventas.app_smart_ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smart_ventas.app_smart_ventas.model.Cliente;
import com.smart_ventas.app_smart_ventas.service.ClienteService;

@Controller
public class ControladorCliente {

    @Autowired
    private ClienteService clienteService;

    // Método para la página de inicio
    @GetMapping("/")
    public String index() {
        return "index"; // Página de inicio
    }

    // Listar clientes
    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.obtenerClientes();
        model.addAttribute("clientes", clientes);
        return "clientes"; // Vista con la lista de clientes
    }

    // Mostrar el formulario para agregar un nuevo cliente
    @GetMapping("/clientes/nuevo")
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("cliente", new Cliente());  // Crea un cliente vacío para el formulario
        return "formulario_cliente"; // Vista para el formulario
    }

    // Agregar un nuevo cliente
    @PostMapping("/clientes/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            clienteService.guardarCliente(cliente);  // La base de datos asignará id_cliente automáticamente
            redirectAttributes.addFlashAttribute("successMessage", "Cliente agregado con éxito.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/clientes"; // Redirige a la lista de clientes
    }

    // Eliminar un cliente
    @GetMapping("/clientes/eliminar")
    public String eliminarCliente(@RequestParam("idCliente") Long idCliente, RedirectAttributes redirectAttributes) {
        try {
            clienteService.eliminarCliente(idCliente);  // Eliminar cliente por ID
            redirectAttributes.addFlashAttribute("successMessage", "Cliente eliminado con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el cliente.");
        }
        return "redirect:/clientes"; // Redirigir a la lista de clientes
    }

  // Método para mostrar el formulario de edición con los datos del cliente
@GetMapping("/clientes/editar")
public String editarCliente(@RequestParam("idCliente") Long idCliente, Model model) {
    Cliente cliente = clienteService.obtenerClientePorId(idCliente) // Recupera el cliente por ID
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    model.addAttribute("cliente", cliente); // Pasamos el cliente a la vista
    return "editar_cliente"; // Vista para editar el cliente
}

// Método para actualizar el cliente
@PostMapping("/clientes/editar")
public String actualizarCliente(Cliente cliente, RedirectAttributes redirectAttributes) {
    try {
        clienteService.guardarCliente(cliente); // Reutilizamos el método guardar para actualizar
        redirectAttributes.addFlashAttribute("successMessage", "Cliente actualizado con éxito.");
    } catch (IllegalArgumentException e) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/clientes"; // Redirige a la lista de clientes
}

}
