package ru.courses2.reflection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReflectionTest {
    @Test
    void reflection () throws NoSuchFieldException {
        Fraction fr = new Fraction(2, 3);
        Fractionable num = Utils.<Fractionable>cache(fr);
        Assertions.assertTrue(fr.testMethodIsCached==0); // инициировали
        num.doubleValue();
        Assertions.assertTrue(fr.testMethodIsCached==1); // первый вызов doubleValue - кэшируем значение
        num.doubleValue();
        Assertions.assertTrue(fr.testMethodIsCached==1); // нет изменений, берем значение из кэша
        num.doubleValue();
        Assertions.assertTrue(fr.testMethodIsCached==1); // нет изменений, берем значение из кэша
        num.setNum(5);
        Assertions.assertTrue(fr.testMethodIsCached==1); // нет изменений, тк кэш только сбросился при установке нового значения
        num.doubleValue();
        Assertions.assertTrue(fr.testMethodIsCached==2); // второй вызов doubleValue, тк было изменение - кэшируем значение
        num.doubleValue();
        Assertions.assertTrue(fr.testMethodIsCached==2); // нет изменений, берем значение из кэша
    }

}
