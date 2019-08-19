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

@ManagedBean(name = "basketRepo")
@ApplicationScoped
public class BasketRepo {

    @ManagedProperty("#{libService}")
    private LibService service;

    public void saveUserBooksAndUpdateBooks(Book book) {
        EntityManager entityManager;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("lol");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        UserBasket userBasket = new UserBasket(service.getPhoneNumber(), Boolean.TRUE, book.getId(), service.gen(), book.getBookName());

        //сохранение в user_books
        entityManager.persist(userBasket);

        //update в books
        entityManager.createNativeQuery("update books set quantity =" + (book.getQuantity() - 1) + " where book_id = " + book.getId()).executeUpdate();
        entityManager.flush();

        entityManager.getTransaction().commit();
        entityManager.merge(userBasket);
        entityManager.merge(book);
    }

    public LibService getService() {
        return service;
    }

    public void setService(LibService service) {
        this.service = service;
    }
}
