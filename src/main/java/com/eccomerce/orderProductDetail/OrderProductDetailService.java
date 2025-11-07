package com.eccomerce.orderProductDetail;

import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;
import java.util.Map;

public interface OrderProductDetailService {

    public Map<String, String> createOrderProductDetail(List<OrderProductDetailDto> orderProductDetailDto);
    public List<OrderProductDetailResponse> getAllOrderProductDetail();
    public List<OrderProductDetailResponse> getAllOrderProductDetails(Long orderId, Long clientId);
    public OrderProductDetail getOrderProductDetailID(Long id);
    public Map<String, String> deleteOrderProductDetail(Long id);
    public Map<String, String> updateOrderProductDetail(Long id, OrderProductDetail orderProductDetail);

}
