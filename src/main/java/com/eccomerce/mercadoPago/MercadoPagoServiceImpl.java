package com.eccomerce.mercadoPago;

import com.eccomerce.cartDetail.dto.CartDetailResponseDto;
import com.eccomerce.product.entity.Product;
import com.eccomerce.product.repository.ProductRepository;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceBackUrls;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoServiceImpl implements MercadoPagoService{

    @Value("${meli.access.token}")
    private String accessToken;

    @Value("${meli.public.key}")
    private String publicKey;

    private final ProductRepository productRepository;

    public MercadoPagoServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public String buyMP(List<CartDetailResponseDto> cartDetailResponseDtos) {

        MercadoPagoConfig.setAccessToken(accessToken);

        List<PreferenceItemRequest> items = new ArrayList<>();

        for (CartDetailResponseDto cartDetailResponseDto : cartDetailResponseDtos){

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .id(cartDetailResponseDto.getProductResponseDto().getId().toString())
                    .categoryId(cartDetailResponseDto.getProductResponseDto().getCategoryDesc())
                    .title(cartDetailResponseDto.getProductResponseDto().getName())
                    .unitPrice(new BigDecimal(cartDetailResponseDto.getProductResponseDto().getPrice()))
                    .quantity(Math.toIntExact(cartDetailResponseDto.getQuantity()))
                    .pictureUrl(cartDetailResponseDto.getProductResponseDto().getImageUrl())
                    .currencyId("ARS")
                    .build();

            items.add(item);
        }

        PreferenceBackUrlsRequest preferenceBackUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success("https://abcd1234.ngrok-free.dev/order/buyCart")
                .failure("https://x.com/home")
                .build();





        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .notificationUrl("https://nonvacant-noemi-pristinely.ngrok-free.dev/mp/ipn")
                .backUrls(preferenceBackUrlsRequest)
                .build();

        PreferenceClient preferenceClient = new PreferenceClient();

        try {
            Preference preference = preferenceClient.create(preferenceRequest);
            return preference.getSandboxInitPoint();
        } catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
        }

    }
}
