package repo;

import entity.Book;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@ManagedBean(name = "libraryRepo")
@ApplicationScoped
public class LibraryRepo {

    public List<Book> getBooksFromDB() {
        EntityManager entityManager;
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("lol");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        return entityManager.createNativeQuery("SELECT * from books", Book.class).getResultList();
    }
}
