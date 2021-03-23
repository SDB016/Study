package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            //방법 3
            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            for (MemberDTO memberDTO : resultList) {
                System.out.println("memberDTO.getUsername()= " + memberDTO.getUsername() + ", memberDTO.getAge() =" + memberDTO.getAge());
            }

            /* 방법1 
            List resultList = em.createQuery("select m.age, m.username from Member m").getResultList();
            Object[] result = (Object[]) resultList.get(0);
            System.out.println("result[0] = " + result[0]);
            System.out.println("result[1] = " + result[1]);
            */
            
            /*방법 2
            List<Object[]> resultList = em.createQuery("select m.age, m.username from Member m", Object[].class).getResultList();
            Object[] objects = resultList.get(0);
            System.out.println("objects[0] = " + objects[0]);
            System.out.println("objects[1] = " + objects[1]);
            */
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
