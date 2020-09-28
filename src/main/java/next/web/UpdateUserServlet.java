package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UpdateUserServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = DataBase.findUserById(req.getParameter("userId"));

        if (user == null) {
            throw new IllegalStateException("수정을 요청한 고객에 대한 정보가 없습니다.");
        }
        User updateUser = new User(req.getParameter("userId"), req.getParameter("password"),
                                   req.getParameter("name"), req.getParameter("email"));
        logger.debug("Update User : {}", updateUser.toString());
        user.update(updateUser);
        resp.sendRedirect("/");
    }

}
