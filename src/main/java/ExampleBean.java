import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@Dependent
@ManagedBean(name = "lol")
@SessionScoped
public class ExampleBean {

    private String login = "pavel";

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
