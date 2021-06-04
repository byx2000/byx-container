package byx.ioc.core.container;

import byx.ioc.core.SimpleContainer;
import byx.ioc.exception.IdNotFoundException;
import byx.ioc.exception.MultiTypeMatchException;
import byx.ioc.exception.ObjectNotFoundException;
import byx.ioc.exception.TypeNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleContainerTest {
    private static class ObjectDefinition {
        private final Object val;

        private ObjectDefinition(Object val) {
            this.val = val;
        }

        public Object create() {
            return val;
        }

        public Class<?> getType() {
            return val.getClass();
        }
    }

    private static class MySimpleContainer extends SimpleContainer<ObjectDefinition> {
        @Override
        protected Object doCreate(ObjectDefinition definition) {
            return definition.create();
        }

        @Override
        protected Class<?> doGetType(ObjectDefinition definition) {
            return definition.getType();
        }
    }

    @Test
    public void test1() {
        MySimpleContainer container = new MySimpleContainer();
        container.registerObject("v1", new ObjectDefinition(123));
        container.registerObject("v2", new ObjectDefinition(456));

        assertEquals(123, (int) container.getObject("v1"));
        assertEquals(456, (int) container.getObject("v2"));
        assertThrows(IdNotFoundException.class, () -> container.getObject("xxx"));
        assertEquals(Set.of("v1", "v2"), container.getObjectIds());
    }

    @Test
    public void test2() {
        MySimpleContainer container = new MySimpleContainer();
        container.registerObject("v1", new ObjectDefinition(123));
        container.registerObject("v2", new ObjectDefinition(456));
        container.registerObject("v3", new ObjectDefinition("hello"));

        assertEquals("hello", container.getObject(String.class));
        assertThrows(TypeNotFoundException.class, () -> container.getObject(Double.class));
        assertThrows(MultiTypeMatchException.class, () -> container.getObject(Integer.class));
        assertEquals(Set.of(Integer.class, String.class), container.getObjectTypes());
    }

    @Test
    public void test3() {
        MySimpleContainer container = new MySimpleContainer();
        container.registerObject("v1", new ObjectDefinition(123));
        container.registerObject("v2", new ObjectDefinition("hello"));

        assertEquals(123, container.getObject("v1", Integer.class));
        assertEquals("hello", container.getObject("v2", String.class));
        assertThrows(ObjectNotFoundException.class, () -> container.getObject("v1", String.class));
        assertThrows(ObjectNotFoundException.class, () -> container.getObject("v2", Integer.class));
        assertThrows(ObjectNotFoundException.class, () -> container.getObject("xxx", Integer.class));
    }

    @Test
    public void test4() {
        MySimpleContainer container = new MySimpleContainer();
        container.registerObject("v1", new ObjectDefinition(123));
        container.registerObject("v2", new ObjectDefinition("hello"));

        assertEquals(Integer.class, container.getType("v1"));
        assertEquals(String.class, container.getType("v2"));
        assertThrows(IdNotFoundException.class, () -> container.getType("xxx"));
    }
}
