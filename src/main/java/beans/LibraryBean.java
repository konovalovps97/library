package beans;

import entity.Book;
import entity.Car;
import entity.User;
import service.LibService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "lib")
@ViewScoped
public class LibraryBean {

    String bookName;

    String author;

    Integer quantity;


    private List<Book> books;

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
        List<Book> listPersons = entityManager.createQuery("SELECT b FROM  Book as b").getResultList();
        if (listPersons == null) {
            System.out.println("all is bad");
        }
        return listPersons;
    }

    public void addBook(Book book) {
        EntityManager entityManager;
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("lol");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Book b set b.quantity = 1 where b.id = " + book.getId()).executeUpdate();
        entityManager.flush();
        System.out.println("hello.world");
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
}
