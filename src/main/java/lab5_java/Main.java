package lab5_java;

/**
 * Главный класс приложения для запуска и демонстрации работы инжектора.
 */
public class Main 
{
    public static void main(String[] args) 
    {
        SomeBean sb = (new Injector()).inject(new SomeBean());
        sb.foo(); 
    }
}