package com.eccomerce.userEntity;

import com.eccomerce.client.entity.Client;
import com.eccomerce.client.repository.ClientRepository;
import com.eccomerce.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class AuthServiceImpl implements AuthService {

    private final ClientRepository clientRepository;
    private final EmailService emailService;
    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(ClientRepository clientRepository, EmailService emailService, UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.emailService = emailService;
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void resetPassword(String email) {

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No existe una cuenta con ese correo"));

        // 1. Generar contraseña aleatoria
        String newPassword = generateRandomPassword();

        // 2. Hashear contraseña
        String hashedPassword = passwordEncoder.encode(newPassword);

        UserEntity userEntity = userEntityRepository.findUserByClientEmail(client.getEmail()).orElseThrow(()-> new RuntimeException("Error no existe un mail " +
                "asociado a un cliente"));

        // 3. Guardar en DB
        userEntity.setPassword(hashedPassword);
        userEntityRepository.save(userEntity);

        // 4. Enviar email con la nueva contraseña
        emailService.sendEmail(
                client.getEmail(),
                "Restablecimiento de contraseña",
                "Hola " + client.getName() + ",\n\n" +
                        "Tu contraseña temporal es: " + newPassword + "\n\n" +
                        "Se recomienda cambiarla desde tu perfil apenas ingreses.\n\n" +
                        "Saludos."
        );
    }

    // Método para generar la contraseña random
    private String generateRandomPassword() {
        int length = 10;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }
}

