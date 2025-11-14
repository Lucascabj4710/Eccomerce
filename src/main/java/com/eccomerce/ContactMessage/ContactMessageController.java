package com.eccomerce.ContactMessage;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody ContactMessageDto dto) {
        contactMessageService.save(dto);
        return ResponseEntity.ok("Mensaje enviado correctamente.");
    }
}