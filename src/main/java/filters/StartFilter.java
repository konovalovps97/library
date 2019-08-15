package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

//@WebFilter(filterName = "startFilter")
public class StartFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String startPage = request.getContextPath().concat("/view/index.xhtml");

       /* if (Arrays.asList(request.getCookies()).stream().anyMatch(cookie -> "phone".equals(cookie.getName()))) {
            response.sendRedirect(request.getContextPath() + "/view/library");
        }*/
       // response.sendRedirect(startPage);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
