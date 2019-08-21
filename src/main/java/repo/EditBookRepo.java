package repo;


import entity.Book;
import entity.UserBasket;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean(name = "editBookRepo")
@ApplicationScoped
public class EditBookRepo {

    public void saveEditBookBook(Book book) {
        EntityManager entityManager;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("lol");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        //сохранение в user_books
       // entityManager.refresh(book);

        //update в books
      //  entityManager.flush();
        entityManager.merge(book);
        entityManager.getTransaction().commit();

    }
}
