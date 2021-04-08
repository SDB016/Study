package jpabook.jpashop_clone.repository.order.query;

import jpabook.jpashop_clone.domain.Address;
import jpabook.jpashop_clone.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderFlatDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Address address;

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderFlatDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus status, Address address, String itemName, int orderPrice) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.status = status;
        this.address = address;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
    }

    public OrderFlatDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus status, Address address, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.status = status;
        this.address = address;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
