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
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/editpatient.html")
public class EditPatientServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String column = request.getParameter("columnToEdit");
        int id = Integer.parseInt(request.getParameter("editId"));
        String newValue = request.getParameter("newValue");

        Model model = ModelFactory.getModel();
        try {
            model.editPatientAndUpdateModel(id, column, newValue);
            request.setAttribute("id", id);
        } catch (DateTimeParseException e){
            // check valid input
            request.setAttribute("id", id);
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/editingErrorPage.jsp");
            dispatch.forward(request, response);
        }

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/redirectToPatient.jsp");
        dispatch.forward(request, response);
    }

}

