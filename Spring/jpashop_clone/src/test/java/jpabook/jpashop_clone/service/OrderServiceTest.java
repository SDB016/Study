package jpabook.jpashop_clone.service;

import jpabook.jpashop_clone.domain.Address;
import jpabook.jpashop_clone.domain.Member;
import jpabook.jpashop_clone.domain.Order;
import jpabook.jpashop_clone.domain.OrderStatus;
import jpabook.jpashop_clone.domain.item.Book;
import jpabook.jpashop_clone.exception.NotEnoughStockException;
import jpabook.jpashop_clone.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

   @PersistenceContext
    EntityManager em;

   @Autowired OrderService orderService;
   @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Address address = new Address("Seoul", "광운로", "122-3");
        Member member = createMember("Shin", address);

        int orderPrice = 10000;
        int stockQuantity = 10;
        int orderCount = 4;

        Book item = createBook("시골 JPA", orderPrice, stockQuantity);


        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order findOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문 시 상태는 ORDER", OrderStatus.ORDER, findOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, findOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", orderPrice * orderCount, findOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", stockQuantity - orderCount, item.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Address address = new Address("Seoul", "광운로", "122-3");

        Member member = createMember("Kim", address);

        int orderPrice = 10000;
        int stockQuantity = 10;
        int orderCount = 20;

        Book item = createBook("bookA", orderPrice, stockQuantity);

        //when
        orderService.order(member.getId(), item.getId(), orderCount);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Address address = new Address("Seoul", "광운로", "122-3");

        Member member = createMember("Kim", address);

        int orderPrice = 10000;
        int stockQuantity = 10;
        int orderCount = 3;

        Book item = createBook("bookA", orderPrice, stockQuantity);

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancel(orderId);

        //then
        Order findOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소 시 상태는 CANCEL 이다.", OrderStatus.CANCEL, findOrder.getStatus());
        assertEquals("주문 취소 시 수량이 원복되어야 한다.", stockQuantity, item.getStockQuantity());
    }


    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book item = new Book();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        em.persist(item);
        return item;
    }

}