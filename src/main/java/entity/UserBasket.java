package entity;

import javax.persistence.*;

@Entity
@Table(name = "user_books")
public class UserBasket {

    @Id
   // @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", columnDefinition = "serial")
    private Integer id;

    @Column(name = "phone_number")
    private Long phoneNumber;

    private Boolean status;

    @Column(name = "book_id")
    private Long bookId;

    public UserBasket(Long phoneNumber, Boolean status, Long bookId, Integer id) {
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.bookId = bookId;
        this.id = id;
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
}
