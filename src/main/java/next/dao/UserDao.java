package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {

        try (
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO USERS VALUES (?, ?, ?, ?)")
        ) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());

            pstmt.executeUpdate();
        }
    }

    public User findByUserId(String userId) throws SQLException {

        try (
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(
                    "SELECT userId, password, name, email FROM USERS WHERE userid=?");
            ResultSet rs = pstmt.executeQuery();
        ) {
            pstmt.setString(1, userId);
            User user = null;
            if (rs.next()) {
                user = new User(
                        rs.getString("userId"), rs.getString("password"),
                        rs.getString("name"), rs.getString("email"));
            }

            return user;
        }
    }
}
