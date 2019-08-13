package service;

import entity.Book;
import entity.Car;
import entity.User;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ManagedBean(name = "libService")
@ApplicationScoped
public class LibService {

    public List<Book> getBooks() {



        return null;
        /*List<Car> list = new ArrayList<Car>();
        for(int i = 0 ; i < size ; i++) {
            list.add(new Car(getRandomId(), getRandomBrand(), getRandomYear(), getRandomColor(), getRandomPrice(), getRandomSoldState()));
        }*/

        /*return list;*/
    }

    private String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private int getRandomYear() {
        return (int) (Math.random() * 50 + 1960);
    }


    private int getRandomPrice() {
        return (int) (Math.random() * 100000);
    }

    private boolean getRandomSoldState() {
        return (Math.random() > 0.5) ? true: false;
    }

}
