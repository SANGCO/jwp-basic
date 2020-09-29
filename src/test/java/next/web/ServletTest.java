package next.web;

import core.db.DataBase;
import core.jdbc.ConnectionManager;
import next.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.Assert.*;


public class ServletTest {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUp() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void listUserServlet_forwardedUrl_test() throws ServletException, IOException {
        ListUserServlet listUserServlet = new ListUserServlet();
        listUserServlet.doGet(request, response);

        assertEquals("/user/list.jsp", response.getForwardedUrl());
    }

    @Test
    public void createUserServlet_redirect_test() throws ServletException, IOException {
        CreateUserServlet createUserServlet = new CreateUserServlet();
        createUserServlet.doPost(request, response);

        assertEquals("/user/list", response.getRedirectedUrl());
    }

    @Test
    public void updateUserFormServlet_forwardedUrl_test() throws ServletException, IOException {
        request.setParameter("userId", "sangco");
        UpdateUserServlet updateUserServlet = new UpdateUserServlet();
        updateUserServlet.doGet(request, response);

        assertEquals("/user/updateForm.jsp", response.getForwardedUrl());
    }

    @Test
    public void updateUserFormServlet_findUser_test() throws ServletException, IOException {
        request.setParameter("userId", "sangco");
        UpdateUserServlet updateUserServlet = new UpdateUserServlet();
        updateUserServlet.doGet(request, response);
        User user = (User) request.getAttribute("user");

        assertEquals("sangco", user.getUserId());
    }

    @Test(expected = IllegalStateException.class)
    public void updateUserFormServlet_findUser_Exception_test() throws ServletException, IOException {
        request.setParameter("userId", "김아무개");
        UpdateUserServlet updateUserServlet = new UpdateUserServlet();
        updateUserServlet.doGet(request, response);
    }

    @Test
    public void updateUserServlet_updateUser_test() throws ServletException, IOException {
        // TODO 지저분한데 리팩토링 가나?
        User updateUser = new User("sangco", "password123",
                "수정한 상코", "sangco123@gmail.com");
        request.setParameter("userId", updateUser.getUserId());
        request.setParameter("password", updateUser.getPassword());
        request.setParameter("name", updateUser.getName());
        request.setParameter("email", updateUser.getEmail());
        UpdateUserServlet updateUserServlet = new UpdateUserServlet();
        updateUserServlet.doPost(request, response);
        User user = DataBase.findUserById(updateUser.getUserId());

        assertEquals(updateUser.getPassword(), user.getPassword());
        assertEquals("/", response.getRedirectedUrl());

        updateUser = new User("sangco", "password",
                "수정한 상코", "sangco123@gmail.com");
        user.update(updateUser);
    }

    @Test
    public void LoginServlet_view_test() throws ServletException, IOException {
        LoginServlet LoginServlet = new LoginServlet();
        LoginServlet.doGet(request, response);

        assertEquals("/user/login.jsp", response.getForwardedUrl());
    }

    @Test
    public void LoginServlet_login_test() throws ServletException, IOException {
        request.setParameter("userId", "sangco");
        request.setParameter("password", "password");
        LoginServlet LoginServlet = new LoginServlet();
        LoginServlet.doPost(request, response);
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");

        assertNotNull(sessionUser);
        assertEquals("/", response.getRedirectedUrl());
    }

    @Test
    public void LoginServlet_fail_password_test() throws ServletException, IOException {
        request.setParameter("userId", "sangco");
        request.setParameter("password", "password1234");
        LoginServlet LoginServlet = new LoginServlet();
        LoginServlet.doPost(request, response);
        HttpSession session = request.getSession();
        Boolean loginFailed = (Boolean) request.getAttribute("loginFailed");

        assertTrue(loginFailed);
        assertNull(session.getAttribute("user"));
        assertEquals("/user/login.jsp", response.getForwardedUrl());
    }

    @Test
    public void LoginServlet_fail_id_notFound_test() throws ServletException, IOException {
        request.setParameter("userId", "sangco");
        request.setParameter("password", "password1234");
        LoginServlet LoginServlet = new LoginServlet();
        LoginServlet.doPost(request, response);
        HttpSession session = request.getSession();
        Boolean loginFailed = (Boolean) request.getAttribute("loginFailed");

        assertTrue(loginFailed);
        assertNull(session.getAttribute("user"));
        assertEquals("/user/login.jsp", response.getForwardedUrl());
    }

    @Test
    public void logoutServlet_test() throws ServletException, IOException {
        User user = new User("sangco", "password123",
                "수정한 상코", "sangco123@gmail.com");
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        LogoutServlet logoutServlet = new LogoutServlet();
        logoutServlet.doGet(request, response);
        HttpSession logoutSession = request.getSession();

        assertNull(logoutSession.getAttribute("user"));
    }

}
