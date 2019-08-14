package beans;

import entity.Book;
import org.primefaces.component.collector.Collector;
import service.LibService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean(name = "lib")
@ApplicationScoped
public class LibraryBean {

    String bookName;

    String author;

    Integer quantity;

    private List<Book> books;
    private List<Book> basket;

    @ManagedProperty("#{libService}")
    private LibService service;

    @PostConstruct
    public void init() {
        books = getBooks();
        basket = getBasket();
    }


    private List<Book> getBooks() {
        EntityManager entityManager;
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("lol");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Book> listPersons = entityManager.createQuery("SELECT b FROM  Book as b").getResultList();
        if (listPersons == null) {
            System.out.println("all is bad");
        }
        return listPersons;
    }

    public void addBook(Book book) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        List<Cookie> cookies = new ArrayList<>(Arrays.asList(request.getCookies()));

        System.out.println(123);


        if (!cookies.stream().anyMatch(cookie -> cookie.getValue().equals(book.getId().toString())) && book.getQuantity() != 0) {
            response.addCookie(new Cookie("book" + book.getId(), book.getId().toString()));
        }

            /*
            EntityManager entityManager;
            EntityManagerFactory factory = Persistence
                    .createEntityManagerFactory("lol");
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();

            entityManager.createNativeQuery("update books set quantity =" + String.valueOf(book.getQuantity() - 1) + " where id = " + book.getId()).executeUpdate();
            entityManager.getTransaction().commit();*/
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public LibService getService() {
        return service;
    }

    public List<Book> getCars() {
        return books;
    }

    public void setService(LibService service) {
        this.service = service;
    }

    public List<Book> getBasket() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        List<Cookie> cookies = new ArrayList<>(Arrays.asList(request.getCookies()));

        List<Integer> books = cookies
                .stream()
                .filter(cookie -> cookie.getName().contains("book"))
                .map(cookie -> Integer.parseInt(cookie.getValue()))
                .collect(Collectors.toList());

        EntityManager entityManager;
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("lol");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        String query = "select b from Book as b where b.id in" + "(" + books + ")";
        query = query.replaceAll("\\[", "").replaceAll("\\]", "");

        List<Book> listBook = (List<Book>) entityManager.createQuery(query).getResultList();

        return listBook;
    }
}
