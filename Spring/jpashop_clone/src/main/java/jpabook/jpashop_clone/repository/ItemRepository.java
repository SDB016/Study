package jpabook.jpashop_clone.repository;

import jpabook.jpashop_clone.domain.item.Book;
import jpabook.jpashop_clone.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public Long save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            //em.merge(item);
        }
        return item.getId();
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    public List<Book> findAllBook() {
        return em.createQuery("select b from Book b", Book.class)
                .getResultList();
    }

}
