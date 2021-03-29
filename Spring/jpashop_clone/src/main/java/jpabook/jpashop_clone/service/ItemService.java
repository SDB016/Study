package jpabook.jpashop_clone.service;

import jpabook.jpashop_clone.domain.item.Book;
import jpabook.jpashop_clone.domain.item.Item;
import jpabook.jpashop_clone.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public List<Book> findAllBook(){
        return itemRepository.findAllBook();
    }

    @Transactional
    public void updateItem(Long itemId, String name, String author, int price, int stockQuantity, String isbn) {

        Book item = (Book) itemRepository.findOne(itemId);

        item.change(name, price, stockQuantity, author, isbn);
    }
}
