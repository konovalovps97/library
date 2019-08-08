import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/lol")
public class Main extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.getWriter().append("Hello, PEOPLE " + request.getMethod());
        System.out.println(123);
        HttpSession session = request.getSession();


        if(session.getValue("login") == null) {
            session.putValue("login", "pavel");
        } else {
            response.getWriter().append((String) session.getValue("login"));
        }
    }
}
