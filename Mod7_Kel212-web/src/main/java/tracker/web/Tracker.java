// Tracker.java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package tracker.web;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import tracker.ejb.TrackerBean;
/**
 *
 * @author usser
 */
@WebServlet(name = "Tracker", urlPatterns = {"/Tracker"})
public class Tracker extends HttpServlet {
    TrackerBean trackerBean = new TrackerBean();
    List<Double> oddNumbers = new ArrayList<>();
    List<Double> evenNumbers = new ArrayList<>();
    
    // ...

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            double total;
            double average = 0;
            int count = 0;

            if (!request.getParameter("value").isEmpty()) {
                double value = Double.parseDouble(request.getParameter("value"));
                total = trackerBean.add(value);

                // Check whether the value is odd or even and add it to the respective array
                if (value % 2 == 0) {
                    evenNumbers.add(value);
                } else {
                    oddNumbers.add(value);
                }
            } else {
                total = trackerBean.getTotal();
            }

            if (trackerBean.getCount() != 0) {
                average = trackerBean.average();
                count = trackerBean.getCount();
            }

            out.println("Count: " + count + "<br />");
            out.println("Total: " + total + "<br />");
            out.println("Average: " + average + "<br />");

            out.println("Odd Numbers: " + oddNumbers.toString() + "<br />");
            out.println("Even Numbers: " + evenNumbers.toString() + "<br />");

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
            rd.include(request, response);
        } catch (IOException | NumberFormatException | ServletException ex) {
            PrintWriter out = response.getWriter();
            out.println("Error: Silahkan isi field dengan angka");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
            rd.include(request, response);
        } finally {
            PrintWriter out = response.getWriter();
            out.close();
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}