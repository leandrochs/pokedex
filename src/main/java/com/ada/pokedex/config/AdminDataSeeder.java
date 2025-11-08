package com.ada.pokedex.config;

import com.ada.pokedex.model.Role;
import com.ada.pokedex.model.Usuario;
import com.ada.pokedex.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AdminDataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminDataSeeder.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.default.username}")
    private String adminUsername;

    @Value("${admin.default.password}")
    private String adminPassword;

    public AdminDataSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByUsername(adminUsername).isEmpty()) {
            // Se a senha padrão não foi alterada, emita um GRANDE aviso
            if ("admin123".equals(adminPassword)) {
                log.warn("****************************************************************");
                log.warn("!! ATENÇÃO: SENHA DE ADMIN PADRÃO DETECTADA !!");
                log.warn("!! Por favor, mude a senha em 'admin.default.password'    !!");
                log.warn("****************************************************************");
            }
            Usuario adminUser = new Usuario(
                    adminUsername,
                    passwordEncoder.encode(adminPassword),
                    Role.ROLE_ADMIN
            );
            usuarioRepository.save(adminUser);
            log.info("Usuário ADMIN padrão '{}' criado com sucesso.", adminUsername);
        } else {
            log.info("Usuário ADMIN padrão '{}' já existe. Nenhuma ação necessária.", adminUsername);
        }
    }
}