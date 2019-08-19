package beans;

import entity.Book;
import repo.BasketRepo;
import repo.MyBookRepo;
import service.LibService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean(name = "basket")
@ViewScoped
public class BasketBean {

    @ManagedProperty("#{libService}")
    private LibService service;

    @ManagedProperty("#{myBookRepo}")
    private MyBookRepo myBookRepo;

    @ManagedProperty("#{basketRepo}")
    private BasketRepo basketRepo;

    public List<Book> getBasket() {

        List<Integer> books = service.getCookies()
                .stream()
                .filter(cookie -> cookie.getName().contains("book"))
                .map(cookie -> Integer.parseInt(cookie.getValue()))
                .collect(Collectors.toList());

        if (!books.isEmpty()) {

            EntityManager entityManager;
            EntityManagerFactory factory = Persistence
                    .createEntityManagerFactory("lol");
            entityManager = factory.createEntityManager();

            entityManager.getTransaction().begin();

            String query = "select b from Book as b where b.id in" + "(" + books + ")";
            query = query.replaceAll("\\[", "").replaceAll("\\]", "");

            List<Book> listBook = entityManager.createQuery(query).getResultList();
            entityManager.flush();
            entityManager.getTransaction().commit();

            return listBook;
        }
        return null;
    }

    public void save() {
        List<Book> bookInBasket = getBasket();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        bookInBasket.stream().filter(book -> book.getQuantity() != 0).forEach(book -> {
            basketRepo.saveUserBooksAndUpdateBooks(book);
        });

        //удаление кук
        service.getCookies().stream().filter(cookie -> cookie.getName().contains("book")).forEach(cookie -> {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        });
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "library.xhtml");
    }

    public void deleteBookFromBasket(Book book) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        service.getCookies().stream().filter(cookie -> cookie.getName().contains("book" + book.getId())).forEach(cookie -> {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        });
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

    public BasketRepo getBasketRepo() {
        return basketRepo;
    }

    public void setBasketRepo(BasketRepo basketRepo) {
        this.basketRepo = basketRepo;
    }
}
