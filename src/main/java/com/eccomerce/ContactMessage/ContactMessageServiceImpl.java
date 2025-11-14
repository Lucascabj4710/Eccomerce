package com.eccomerce.ContactMessage;

import com.eccomerce.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository repository;
    private final EmailService emailService;

    // Mail donde vos recibís los mensajes del formulario
    @Value("${app.contact.admin-email}")
    private String adminEmail;

    @Override
    public void save(ContactMessageDto dto) {
        // 1) Guardar en la base de datos
        ContactMessage contact = ContactMessage.builder()
                .nombreCompleto(dto.getNombreCompleto())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .mensaje(dto.getMensaje())
                .build();

        repository.save(contact);

        // 2) Enviar mail al admin (vos)
        String subjectAdmin = "Nuevo mensaje de contacto - Paradiise";
        String bodyAdmin = "Hola, recibiste un nuevo mensaje desde el formulario de contacto:\n\n"
                + "Nombre: " + dto.getNombreCompleto() + "\n"
                + "Email: " + dto.getEmail() + "\n"
                + "Teléfono: " + dto.getTelefono() + "\n\n"
                + "Mensaje:\n" + dto.getMensaje() + "\n";

        emailService.sendEmail(adminEmail, subjectAdmin, bodyAdmin);

        // 3) (Opcional pero piola) Confirmación al usuario
        String subjectUser = "Hemos recibido tu mensaje - Paradiise";
        String bodyUser = "Hola " + dto.getNombreCompleto() + ",\n\n"
                + "Gracias por contactarte con Paradiise. Hemos recibido tu mensaje y nos pondremos en contacto a la brevedad.\n\n"
                + "Copia de tu mensaje:\n"
                + dto.getMensaje() + "\n\n"
                + "Saludos,\nParadiise";

        emailService.sendEmail(dto.getEmail(), subjectUser, bodyUser);
    }
}