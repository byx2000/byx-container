package byx.ioc.annotation.callback;

import byx.ioc.annotation.annotation.Value;
import byx.ioc.annotation.annotation.Values;
import byx.ioc.annotation.core.AnnotationConfigContainerCallback;
import byx.ioc.annotation.core.AnnotationConfigContainerContext;
import byx.ioc.annotation.core.ObjectDefinition;
import byx.ioc.annotation.core.ValueConverter;
import byx.ioc.annotation.exception.ValueConverterNotFoundException;
import byx.ioc.util.ClassPredicates;
import byx.ioc.annotation.util.PackageScanner;
import byx.ioc.util.ExtensionLoader;

import java.util.Arrays;
import java.util.List;

/**
 * 处理Value注解
 *
 * @author byx
 */
public class ValueProcessor implements AnnotationConfigContainerCallback {
    @Override
    public void afterAnnotationConfigContainerInit(AnnotationConfigContainerContext ctx) {
        PackageScanner packageScanner = ctx.getPackageScanner();
        packageScanner.getClasses(ClassPredicates.hasAnnotation(Value.class))
                .forEach(c -> processValue(c.getAnnotation(Value.class), ctx));
        packageScanner.getClasses(ClassPredicates.hasAnnotation(Values.class))
                .forEach(c -> Arrays.stream(c.getAnnotation(Values.class).value()).forEach(v -> processValue(v, ctx)));
    }

    private ValueConverter getValueConverter(Class<?> toType) {
        List<ValueConverter> converters = ExtensionLoader.getExtensions(
                ClassPredicates.isAssignableTo(ValueConverter.class),
                ExtensionLoader.createByDefaultConstructor());
        for (ValueConverter vc : converters) {
            if (vc.fromType() == String.class && vc.toType() == toType) {
                return vc;
            }
        }
        throw new ValueConverterNotFoundException(String.class, toType);
    }

    private void processValue(Value v, AnnotationConfigContainerContext ctx) {
        ValueConverter converter = getValueConverter(v.type());
        String id = "".equals(v.id()) ? v.value() : v.id();
        ctx.getObjectRegistry().registerObject(id, new ObjectDefinition() {
            @Override
            public Class<?> getType() {
                return v.type();
            }

            @Override
            public Object getInstance(Object[] dependencies) {
                return converter.convert(v.value());
            }
        });
    }
}
