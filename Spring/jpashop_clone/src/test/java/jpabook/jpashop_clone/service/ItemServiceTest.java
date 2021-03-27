package jpabook.jpashop_clone.service;

import jpabook.jpashop_clone.domain.item.Album;
import jpabook.jpashop_clone.domain.item.Book;
import jpabook.jpashop_clone.domain.item.Item;
import jpabook.jpashop_clone.exception.NotEnoughStockException;
import jpabook.jpashop_clone.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    //아이템 저장
    @Test
    public void 아이템_저장() throws Exception {
        //given
        Item item = new Book();
        item.setName("bookA");

        //when
        Long savedItem = itemService.saveItem(item);

        //then
        assertEquals(item, itemRepository.findOne(savedItem));
        assertEquals(item.getId(), itemRepository.findOne(savedItem).getId());
    }


    //아이템 재고 삭제 후 예외 발생
    @Test(expected = NotEnoughStockException.class)
    public void 아이템_재고_삭제_예외() throws Exception {
        //given
        Item item = new Album();
        item.setName("albumA");
        item.setStockQuantity(10);

        //when
        item.removeStock(20);

        //then
        fail("예외가 발생해야 한다.");
    }


}