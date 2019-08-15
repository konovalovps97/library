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

@ManagedBean(name = "auth")
@Stateful
@ApplicationScoped
public class AuthBean {

    private Long phone;
    private String password;

    @Column(name = "is_admin")
    private Boolean admin = Boolean.FALSE;

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

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String login() {

        try {
            Thread.currentThread().getContextClassLoader().getResource("META-INF/persistence.xml");
            EntityManagerFactory emfdb = Persistence.createEntityManagerFactory("lol");

            DriverManager.registerDriver(new org.postgresql.Driver());

            EntityManager entityManager = emfdb.createEntityManager();
            User user = entityManager.find(User.class, phone);



            if (user.getPassword().equals(password)) {
                return "/view/library";
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println(123);
        }

        return "";
    }
}
// Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "test_user", "test_user");
//User user = entityManager.find(User.class, phone);