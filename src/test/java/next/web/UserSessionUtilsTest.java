package next.web;

import next.dao.UserTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpSession;

import static next.dao.UserTest.*;
import static next.web.ControllerTest.login;
import static next.web.UserSessionUtils.isSameUser;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void isLogined_test() {
        // Given
        login(request);
        HttpSession session = request.getSession();

        // When
        boolean logined = UserSessionUtils.isLogined(session);

        // Then
        assertTrue(logined);
    }

    @Test
    public void isSameUser_login_failTest() {
        // Given
        HttpSession session = request.getSession();

        // When
        boolean sameUser = isSameUser(session, DEFAUlTUSER);

        //Then
        assertFalse(sameUser);
    }

    @Test
    public void isSameUser_null_failTest() {
        // Given
        login(request);
        HttpSession session = request.getSession();

        // When
        boolean sameUser = isSameUser(session, null);

        //Then
        assertFalse(sameUser);
    }

    @Test
    public void isSameUser_sameUser_failTest() {
        // Given
        login(request);
        HttpSession session = request.getSession();

        // When
        boolean sameUser = isSameUser(session, ANOTHERUSER);

        //Then
        assertFalse(sameUser);
    }

    @Test
    public void isSameUser_test() {
        // Given
        login(request);
        HttpSession session = request.getSession();

        // When
        boolean sameUser = isSameUser(session, DEFAUlTUSER);

        //Then
        assertTrue(sameUser);
    }

}
