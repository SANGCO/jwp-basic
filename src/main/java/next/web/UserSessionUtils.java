package next.web;

import next.model.User;

import javax.servlet.http.HttpSession;

public class UserSessionUtils {

    public static boolean isLogined(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null;
    }

    public static boolean isSameUser(HttpSession session, User user) {
        if (!isLogined(session)) {
            return false;
        }

        if (user == null) {
            return false;
        }

        User sessionUser = (User) session.getAttribute("user");
        return sessionUser.isSameUser(user);
    }

}
