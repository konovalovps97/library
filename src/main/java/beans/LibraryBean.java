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

@ManagedBean(name = "lib")
@ApplicationScoped
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

        if (!cookies.stream().anyMatch(cookie -> cookie.getValue().equals(book.getId().toString())) && book.getQuantity() != 0) {
            response.addCookie(new Cookie("book" + book.getId(), book.getId().toString()));
        }

    }

    public List<Book> getCars() {
        return books;
    }

    public List<Book> getBasket() {

        List<Integer> books = getCookies()
                .stream()
                .filter(cookie -> cookie.getName().contains("book"))
                .map(cookie -> Integer.parseInt(cookie.getValue()))
                .collect(Collectors.toList());

        if (!books.isEmpty()) {

            EntityManager entityManager;
            EntityManagerFactory factory = Persistence
                    .createEntityManagerFactory("lol");
            entityManager = (JpaEntityManager) factory.createEntityManager();

            entityManager.getTransaction().begin();

            String query = "select b from Book as b where b.id in" + "(" + books + ")";
            query = query.replaceAll("\\[", "").replaceAll("\\]", "");

            List<Book> listBook = (List<Book>) entityManager.createQuery(query).getResultList();

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
            UserBasket userBasket = new UserBasket(123L, Boolean.TRUE, book.getId(), gen());

            //сохранение в user_books
            entityManager.persist(userBasket);

            //update в books
            entityManager.createNativeQuery("update books set quantity =" + (book.getQuantity() - 1) + " where book_id = " + book.getId()).executeUpdate();

            entityManager.getTransaction().commit();
        });

        //удаление кук
        getCookies().stream().filter(cookie -> cookie.getName().contains("book")).forEach(cookie -> {

            cookie.setMaxAge(0);
            response.addCookie(cookie);
        });

        String loginPage = request.getContextPath() + "/view/index.xhtml";
        response.sendRedirect(loginPage);
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

    public Set<Book> getMyBook(){
        Thread.currentThread().getContextClassLoader().getResource("META-INF/persistence.xml");
        EntityManagerFactory emfdb = Persistence.createEntityManagerFactory("lol");


        EntityManager entityManager = emfdb.createEntityManager();
        User user = entityManager.find(User.class, 123L);

        return  user.getBooks();
    }

    public void deleteBookFromBasket(Book book) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        getCookies().stream().filter(cookie -> cookie.getName().contains("book" + book.getId())).forEach(cookie -> {

            cookie.setMaxAge(0);
            response.addCookie(cookie);
        });

    }

    public void deleteBookFromOrder(Book book) {
        //book.setBooksInBasket(new ArrayList<>());
       /* try {

            System.out.println(book.getBooksInBasket());
        } catch (Throwable r) {
            r.printStackTrace();
        }*/
       //List<UserBasket> bookInUserBasket  ;

       /* List<UserBasket> bookInUserBasket = book.getBooksInBasket().stream()
                .filter(userBasket -> book.getId().longValue() == userBasket.getBookId().longValue()).collect(Collectors.toList());

        /*
                .filter(userBasket -> userBasket.getPhoneNumber().equals(123L))
                .filter(userBasket -> userBasket.getStatus().equals(Boolean.TRUE))*/
        System.out.println(123);
       long size = book.getBooksInBasket().stream().filter(userBasket -> userBasket.getBookId().toString().equals("1")).count();

                        //userBasket.getBookId().floatValue() == book.getId().floatValue()


        System.out.println(book.getBooksInBasket());
    }
}
