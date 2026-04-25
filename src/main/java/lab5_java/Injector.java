package lab5_java;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class Injector
{

    private Properties properties;

    public Injector()
    {
        this("config.properties");
    }

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