package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static next.web.UserSessionUtils.isLogined;
import static next.web.UserSessionUtils.isSameUser;

@WebServlet("/user/update")
public class UpdateUserController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UpdateUserController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);

        if (!isSameUser(session, user)) {
            throw new IllegalStateException("다른 회원의 정볼르 수정할 수 없습니다.");
        }
        req.setAttribute("user", user);
        RequestDispatcher rd = req.getRequestDispatcher("/user/updateForm.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);

        if (!isSameUser(session, user)) {
            throw new IllegalStateException("다른 회원의 정볼르 수정할 수 없습니다.");
        }
        User updateUser = new User(req.getParameter("userId"), req.getParameter("password"),
                                   req.getParameter("name"), req.getParameter("email"));
        logger.debug("Update User : {}", updateUser.toString());
        user.update(updateUser);
        resp.sendRedirect("/");
    }

}
