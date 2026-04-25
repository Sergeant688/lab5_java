package lab5_java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс для тестирования {@link Injector}.
 */
class InjectorTest 
{

    private Injector injector;

    /**
     * Метод, который автоматически выполняется перед каждым тестом.
     * Инициализирует объект Injector с тестовым файлом настроек.
     */
    @BeforeEach
    void setUp()
    {
        injector = new Injector("config.properties");
    }

    /**
     * Проверяет успешное внедрение зависимостей с помощью рефлексии.
     * Ожидается, что поля field1 и field2 класса {@link SomeBean}
     * не будут равны null и получат объекты правильных классов.
     *
     * @throws Exception если возникают ошибки доступа через рефлексию
     */
    @Test
    void testInjectionIsSuccessful() throws Exception
    {
        SomeBean bean = new SomeBean();
        injector.inject(bean);

        Field field1 = bean.getClass().getDeclaredField("field1");
        Field field2 = bean.getClass().getDeclaredField("field2");

        field1.setAccessible(true);
        field2.setAccessible(true);

        Object injectedField1 = field1.get(bean);
        Object injectedField2 = field2.get(bean);

        assertNotNull(injectedField1);
        assertNotNull(injectedField2);

        assertTrue(injectedField1 instanceof SomeImpl);
        assertTrue(injectedField2 instanceof SODoer);
    }

    /**
     * Проверяет поведение программы при передаче несуществующего файла настроек.
     * Ожидается выброс исключения {@link RuntimeException}.
     */
    @Test
    void testMissingPropertiesFile()
    {
        assertThrows(RuntimeException.class, () -> new Injector("missing.properties"));
    }
}