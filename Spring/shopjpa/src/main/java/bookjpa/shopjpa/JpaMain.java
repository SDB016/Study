package bookjpa.shopjpa;

import bookjpa.shopjpa.domain.Member;
import bookjpa.shopjpa.domain.OrderItem;
import bookjpa.shopjpa.domain.Orders;
import bookjpa.shopjpa.domain.item.Book;
import bookjpa.shopjpa.domain.item.Item;
import bookjpa.shopjpa.domain.item.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("kim");

            em.persist(book);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
