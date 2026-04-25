package lab5_java;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class Injector 
{
    
    public <T> T inject(T object) 
    {
        try 
        {
            FileInputStream file = new FileInputStream("config.properties");
            Properties properties = new Properties();
            properties.load(file);

            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) 
            {
                if (field.isAnnotationPresent(AutoInjectable.class)) 
                {
                    String interfaceName = field.getType().getName();
                    String implClassName = properties.getProperty(interfaceName);
                    
                    if (implClassName != null) 
                    {
                        Object instance = Class.forName(implClassName).newInstance();
                        
                        field.setAccessible(true);
                        field.set(object, instance);
                    }
                }
            }
        }
        
        catch (Exception e) 
        {
            System.out.println("Ошибка инъекции: " + e.getMessage());
        }
        
        return object;
    }
}
