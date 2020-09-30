package next.web;

import core.db.DataBase;
import next.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static next.dao.UserTest.DEFAUlTUSER;
import static next.dao.UserTest.UPDATEUSER;
import static org.junit.Assert.*;


public class ControllerTest {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void listUserController_forwardedUrl_fail_test() throws ServletException, IOException {
        // Given
        ListUserController listUserController = new ListUserController();

        // When
        listUserController.doGet(request, response);

        // Then
        assertEquals("/user/loginForm", response.getRedirectedUrl());
    }

    @Test
    public void listUserController_forwardedUrl_test() throws ServletException, IOException {
        // Given
        login(request);
        ListUserController listUserController = new ListUserController();

        // When
        listUserController.doGet(request, response);

        // Then
        assertEquals("/user/list.jsp", response.getForwardedUrl());
    }

    @Test
    public void createUserController_redirect_test() throws ServletException, IOException {
        // Given
        CreateUserController createUserController = new CreateUserController();

        // When
        createUserController.doPost(request, response);

        // Then
        assertEquals("/user/list", response.getRedirectedUrl());
    }

    @Test
    public void updateUserFormController_forwardedUrl_test() throws ServletException, IOException {
        // Given
        request.setParameter("userId", DEFAUlTUSER.getUserId());
        UpdateUserController updateUserController = new UpdateUserController();

        // When
        updateUserController.doGet(request, response);

        // Then
        assertEquals("/user/updateForm.jsp", response.getForwardedUrl());
    }

    @Test
    public void updateUserFormController_findUser_test() throws ServletException, IOException {
        // Given
        request.setParameter("userId", DEFAUlTUSER.getUserId());
        UpdateUserController updateUserController = new UpdateUserController();

        // When
        updateUserController.doGet(request, response);
        User user = (User) request.getAttribute("user");

        // Then
        assertEquals(DEFAUlTUSER.getUserId(), user.getUserId());
    }

    @Test(expected = IllegalStateException.class)
    public void updateUserFormController_findUser_Exception_test() throws ServletException, IOException {
        // Given
        request.setParameter("userId", "failTestUserId");

        // When
        UpdateUserController updateUserController = new UpdateUserController();

        // Then
        updateUserController.doGet(request, response);
    }

    @Test
    public void updateUserController_updateUser_test() throws ServletException, IOException {
        // TODO 로그인 확인
        // Given
        setParameter(UPDATEUSER);
        request.setParameter("name", UPDATEUSER.getName());
        request.setParameter("email", UPDATEUSER.getEmail());
        UpdateUserController updateUserController = new UpdateUserController();

        // When
        updateUserController.doPost(request, response);
        User user = DataBase.findUserById(UPDATEUSER.getUserId());

        // Then
        assertEquals(UPDATEUSER.getPassword(), user.getPassword());
        assertEquals("/", response.getRedirectedUrl());

        // Rollback
        user.update(DEFAUlTUSER);
    }

    @Test
    public void LoginController_view_test() throws ServletException, IOException {
        // Given
        LoginController LoginController = new LoginController();

        // When
        LoginController.doGet(request, response);

        // Then
        assertEquals("/user/login.jsp", response.getForwardedUrl());
    }

    @Test
    public void LoginController_login_test() throws ServletException, IOException {
        // Given
        setParameter(DEFAUlTUSER);
        LoginController LoginController = new LoginController();

        // When
        LoginController.doPost(request, response);
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");

        // Then
        assertNotNull(sessionUser);
        assertEquals("/", response.getRedirectedUrl());
    }

    @Test
    public void LoginController_fail_password_test() throws ServletException, IOException {
        // Given
        setParameter(UPDATEUSER);
        LoginController LoginController = new LoginController();

        // When
        LoginController.doPost(request, response);
        HttpSession session = request.getSession();
        Boolean loginFailed = (Boolean) request.getAttribute("loginFailed");

        // Then
        assertTrue(loginFailed);
        assertNull(session.getAttribute("user"));
        assertEquals("/user/login.jsp", response.getForwardedUrl());
    }

    @Test
    public void LoginController_fail_id_notFound_test() throws ServletException, IOException {
        // Given
        setParameter(UPDATEUSER);
        LoginController LoginController = new LoginController();

        // When
        LoginController.doPost(request, response);
        HttpSession session = request.getSession();
        Boolean loginFailed = (Boolean) request.getAttribute("loginFailed");

        // Then
        assertTrue(loginFailed);
        assertNull(session.getAttribute("user"));
        assertEquals("/user/login.jsp", response.getForwardedUrl());
    }

    @Test
    public void logoutController_test() throws ServletException, IOException {
        // TODO 로그인 검사

        // Given
        login(request);
        LogoutController logoutController = new LogoutController();

        // When
        logoutController.doGet(request, response);
        HttpSession logoutSession = request.getSession();

        // Then
        assertNull(logoutSession.getAttribute("user"));
    }

    private void setParameter(User user) {
        request.setParameter("userId", user.getUserId());
        request.setParameter("password", user.getPassword());
    }

    public static void login(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("user", DEFAUlTUSER);
    }

}
