package next.web;

import next.dao.UserTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpSession;

import static next.dao.UserTest.DEFAUlTUSER;
import static next.web.ControllerTest.login;

public class UserSessionUtilsTest {

    // TODO 사윙 테스트 객체 만들
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void isLogined_fail_test() {
        // Given
        HttpSession session = request.getSession();

        // When
        boolean logined = UserSessionUtils.isLogined(session);

        // Then
        Assert.assertFalse(logined);
    }

}
