package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long save(Item item){
        itemRepository.save(item);
        return item.getId();
    }


    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

    //변경 감지 기능 사용 (Dirty Checking)
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity, String author, String isbn){
        Book findItem = (Book) itemRepository.findOne(itemId); //트랜잭션 안에서 조회 -> 영속 상태
        findItem.change(name, price, stockQuantity, author, isbn);
    }


}
