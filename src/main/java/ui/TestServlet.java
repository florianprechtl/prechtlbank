package ui;

import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/testpanel"})
public class TestServlet extends HttpServlet {

    @Inject
    private Logger logger;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();

        out.println("<html>");
        out.println("<head/>");
        out.println("<body>");
        out.println("<h1>PrechtlBank seins erstem Servlet</h1>");
        out.println("</body>");
        out.println("</html>");

        logger.info("test :: test");
        System.out.println("Hallo Welt");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }




}
