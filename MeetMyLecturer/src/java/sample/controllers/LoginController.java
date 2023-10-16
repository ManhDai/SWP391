/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sample.user.UserDAO;
import sample.user.UserDTO;

/**
 *
 * @author Manh
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String AD = "AD";
    private static final String AD_PAGE = "admin.jsp";
    private static final String US = "US";
    private static final String US_PAGE = "user.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {

            String userResponse = request.getParameter("g-recaptcha-response");
            if (verifyRecaptcha(userResponse)) {
                String userID = request.getParameter("userID");
                String password = request.getParameter("password");
                UserDAO dao = new UserDAO();
                UserDTO loginUser = dao.checkLogin(userID, password);
                if (loginUser == null) {
                    url = ERROR;
                    request.setAttribute("ERROR", "Incorrect UserID or Password");
                } else {
                    //phan quyen o day
                    String roleID = loginUser.getRoleID();
                    HttpSession session = request.getSession();
                    session.setAttribute("LOGIN_USER", loginUser);
                    if (AD.equals(roleID)) {
                        url = AD_PAGE;
                    } else if (US.equals(roleID)) {
                        url = US_PAGE;
                    } else {
                        url = ERROR;
                        request.setAttribute("ERROR", "Your role is not support !");
                    }
                }
            } else {
                url = ERROR;
                request.setAttribute("ERROR", "reCAPTCHA verification failed.");
            } 

        } catch (Exception e) {
            log("Error at LoginController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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

    private boolean verifyRecaptcha(String userResponse) {
        try {
            String secret = "6LfDcscnAAAAAD9mjoc163oPUiH4-ZEiaDOy1ZoM"; // Thay YOUR_SECRET_KEY bằng Secret Key bạn đã nhận được

            // Tạo URL để gửi yêu cầu xác minh reCAPTCHA đến Google
            String url = "https://www.google.com/recaptcha/api/siteverify"
                    + "?secret=" + secret
                    + "&response=" + userResponse;

            // Gửi yêu cầu POST đến Google và kiểm tra kết quả
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");

            // Đọc phản hồi từ Google
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Sử dụng JSON để phân tích phản hồi từ Google
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());
            // Kiểm tra xem reCAPTCHA có hợp lệ không
            Boolean success = (Boolean) jsonResponse.get("success");
            return success != null && success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
