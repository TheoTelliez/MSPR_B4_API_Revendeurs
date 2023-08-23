package xyz.msprpayetonkawa.apirevendeur.tools;

import org.mockito.Mockito;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.util.ReflectionTestUtils;

@SuppressWarnings("unchecked")
public class SpringBeanMockUtil {
    private SpringBeanMockUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static <T> T unwrapProxy(T bean) {
        try {
            if (AopUtils.isAopProxy(bean) && bean instanceof Advised){
                Advised advised = (Advised) bean;
                bean = (T) advised.getTargetSource().getTarget();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bean;
    }

    public static <T> T mockFieldOnBean(Object beanToInjectMock, Class<T> classToMock){
        T mocked = Mockito.mock(classToMock);
        ReflectionTestUtils.setField(unwrapProxy(beanToInjectMock), null, mocked, classToMock);
        return mocked;
    }
}
