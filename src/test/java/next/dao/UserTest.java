package next.dao;

import core.db.DataBase;
import next.model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void user_update_test() {
        User user = DataBase.findUserById("SANGCO");
        user.update(new User(user.getUserId(), "password123", "수정 상코", "sangco123@gmail.com"));
        User updateUser = DataBase.findUserById("SANGCO");
        assertEquals("수정 상코", updateUser.getName());
    }

}
