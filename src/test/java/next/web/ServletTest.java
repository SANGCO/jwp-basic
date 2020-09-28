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
import java.io.IOException;

import static org.junit.Assert.assertEquals;


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
        request.setParameter("userId", "SANGCO");
        UpdateUserFormServlet updateUserFormServlet = new UpdateUserFormServlet();
        updateUserFormServlet.doGet(request, response);
        assertEquals("/user/updateForm.jsp", response.getForwardedUrl());
    }

    @Test
    public void updateUserFormServlet_findUser_test() throws ServletException, IOException {
        request.setParameter("userId", "SANGCO");
        UpdateUserFormServlet updateUserFormServlet = new UpdateUserFormServlet();
        updateUserFormServlet.doGet(request, response);
        User user = (User) request.getAttribute("user");
        assertEquals("SANGCO", user.getUserId());
    }

    @Test(expected = IllegalStateException.class)
    public void updateUserFormServlet_findUser_Exception_test() throws ServletException, IOException {
        request.setParameter("userId", "김아무개");
        UpdateUserFormServlet updateUserFormServlet = new UpdateUserFormServlet();
        updateUserFormServlet.doGet(request, response);
    }

    @Test
    public void updateUserServlet_updateUser_test() throws ServletException, IOException {
        request.setParameter("userId", "SANGCO");
        request.setParameter("password", "password123");
        request.setParameter("name", "수정한 상코");
        request.setParameter("email", "sangco123@gmail.com");
        UpdateUserServlet updateUserServlet = new UpdateUserServlet();
        updateUserServlet.doPost(request, response);
        assertEquals("/", response.getRedirectedUrl());
    }

}
