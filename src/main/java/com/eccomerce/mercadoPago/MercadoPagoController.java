package com.eccomerce.mercadoPago;

import com.eccomerce.cartDetail.dto.CartDetailResponseDto;
import com.eccomerce.product.dto.ProductResponseDto;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("mp")
public class MercadoPagoController {

    private final MercadoPagoServiceImpl mercadoPagoService;

    public MercadoPagoController(MercadoPagoServiceImpl mercadoPagoService){
        this.mercadoPagoService = mercadoPagoService;
    }


    @PostMapping("/mercadoPago")
    public String mercadoPagoGet() throws MPException, MPApiException {

        List<CartDetailResponseDto> cartDetailResponseDtos = new ArrayList<>();


        CartDetailResponseDto cartDetail = new CartDetailResponseDto();

        ProductResponseDto product = new ProductResponseDto();
        product.setId(5L);
        product.setName("Anillo Loro");
        product.setImageUrl("/imgfolder/bb180d78-82c0-47c9-aecc-72833f47c280.jpg");
        product.setCategoryDesc("Anillos");
        product.setPrice(4000.0F);
        product.setColor("Verde");
        product.setMaterial("Plata 925");
        product.setWaist(null);
        product.setIsEnabled("ENABLED");

        cartDetail.setProductResponseDto(product);
        cartDetail.setQuantity(1L);
        cartDetail.setUnitPrice(4000.0F);





        CartDetailResponseDto cartDetail2 = new CartDetailResponseDto();

        ProductResponseDto product2 = new ProductResponseDto();
        product2.setId(4L);
        product2.setName("Anillo Perrito");
        product2.setImageUrl("/imgfolder/3aa6c211-94c2-45b5-a295-7ec2fc75f825.png");
        product2.setCategoryDesc("Anillos");
        product2.setPrice(123F);
        product2.setColor("Plateadito");
        product2.setMaterial("Plata 925");
        product2.setWaist("11");
        product2.setIsEnabled("ENABLED");

        cartDetail2.setProductResponseDto(product);
        cartDetail2.setQuantity(1L);
        cartDetail2.setUnitPrice(123F);




        cartDetailResponseDtos.add(cartDetail);
        cartDetailResponseDtos.add(cartDetail2);



        return mercadoPagoService.buyMP(cartDetailResponseDtos);
    }

    // Endpoint para recibir notificaciones de Mercado Pago (IPN/Webhook)
    @PostMapping("/ipn")
    public ResponseEntity<String> recibirIPN(@RequestBody String payload) {
        System.out.println("Notificación recibida: " + payload);
        // parsear JSON y actualizar pedido/estado
        return ResponseEntity.ok("OK");
    }

}
