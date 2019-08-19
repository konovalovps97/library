package beans;

import entity.User;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

@ManagedBean(name = "auth")
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


    public void init() throws IOException {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        if (request.getCookies() != null) {
            if (Arrays.asList(request.getCookies()).stream().anyMatch(cookie -> "phone".equals(cookie.getName()))) {
                response.sendRedirect(request.getContextPath() + "/view/library.xhtml");
            }
        }

    }

    public void login() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        try {
            Thread.currentThread().getContextClassLoader().getResource("META-INF/persistence.xml");
            EntityManagerFactory emfdb = Persistence.createEntityManagerFactory("lol");

            DriverManager.registerDriver(new org.postgresql.Driver());

            EntityManager entityManager = emfdb.createEntityManager();
            User user = entityManager.find(User.class, phone);


            if (user.getPassword().equals(password)) {
                Cookie cookie = new Cookie("phone", phone.toString());
                cookie.setMaxAge(86400);

                response.addCookie(cookie);
               //response.flushBuffer();
                FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "library.xhtml");

                //FacesContext.getCurrentInstance().getExternalContext().redirect("library.xhtml");
                //return "/view/library.xhtml?faces-redirect=true";
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println(123);
        }

        //return "";
    }


}
// Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "test_user", "test_user");
//User user = entityManager.find(User.class, phone);