package bookjpa.shopjpa;

import bookjpa.shopjpa.domain.Member;
import bookjpa.shopjpa.domain.OrderItem;
import bookjpa.shopjpa.domain.Orders;

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

            Orders order = new Orders();

            OrderItem orderItem = new OrderItem();
            order.addOrderItem(orderItem);

            em.persist(order);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
