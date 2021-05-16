package byx.ioc.core;

import byx.ioc.exception.IdNotFoundException;
import byx.ioc.exception.MultiTypeMatchException;
import byx.ioc.exception.TypeNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractContainerTest {
    @Test
    public void test1() {
        Container container = new AbstractContainer() {
            {
                registerObject("f1", new ObjectDefinition() {
                    @Override
                    public Class<?> getType() {
                        return String.class;
                    }

                    @Override
                    public Object getInstance(Object[] params) {
                        return "hello";
                    }
                });
                registerObject("f2", new ObjectDefinition() {
                    @Override
                    public Class<?> getType() {
                        return Integer.class;
                    }

                    @Override
                    public Object getInstance(Object[] params) {
                        return 123;
                    }
                });
                registerObject("f3", new ObjectDefinition() {
                    @Override
                    public Class<?> getType() {
                        return Double.class;
                    }

                    @Override
                    public Object getInstance(Object[] params) {
                        return 3.14;
                    }
                });
                registerObject("f4", new ObjectDefinition() {
                    @Override
                    public Class<?> getType() {
                        return Double.class;
                    }

                    @Override
                    public Object getInstance(Object[] params) {
                        return 6.28;
                    }
                });
            }
        };

        String s = container.getObject(String.class);
        assertEquals("hello", s);

        s = container.getObject("f1", String.class);
        assertEquals("hello", s);

        Integer i = container.getObject(Integer.class);
        assertEquals(123, i);

        i = container.getObject("f2", Integer.class);
        assertEquals(123, i);

        String s2 = container.getObject("f1");
        assertSame(s, s2);

        Integer i2 = container.getObject("f2");
        assertSame(i, i2);

        assertThrows(IdNotFoundException.class, () -> container.getObject("f5"));
        assertThrows(TypeNotFoundException.class, () -> container.getObject(Boolean.class));
        assertThrows(MultiTypeMatchException.class, () -> container.getObject(Double.class));
        assertThrows(IdNotFoundException.class, () -> container.getObject("aaa", String.class));
        assertThrows(TypeNotFoundException.class, () -> container.getObject("f1", Integer.class));
    }

    @Test
    public void test2() {
        Container container = new AbstractContainer() {
            {
                registerObject("f", new ObjectDefinition() {
                    @Override
                    public Class<?> getType() {
                        return String.class;
                    }

                    @Override
                    public Object getInstance(Object[] params) {
                        return "hello";
                    }
                });
            }
        };

        CharSequence s = container.getObject(CharSequence.class);
        assertEquals("hello", s);
    }

    @Test
    public void test3() {
        Container container = new AbstractContainer() {
            {
                registerObject("a", new ObjectDefinition() {
                    @Override
                    public Dependency[] getInstanceDependencies() {
                        return new Dependency[]{Dependency.type(String.class)};
                    }

                    @Override
                    public Class<?> getType() {
                        return Integer.class;
                    }

                    @Override
                    public Object getInstance(Object[] params) {
                        return 123;
                    }
                });
            }
        };

        assertThrows(TypeNotFoundException.class, () -> container.getObject("a"));
    }

    @Test
    public void test4() {
        Container container = new AbstractContainer() {
            {
                registerObject("a", new ObjectDefinition() {
                    @Override
                    public Dependency[] getInstanceDependencies() {
                        return new Dependency[]{Dependency.id("x")};
                    }

                    @Override
                    public Class<?> getType() {
                        return Integer.class;
                    }

                    @Override
                    public Object getInstance(Object[] params) {
                        return 123;
                    }
                });
            }
        };

        assertThrows(IdNotFoundException.class, () -> container.getObject("a"));
    }

    @Test
    public void test5() {
        Container container = new AbstractContainer() {
            {
                registerObject("a", new ObjectDefinition() {
                    @Override
                    public Class<?> getType() {
                        return Integer.class;
                    }

                    @Override
                    public Object getInstance(Object[] params) {
                        return 123;
                    }
                });
                registerObject("b", new ObjectDefinition() {
                    @Override
                    public Class<?> getType() {
                        return String.class;
                    }

                    @Override
                    public Object getInstance(Object[] params) {
                        return "abc";
                    }
                });
                registerObject("c", new ObjectDefinition() {
                    @Override
                    public Class<?> getType() {
                        return String.class;
                    }

                    @Override
                    public Object getInstance(Object[] params) {
                        return "def";
                    }
                });
            }
        };

        Set<String> s1 = container.getObjects(String.class);
        assertEquals(Set.of("abc", "def"), s1);

        Set<Integer> s2 = container.getObjects(Integer.class);
        assertEquals(Set.of(123), s2);

        Set<Double> s3 = container.getObjects(Double.class);
        assertTrue(s3.isEmpty());
    }

    @Test
    public void test6() {
        Container container = new AbstractContainer() {
            {
                registerObject("a", new ObjectDefinition() {
                    @Override
                    public Class<?> getType() {
                        return Integer.class;
                    }

                    @Override
                    public Object getInstance(Object[] params) {
                        return 123;
                    }
                });
                registerObject("b", new ObjectDefinition() {
                    @Override
                    public Class<?> getType() {
                        return Integer.class;
                    }

                    @Override
                    public Object getInstance(Object[] params) {
                        return 456;
                    }
                });
                registerObject("c", new ObjectDefinition() {
                    @Override
                    public Class<?> getType() {
                        return String.class;
                    }

                    @Override
                    public Object getInstance(Object[] params) {
                        return "hello";
                    }
                });
            }
        };

        Set<Class<?>> types = container.getObjectTypes();
        assertEquals(Set.of(Integer.class, String.class), types);
    }
}
