package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/updateForm")
public class UpdateUserFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new IllegalStateException("수정하려는 회원에 대한 정보가 없습니다.");
        }

        req.setAttribute("user", user);
        RequestDispatcher rd = req.getRequestDispatcher("/user/updateForm.jsp");
        rd.forward(req, resp);
    }

}
