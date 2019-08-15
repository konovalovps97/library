package entity;

import service.LibService;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "book_name")
    private String bookName;

    private String author;

    private Integer quantity;

    @ManyToMany(mappedBy = "books")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy="book")
    private List<UserBasket> booksInBasket;

    public List<UserBasket> getBooksInBasket() {
        return booksInBasket;
    }

    public void setBooksInBasket(List<UserBasket> booksInBasket) {
        this.booksInBasket = booksInBasket;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Book(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
