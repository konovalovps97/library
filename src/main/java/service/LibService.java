package service;

import entity.Book;
import entity.Car;
import entity.User;

import javax.faces.application.ViewHandler;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@ManagedBean(name = "libService")
@ApplicationScoped
public class LibService {

    public void reloadPage() {
        String refreshpage = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        ViewHandler handler = FacesContext.getCurrentInstance().getApplication().getViewHandler();
        UIViewRoot root = handler.createView(FacesContext.getCurrentInstance(), refreshpage);
        root.setViewId(refreshpage);
        FacesContext.getCurrentInstance().setViewRoot(root);
    }

    public Integer gen() {
        Random r = new Random(System.currentTimeMillis());
        return ((1 + r.nextInt(2)) * 2 + r.nextInt(10000));
    }

    public List<Cookie> getCookies() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        List<Cookie> cookies = new ArrayList<>(Arrays.asList(request.getCookies()));
        return cookies;
    }

    public Long getPhoneNumber() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        Cookie[] cookies = request.getCookies();
        cookies[0].getName();
        Long phoneNumber = Long.parseLong(Arrays.stream(request.getCookies())
                .filter(cookie -> "phone".equals(cookie.getName()))
                .collect(Collectors.toList())
                .get(0)
                .getValue());
        return phoneNumber;
    }
}
