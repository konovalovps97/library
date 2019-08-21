package beans;

import entity.Book;
import entity.UserBasket;
import org.primefaces.context.RequestContext;
import repo.LibraryRepo;
import service.LibService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean(name = "lib")
@ViewScoped
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

    @ManagedProperty("#{libraryRepo}")
    private LibraryRepo libraryRepo;

    @ManagedProperty("#{editBook}")
    private EditBookBeak editBookBeak;

    @PostConstruct
    public void init() {
        books = getBooks();
    }

    private List<Book> getBooks() {

        List<Book> listPersons = libraryRepo.getBooksFromDB();
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

    public void goToEdit(Book book) {
        editBookBeak.setBook(book);
      /*  FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        return  "/view/editBook.xhtml";*/
    }

    public List<Book> getBooksInLib() {
        return books;
    }

    public List<UserBasket> getHistory() {
        return libraryRepo.getHistoryBookFromDB();
    }

    public List<Book> getBasket() {
        return basket;
    }

    public LibraryRepo getLibraryRepo() {
        return libraryRepo;
    }

    public void setLibraryRepo(LibraryRepo libraryRepo) {
        this.libraryRepo = libraryRepo;
    }

    public EditBookBeak getEditBookBeak() {
        return editBookBeak;
    }

    public void setEditBookBeak(EditBookBeak editBookBeak) {
        this.editBookBeak = editBookBeak;
    }
}
