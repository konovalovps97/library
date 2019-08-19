package repo;

import entity.User;
import entity.UserBasket;
import service.LibService;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@ManagedBean(name = "myBookRepo")
@ApplicationScoped
public class MyBookRepo {

    @ManagedProperty("#{libService}")
    private LibService service;


    public List getBooks() {
        Thread.currentThread().getContextClassLoader().getResource("META-INF/persistence.xml");
        EntityManagerFactory emfdb = Persistence.createEntityManagerFactory("lol");

        EntityManager entityManager = emfdb.createEntityManager();
        User user = entityManager.find(User.class, service.getPhoneNumber());

        String query = "SELECT b FROM  UserBasket  as b where b.phoneNumber = " + user.getPhoneNumber() + " and b.status = " + Boolean.TRUE;
        return entityManager.createQuery(query).getResultList();
    }

    public void deleteBookFromOrder(UserBasket userBasket) {
        EntityManager entityManager;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("lol");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("update user_books set status =" + Boolean.FALSE + " " + " where id_book_in_basket =  " + userBasket.getId()).executeUpdate();
        entityManager.getTransaction().commit();

       // service.reloadPage();
    }

    public LibService getService() {
        return service;
    }

    public void setService(LibService service) {
        this.service = service;
    }
}
