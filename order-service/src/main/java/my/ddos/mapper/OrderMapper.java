package my.ddos.mapper;

import my.ddos.model.OrderResponse;
import my.ddos.model.dto.OrderRequest;
import my.ddos.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderRequest orderRequest);
    OrderResponse toResponse(Order order);
}
