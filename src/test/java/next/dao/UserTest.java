package next.dao;

import core.db.DataBase;
import next.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    public static final User DEFAUlTUSER = new User("sangco", "password", "상코", "sangco@gmail.com");
    public static final User UPDATEUSER = new User("sangco", "password123", "수정한 상코", "sangco123@gmail.com");

    @Test
    public void update_test() {
        // Given
        User user = getUserById(DEFAUlTUSER.getUserId());

        // When
        user.update(UPDATEUSER);
        User updateUser = getUserById(UPDATEUSER.getUserId());

        // Then
        assertEquals(UPDATEUSER.getName(), updateUser.getName());

        // Rollback
        updateUser.update(DEFAUlTUSER);
    }

    @Test
    public void matchPassword_test() {
        // Given
        User user = getUserById(DEFAUlTUSER.getUserId());

        // When
        boolean isMatched = user.matchPassword(user.getPassword());

        // Then
        assertTrue(isMatched);
    }

    @Test
    public void matchPassword_null_test() {
        // Given
        User user = getUserById(DEFAUlTUSER.getUserId());

        // When
        boolean isMatched = user.matchPassword(null);

        // Then
        assertFalse(isMatched);
    }

    @Test
    public void matchPassword_wrongPassword_test() {
        // Given
        User user = getUserById(DEFAUlTUSER.getUserId());

        // When
        boolean isMatched = user.matchPassword(UPDATEUSER.getPassword());

        // Then
        assertFalse(isMatched);
    }

    private User getUserById(String userId) {
        return DataBase.findUserById(userId);
    }

}
