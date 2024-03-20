package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/addPatient.html")
public class AddNewPatientServlet extends HttpServlet {
    HttpServletRequest request;
    HttpServletResponse response;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.request = request;
        this.response = response;

        Model model = ModelFactory.getModel();
        HashMap<String, String> infoOFPatient = new HashMap<>();
        List<String> columns = model.getColumnNames();
        for(String column: columns){
            infoOFPatient.put(column,request.getParameter(column));
        }

        try {
            model.addPatientAndUpdateModel(infoOFPatient);
        } catch (DateTimeParseException e){
            // Handle wrong format date input
           forwardTo("/errprpage.html");
        } catch (InvalidParameterException e){
           forwardTo("/addErrorPage.html");
        }
        forwardTo("/redirectToMenu.jsp");
    }

    private void forwardTo(String htmlPath) throws ServletException, IOException {
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(htmlPath);
        dispatch.forward(request, response);
    }
}
