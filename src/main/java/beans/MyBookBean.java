package beans;

import entity.Book;
import entity.UserBasket;
import repo.MyBookRepo;
import service.LibService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name = "myBookBean")
@ViewScoped
public class MyBookBean {

    List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    @PostConstruct
    public void init() {
        books = myBookRepo.getBooks();
    }

    @ManagedProperty("#{myBookRepo}")
    private MyBookRepo myBookRepo;

    public void deleteBookFromOrder(UserBasket userBasket) {
        myBookRepo.deleteBookFromOrder(userBasket);
    }

    public MyBookRepo getMyBookRepo() {
        return myBookRepo;
    }

    public void setMyBookRepo(MyBookRepo myBookRepo) {
        this.myBookRepo = myBookRepo;
    }

}
