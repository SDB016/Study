package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    @Rollback(false)
    public void 상품추가() throws Exception {
        //given
        Album item = new Album();
        item.setName("나의 첫 앨범");
        item.setPrice(100000);
        item.setStockQuantity(1);
        item.setArtist("신동빈");
        item.setEtc("저의 첫 앨범입니다.");

        //when
        Long savedId = itemService.save(item);

        //then
        assertEquals(item, itemRepository.findOne(savedId));
    }
}

