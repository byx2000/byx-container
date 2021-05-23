package byx.container.extension.orm.test1;

import byx.container.extension.orm.annotation.Dao;
import byx.orm.annotation.Query;

import java.util.List;

@Dao
public interface BookDao {
    @Query("SELECT * FROM book")
    List<Book> listAll();
}
