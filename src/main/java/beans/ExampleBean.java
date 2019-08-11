package beans;

import entity.User;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ManagedBean(name = "lol")
@Stateful
@ApplicationScoped
public class ExampleBean {

        @PersistenceUnit(unitName  = "lol")
    private EntityManagerFactory entityManagerFactory;

    private Long phone;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String login() {

        try {
          //  this.getClass().getClassLoader().getResource("META-INF/persistence.xml");
            Thread.currentThread().getContextClassLoader().getResource("META-INF/persistence.xml");
            EntityManagerFactory emfdb = Persistence.createEntityManagerFactory("lol");
            DriverManager.registerDriver(new org.postgresql.Driver());
           // Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "test_user", "test_user");
            //User user = entityManager.find(User.class, phone);
          /*  if (user.getPassword().equals(password)) {

            }*/

        } catch (SQLException  | NullPointerException e) {
            System.out.println(123);
        }

        System.out.println(123);
        return "";
    }
}
