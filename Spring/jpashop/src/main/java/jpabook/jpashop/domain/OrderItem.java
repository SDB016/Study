package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //constructor 이용한 생성 막기
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 당시 가격
    private int count; //주문 수량


    //protected OrderItem() {} //createOrderItem 이외 방법으로 객체 생성 막기 (new 이용 생성)

    //==생성 메서드==//
    //할인 등 이유로 가격이 바뀔 수 있기 때문에 가격, 갯수를 받음
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderPrice(orderPrice);
        orderItem.setItem(item);
        orderItem.setCount(count);

        item.removeStockQuantity(count); //재고 빼기
        return orderItem;
    }

    //==비즈니스 로직==//

    /**
     * 재고 수량 원복
     */
    public void cancel() {
        getItem().addStockQuantity(count);
    }

    //==조회 로직==//

    /**
     *주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
