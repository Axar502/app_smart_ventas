package com.smart_ventas.app_smart_ventas.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    // Datos quemados para usuario y contraseña
    private final String USERNAME = "admin";
    private final String PASSWORD = "password";

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                Model model) {
        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            return "redirect:/productos"; // Redirige a la página de inicio después del login
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login"; // Muestra de nuevo el formulario de login con un mensaje de error
        }
    }
}

