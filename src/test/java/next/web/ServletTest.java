package next.web;

import org.junit.Before;
import org.junit.Test;
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
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    /*
     * TODO 실제 리턴되는 뷰를 테슽 할 수 없을까나
     */
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



}
