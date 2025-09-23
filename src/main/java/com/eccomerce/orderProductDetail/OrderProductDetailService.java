package com.eccomerce.orderProductDetail;

import java.util.List;
import java.util.Map;

public interface OrderProductDetailService {

    public Map<String, String> createOrderProductDetail(List<OrderProductDetailDto> orderProductDetailDto);
    public List<OrderProductDetailResponse> getAllOrderProductDetail();
    public OrderProductDetail getOrderProductDetailID(Long id);
    public Map<String, String> deleteOrderProductDetail(Long id);
    public Map<String, String> updateOrderProductDetail(Long id, OrderProductDetail orderProductDetail);

}
