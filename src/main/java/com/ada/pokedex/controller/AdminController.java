package com.ada.pokedex.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ada.pokedex.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Administração", description = "Endpoints acessíveis apenas por Admins")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/promote/{username}")
    @Operation(summary = "Promove um usuário para o nível ADMIN")
    public ResponseEntity<String> promoverParaAdmin(@PathVariable String username) {
        adminService.promoverParaAdmin(username);
        return ResponseEntity.ok("Usuário " + username + " foi promovido para ADMIN.");
    }
}