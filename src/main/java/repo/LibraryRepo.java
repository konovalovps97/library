package repo;

import entity.Book;
import entity.UserBasket;
import service.LibService;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@ManagedBean(name = "libraryRepo")
@ApplicationScoped
public class LibraryRepo {

    @ManagedProperty("#{libService}")
    private LibService service;


    public List<Book> getBooksFromDB() {
        EntityManager entityManager;
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("lol");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        return entityManager.createNativeQuery("SELECT * from books", Book.class).getResultList();
    }

    public List<UserBasket> getHistoryBookFromDB(){
        EntityManagerFactory emfdb = Persistence.createEntityManagerFactory("lol");

        EntityManager entityManager = emfdb.createEntityManager();
        String query = "SELECT b FROM  UserBasket  as b where b.phoneNumber = " + service.getPhoneNumber();

        return entityManager.createQuery(query).getResultList();
    }

    public LibService getService() {
        return service;
    }

    public void setService(LibService service) {
        this.service = service;
    }
}
