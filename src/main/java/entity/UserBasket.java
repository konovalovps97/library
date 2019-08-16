package entity;

import javax.persistence.*;

@Entity
@Table(name = "user_books")
public class UserBasket {

    @Id
    // @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id_book_in_basket", columnDefinition = "serial")
    private Integer id;

    @Column(name = "phone_number")
    private Long phoneNumber;

    private Boolean status;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_id")
    private Long bookId;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "book_id", insertable = false, updatable = false)
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public UserBasket(Long phoneNumber, Boolean status, Long bookId, Integer id, String bookName) {
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.bookId = bookId;
        this.id = id;
        this.bookName = bookName;
    }

    public UserBasket() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
