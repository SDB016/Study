package jpabook.jpashop_clone.service;

import jpabook.jpashop_clone.domain.*;
import jpabook.jpashop_clone.domain.item.Item;
import jpabook.jpashop_clone.repository.ItemRepository;
import jpabook.jpashop_clone.repository.MemberRepository;
import jpabook.jpashop_clone.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    public Long order(Long memberId, Long itemId, int count){

        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    public void cancel(Long orderId){

        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }


    /**
     * 주문 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch){return orderRepository.findAllByCriteria(orderSearch);}

}
