package jpabook.jpashop_clone.api;

import jpabook.jpashop_clone.domain.Address;
import jpabook.jpashop_clone.domain.Order;
import jpabook.jpashop_clone.domain.OrderSearch;
import jpabook.jpashop_clone.domain.OrderStatus;
import jpabook.jpashop_clone.repository.OrderRepository;
import jpabook.jpashop_clone.repository.OrderSimpleQueryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne (ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress();

        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2(){

        // Order 2개
        // 1 + 회원N + 배송 정보 N 문제 (V1과 쿼리 수 같음)
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return new Result(result);
    }

    @GetMapping("/api/v3/simple-orders")
    public Result ordersV3(){

        // 가장 추천하는 방법!!
        // fetch join 이용
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return new Result(result);
    }

    @GetMapping("/api/v4/simple-orders")
    public Result ordersV4(){

        // 원하는 필드만!
        List<OrderSimpleQueryDto> result = orderRepository.findOrderDtos();
        return new Result(result);
    }



    @Data
    @AllArgsConstructor
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {

            orderId = order.getId();
            name = order.getMember().getName(); //Lazy 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); //Lazy 초기화
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
