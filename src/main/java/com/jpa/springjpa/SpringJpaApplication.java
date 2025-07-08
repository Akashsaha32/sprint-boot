package com.jpa.springjpa;


import com.jpa.springjpa.dto.ProductSearchDTO;
import com.jpa.springjpa.entity.Product;
import com.jpa.springjpa.entity.Student;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.hibernate.jpa.QueryHints;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SpringJpaApplication {
    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_01");
    private final static EntityManager em = emf.createEntityManager();
    public static void main(String[] args) {

        var filter = new ProductSearchDTO(
                "Mac",
                "Laptop",
                20000,
                180000,
                List.of("HP", "Samsung", "DELL", "MAC")
        );

        transactional((em) -> {
            // need to customization or category wise search
            /*CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Student> cq = cb.createQuery(Student.class);
            Root<Student> root = cq.from(Student.class);
            root.fetch("courses", JoinType.LEFT);
            cq.where(cb.equal(root.get("name"), "Akash Saha"));
            //cq.where(cb.equal(root.get("courses").get("code"), "CSE-231"));

            TypedQuery<Student> query = em.createQuery(cq);
            query.getResultStream().forEach(System.out::println);
             */


            /*CriteriaBuilder cb = em.getCriteriaBuilder();
            //CriteriaQuery<String> cq = cb.createQuery(String.class);
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<Student> root = cq.from(Student.class);
            //root.fetch("courses", JoinType.LEFT);
            cq.multiselect(root.get("name"), root.get("cgpa"));
            cq.where(cb.equal(root.get("id"), 1));
            //cq.where(cb.equal(root.get("name"), "Akash Saha"));
            //cq.where(cb.equal(root.get("courses").get("code"), "CSE-231"));

            TypedQuery<Object[]> query = em.createQuery(cq);
            query.getResultStream().forEach((a) -> {
                System.out.println(a[0]+", "+a[1]);
            });*/

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);

            ArrayList<Predicate> list = new ArrayList<Predicate>();

            if(filter.searchValue() != null && !filter.searchValue().isEmpty()) {
                String value = filter.searchValue();
                Predicate np = cb.like(root.get("name"), "%" + value + "%");
                var  dp = cb.like(root.get("description"), "%" + value + "%");
                list.add(cb.or(np, dp));
            }

            if(filter.category() != null) {
                list.add(cb.equal(root.get("category"), filter.category()));
            }

            if(filter.min_price() > 0 && filter.max_price() > 0 && filter.min_price() < filter.max_price()) {
                list.add(cb.between(root.get("price"), filter.min_price(), filter.max_price()));
            }

            if(filter.brand() != null) {
                Predicate p = root.get("brand").in(filter.brand());
                list.add(p);
            }

            cq.where(list.toArray(new Predicate[0]));
            var q = em.createQuery(cq);
            q.getResultStream().forEach(System.out::println);
        });
    }

    private static void transactional(Consumer<EntityManager> consumer) {
        // @transaction annotation work like this
        em.getTransaction().begin(); //before
        try {
            consumer.accept(em);
        }catch (Exception e) {
            em.getTransaction().rollback(); //after throw
            throw new RuntimeException("Error in transaction");
        }
        em.getTransaction().commit(); //after advice
        em.close();
        emf.close();
    }

    private static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}