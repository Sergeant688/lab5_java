package lab5_java;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Пользовательская аннотация.
 * <p>
 * Поля, помеченные данной аннотацией, автоматически инициализируются объектами
 * соответствующих классов при обработке классом {@link Injector}.
 * Аннотация доступна во время выполнения программы и применяется только к полям.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoInjectable 
{
}
