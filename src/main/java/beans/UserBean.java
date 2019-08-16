package beans;

import entity.Book;
import entity.User;
import entity.UserBasket;
import org.eclipse.persistence.jpa.JpaEntityManager;
import service.LibService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@ManagedBean(name = "user")
@ApplicationScoped
public class UserBean {

    public List<User> users;

    @PostConstruct
    public void init() {
        users = getUsersFromDB();
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


    public List<User> getUsersFromDB() {
        EntityManager entityManager;
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("lol");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        List<User> listPersons = entityManager.createQuery("SELECT u FROM  User as u").getResultList();

        return listPersons;
    }



}
