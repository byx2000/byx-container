package byx.container.extension.jdbc;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import byx.util.jdbc.JdbcUtils;
import byx.util.jdbc.core.MapRowMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcExtensionTest {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(JdbcExtensionTest.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        JdbcUtils jdbcUtils = container.getObject(JdbcUtils.class);
        assertNotNull(jdbcUtils);

        List<Map<String, Object>> result = jdbcUtils.queryList("SELECT * FROM tb", new MapRowMapper());
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).get("id"));
        assertEquals(123, result.get(0).get("value"));
        assertEquals(2, result.get(1).get("id"));
        assertEquals(456, result.get(1).get("value"));
        assertEquals(3, result.get(2).get("id"));
        assertEquals(789, result.get(2).get("value"));
    }
}
