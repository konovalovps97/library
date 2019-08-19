package beans;

import entity.UserBasket;
import repo.MyBookRepo;
import service.LibService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name = "myBookBean")
@ViewScoped
public class MyBookBean {

    @ManagedProperty("#{libService}")
    private LibService service;

    @ManagedProperty("#{myBookRepo}")
    private MyBookRepo myBookRepo;

    public List<UserBasket> getMyBooks() {
        return myBookRepo.getBooks();
    }

    public void deleteBookFromOrder(UserBasket userBasket) {
        myBookRepo.deleteBookFromOrder(userBasket);
    }

    public LibService getService() {
        return service;
    }

    public void setService(LibService service) {
        this.service = service;
    }

    public MyBookRepo getMyBookRepo() {
        return myBookRepo;
    }

    public void setMyBookRepo(MyBookRepo myBookRepo) {
        this.myBookRepo = myBookRepo;
    }

}
