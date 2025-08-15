package com.eccomerce.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class prueba {

    public enum EstadoPedido {
        PENDIENTE,
        PAGADO,
        CANCELADO
    }

    public static void main(String[] args) {



        String estado = "pagado";
        estado.toUpperCase(Locale.ROOT);
        System.out.println(estado.toUpperCase(Locale.ROOT));

        if (estado.toUpperCase(Locale.ROOT).equals(EstadoPedido.PAGADO.name())){
            System.out.println(estado.toUpperCase(Locale.ROOT));
        } else {
            System.out.println("ERROR");
        }





    }

}
