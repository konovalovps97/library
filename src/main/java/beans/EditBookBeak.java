package beans;


import entity.Book;
import repo.EditBookRepo;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "editBook")
@SessionScoped
public class EditBookBeak {

    @ManagedProperty("#{editBookRepo}")
    private EditBookRepo editBookRepo;

    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @PostConstruct
    public void init() {
        book = getBook();
    }

    public void editBook(Book book) {
        editBookRepo.saveEditBookBook(book);
    }

    public EditBookRepo getEditBookRepo() {
        return editBookRepo;
    }

    public void setEditBookRepo(EditBookRepo editBookRepo) {
        this.editBookRepo = editBookRepo;
    }
}
