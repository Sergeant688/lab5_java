package lab5_java;

/**
 * Класс, содержащий поля для автоматического внедрения зависимостей.
 */
public class SomeBean 
{
    @AutoInjectable
    private SomeInterface field1;
    
    @AutoInjectable
    private SomeOtherInterface field2;

    /**
     * Метод для проверки работоспособности внедренных зависимостей.
     */
    public void foo() 
    {
        field1.doSomething();
        field2.doSomeOther();
    }
}
