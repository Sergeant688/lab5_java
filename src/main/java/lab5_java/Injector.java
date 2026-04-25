package lab5_java;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Класс, реализующий механизм внедрения зависимостей.
 */
public class Injector
{
    private Properties properties;

    /**
     * Конструктор по умолчанию.
     * Инициализирует Injector с использованием файла настроек "config.properties" по умолчанию.
     */
    public Injector()
    {
        this("config.properties");
    }

    /**
     * Конструктор с параметром.
     * * @param propertiesFileName путь к файлу настроек конфигурации
     * @throws RuntimeException если файл не найден или произошла ошибка при чтении
     */
    public Injector(String propertiesFileName) 
    {
        properties = new Properties();
        try (FileInputStream file = new FileInputStream(propertiesFileName)) 
        {
            properties.load(file);
        }
        catch (Exception ex) 
        {
            throw new RuntimeException("Ошибка при загрузке properties", ex);
        }
    }

    /**
     * Метод анализирует переданный объект и внедряет зависимости в его поля,
     * которые помечены аннотацией {@link AutoInjectable}.
     *
     * @param object объект, в который необходимо внедрить зависимости
     * @param <T> тип обрабатываемого объекта
     * @return исходный объект с проинициализированными полями
     */
    public <T> T inject(T object)
    {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields)
        {
            if (field.isAnnotationPresent(AutoInjectable.class))
            {
                String interfaceName = field.getType().getName();
                String сlassName = properties.getProperty(interfaceName);

                if (сlassName != null)
                {
                    try
                    {
                        Class<?> implClass = Class.forName(сlassName);
                        Object instance = implClass.getDeclaredConstructor().newInstance();

                        field.setAccessible(true);
                        field.set(object, instance);
                    }
                    catch (Exception e)
                    {
                        System.err.println("Ошибка инъекции для поля: " + field.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
        return object;
    }
}