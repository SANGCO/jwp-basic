package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import static org.junit.Assert.assertEquals;


public class UserDaoTest {

    /*
     * TODO Q. 이거 세팅해주기 전에는 어떻게 테스트가 돌아가는걸까?
     * Unique index or primary key violation 뜨는거 보니깐
     * 로컬에 떨군 jwp-basic.h2.db 파일에 접근하는거 같은데 어떻게 접근하는거지?
     */

    @Before
    public void setUp() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void crud() throws Exception {
        User expected = new User("userId", "password", "name", "javajigi@email.com");
        UserDao userDao = new UserDao();
        userDao.insert(expected);

        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);

    }

}
