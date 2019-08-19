package beans;

import entity.Book;
import entity.UserBasket;
import org.primefaces.context.RequestContext;
import service.LibService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean(name = "lib")
@SessionScoped
public class LibraryBean {

    String bookName;

    String author;

    Integer quantity;

    private List<Book> books;
    private List<Book> basket;

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

    public void setBasket(List<Book> basket) {
        this.basket = basket;
    }

    public LibService getService() {
        return service;
    }

    public void setService(LibService service) {
        this.service = service;
    }

    @ManagedProperty("#{libService}")
    private LibService service;

    @PostConstruct
    public void init() {
        books = getBooks();
    }

    private List<Book> getBooks() {
        EntityManager entityManager;
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("lol");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Book> listPersons = entityManager.createNativeQuery("SELECT * from books", Book.class).getResultList();
        if (listPersons == null) {
            System.out.println("all is bad");
        }
        RequestContext.getCurrentInstance().update("main:tbl");
        return listPersons;
    }

    public void addBook(Book book) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        List<Cookie> cookies = new ArrayList<>(Arrays.asList(request.getCookies()));

        if (!cookies.stream().anyMatch(cookie -> cookie.getValue().equals(book.getId().toString())) && book.getQuantity() != 0) {
            response.addCookie(new Cookie("book" + book.getId(), book.getId().toString()));
        }

        service.reloadPage();
    }

    public List<Book> getBooksInLib() {
        return books;
    }

    public List<Book> getBasket() {

        List<Integer> books = service.getCookies()
                .stream()
                .filter(cookie -> cookie.getName().contains("book"))
                .map(cookie -> Integer.parseInt(cookie.getValue()))
                .collect(Collectors.toList());

        if (!books.isEmpty()) {

            EntityManager entityManager;
            EntityManagerFactory factory = Persistence
                    .createEntityManagerFactory("lol");
            entityManager = factory.createEntityManager();

            entityManager.getTransaction().begin();

            String query = "select b from Book as b where b.id in" + "(" + books + ")";
            query = query.replaceAll("\\[", "").replaceAll("\\]", "");

            List<Book> listBook = entityManager.createQuery(query).getResultList();
            entityManager.flush();
            entityManager.getTransaction().commit();

            return listBook;
        }
        return null;
    }

    public void save() throws IOException {
        List<Book> bookInBasket = getBasket();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        bookInBasket.stream().filter(book -> book.getQuantity() != 0).forEach(book -> {
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

        });

        //удаление кук
        service.getCookies().stream().filter(cookie -> cookie.getName().contains("book")).forEach(cookie -> {

            cookie.setMaxAge(0);
            response.addCookie(cookie);
        });
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "library.xhtml");

    }

    public void deleteBookFromBasket(Book book) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        service.getCookies().stream().filter(cookie -> cookie.getName().contains("book" + book.getId())).forEach(cookie -> {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        });

        service.reloadPage();
    }

    public List<UserBasket> getHistory() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        EntityManagerFactory emfdb = Persistence.createEntityManagerFactory("lol");

        EntityManager entityManager = emfdb.createEntityManager();
        String query = "SELECT b FROM  UserBasket  as b where b.phoneNumber = " + service.getPhoneNumber();
        List<UserBasket> userBaskets = entityManager.createQuery(query).getResultList();

        return userBaskets;
    }
}
