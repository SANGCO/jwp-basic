package next.dao;

import core.db.DataBase;
import next.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void update_test() {
        User user = DataBase.findUserById("SANGCO");
        user.update(new User(user.getUserId(), "password123", "수정 상코", "sangco123@gmail.com"));
        User updateUser = DataBase.findUserById("SANGCO");
        assertEquals("수정 상코", updateUser.getName());
    }

    @Test
    public void matchPassword_test() {
        User user = DataBase.findUserById("SANGCO");
        assertTrue(user.matchPassword(user.getPassword()));
    }

    @Test
    public void matchPassword_null_test() {
        User user = DataBase.findUserById("SANGCO");
        assertFalse(user.matchPassword(null));
    }

    @Test
    public void matchPassword_wrongPassword_test() {
        User user = DataBase.findUserById("SANGCO");
        assertFalse(user.matchPassword("password123"));
    }

}
