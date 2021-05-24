package byx.ioc.extension.transaction.test2;

import byx.ioc.annotation.annotation.Component;
import byx.transaction.annotation.Transactional;

@Component
public interface MyService {
    @Transactional
    void insert(Integer id, String username, String password);
}
