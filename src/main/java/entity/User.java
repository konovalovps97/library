package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "phone_number")
    private Long phoneNumber;

    private String password;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "user_books",
            joinColumns = { @JoinColumn(name = "phone_number") },
            inverseJoinColumns = { @JoinColumn(name = "book_id") }
    )
    Set<Book> books = new HashSet<>();

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
