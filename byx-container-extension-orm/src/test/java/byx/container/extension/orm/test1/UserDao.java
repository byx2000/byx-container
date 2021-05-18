package byx.container.extension.orm.test1;

import byx.container.extension.orm.annotation.Dao;
import byx.orm.annotation.Query;
import byx.orm.annotation.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> listAll();

    @Query("SELECT * FROM user WHERE uid = #{id}")
    User getById(Integer id);

    @Query("SELECT COUNT(0) FROM user")
    int count();

    @Update("INSERT INTO user(username, password) VALUES(#{user.username}, #{user.password})")
    int insert(User user);

    @Update("DELETE FROM user WHERE username = #{username} AND password = #{password}")
    void delete(String username, String password);
}
