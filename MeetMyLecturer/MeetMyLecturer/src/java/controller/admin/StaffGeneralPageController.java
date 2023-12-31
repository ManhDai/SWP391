/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import DAO.AccountDAO;
import DAO.BookingDAO;
import DTO.AccountDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Trung Kien
 */
@WebServlet(name = "StaffGeneralPageController", urlPatterns = {"/StaffGeneralPageController"})
public class StaffGeneralPageController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String accountID = request.getParameter("accountID"); // dùng để tìm ra user profile người lần đầu
        AccountDAO aDao = new AccountDAO();
        BookingDAO bDao = new BookingDAO();
        String action = request.getParameter("action");
        int totalUserBookings = bDao.CountBookingByAccountID(accountID);
        request.setAttribute("totalUserBookings", totalUserBookings);
        AccountDTO a = (AccountDTO) session.getAttribute("acc"); // account này dùng để lấy ra người đang sử dụng cái trang này
        if (a == null) {
            response.sendRedirect("dashboard/login.jsp");
        } else {
            try {
                if (action == null || action.isEmpty()) {
                    //account này dùng để thể hiện ra user profile
                    AccountDTO b = aDao.GetAccountByAccountID(accountID);
                    session.setAttribute("account", b);
                }
                if (action.equalsIgnoreCase("Chỉnh Sửa")) {
                    AccountDTO c = (AccountDTO) session.getAttribute("account"); // account này dùng để lấy lại thông tin của user profile để cập nhật
                    String fullName = request.getParameter("fullName");
                    String address = request.getParameter("address");
                    String email = request.getParameter("email");
                    String phone = request.getParameter("phone");
                    String roleID_1 = request.getParameter("roleID");
                    String salaryString = request.getParameter("salary");
                    salaryString = salaryString.replaceAll("[^0-9]", "");
                    // Chuyển đổi thành số
                    int salary = Integer.parseInt(salaryString);
                    int roleID_2 = Integer.parseInt(roleID_1);
                    String password = c.getPassword();
                    String gender = c.getGender();
                    String dateOfBirth = c.getDateOfBirth();
                    String image = c.getImage();
                    String accountID_2 = c.getAccountID(); //account này dùng để lấy lại user profile đó để chỉnh sửa update lại
                    aDao.UpdateAccountProfile(email, password, fullName, address, phone, roleID_2, gender, dateOfBirth, image, salary, accountID_2);
                    AccountDTO updatedAccount = aDao.GetAccountByAccountID(accountID_2);
                    String message = "Chỉnh sửa thông tin thành công!";
                    request.setAttribute("account", updatedAccount);

                    request.setAttribute("message", message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                request.getRequestDispatcher("/dashboard/generalStaff.jsp").forward(request, response);
            }
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
