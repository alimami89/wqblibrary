/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Author;
import entity.Book;
import entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.AuthorFacade;
import session.BookFacade;
import session.UserRolesFacade;

/**
 *
 * @author user
 */
@WebServlet(name = "MyServlet",loadOnStartup = 1, urlPatterns = {
    "/addBook",
    "/createBook",
    "/listBooks",
})
public class BookServlet extends HttpServlet {
    @EJB private AuthorFacade authorFacade;
    @EJB private BookFacade bookFacade;
    @EJB private UserRolesFacade userRolesFacade;
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
        String path = request.getServletPath();
        switch (path) {
            case "/addBook":
                HttpSession session = request.getSession(false);
                if(session == null){
                    request.setAttribute("info", "Авторизуйтесь!");
                    request.getRequestDispatcher("/showLogin").forward(request, response);
                }
                User authUser = (User) session.getAttribute("authUser");
                if(authUser == null){
                    request.setAttribute("info", "Авторизуйтесь!");
                    request.getRequestDispatcher("/showLogin").forward(request, response);
                }
                if(!userRolesFacade.isRole("MANAGER",authUser)){
                    request.setAttribute("info", "У вас нет прав!");
                    request.getRequestDispatcher("/showLogin").forward(request, response);
                }
                request.setAttribute("activeAddBook", true);
                List<Author> authors = authorFacade.findAll();
                request.setAttribute("authors", authors);
                request.getRequestDispatcher("/WEB-INF/addBook.jsp").forward(request, response);
                break;
            case "/createBook":
                String bookName = request.getParameter("bookName");
                String[] authorsId = request.getParameterValues("authorsId");
                String releaseYear = request.getParameter("releaseYear");
                String quantity = request.getParameter("quantity");
                if("".equals(bookName) || "".equals(releaseYear) || "".equals(quantity)){
                    request.setAttribute("bookName", bookName);
                    request.setAttribute("releaseYear", releaseYear);
                    request.setAttribute("quantity", quantity);
                    request.setAttribute("info", "Заполните все поля");
                    request.getRequestDispatcher("/addBook").forward(request, response);
                    break;
                }
                if(authorsId == null || authorsId.length == 0){
                    request.setAttribute("bookName", bookName);
                    request.setAttribute("releaseYear", releaseYear);
                    request.setAttribute("quantity", quantity);
                    request.setAttribute("info", "Выберите авторов");
                    request.getRequestDispatcher("/addBook").forward(request, response);
                    break;
                }
                Book book = new Book();
                book.setBookName(bookName);
                List<Author> listAuthors = new ArrayList<>();
                for (int i = 0; i < authorsId.length; i++) {
                    listAuthors.add(authorFacade.find(Long.parseLong(authorsId[i])));
                }
                book.setAuthors(listAuthors);
                try {
                    book.setReleaseYear(Integer.parseInt(releaseYear));
                    book.setQuantity(Integer.parseInt(quantity));
                } catch (Exception e) {
                    request.setAttribute("bookName", bookName);
                    request.setAttribute("releaseYear", releaseYear);
                    request.setAttribute("quantity", quantity);
                    request.setAttribute("info", "Год публикации и количество вводите цифры");
                    request.getRequestDispatcher("/addBook").forward(request, response);
                    break;
                }
                book.setCount(book.getQuantity());
                bookFacade.create(book);
                request.setAttribute("info", "Книга добавлена");
                request.getRequestDispatcher("/listBooks").forward(request, response);
                break;
            case "/listBooks":
                List<Book> books = bookFacade.findAll();
                request.setAttribute("books", books);
                request.getRequestDispatcher("/listBooks.jsp").forward(request, response);
                break;
            
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
