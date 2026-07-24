import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class HomeWorkFirstTest {
    @Test
    @DisplayName("Задача 1: разработать метод с сигнатурой publiс static boolean isEven(int n)." +
            "Метод возвращает true, если число чётное, и false — если нечётное.")
    void task1(){
        System.out.println(HomeWorkFirst.isEven(-3));
        System.out.println(HomeWorkFirst.isEven(3));
        System.out.println(HomeWorkFirst.isEven(0));
        System.out.println(HomeWorkFirst.isEven(12));
        System.out.println(HomeWorkFirst.isEven(-2));
    }
    @Test
    @DisplayName("Задача 2: разработать метод с сигнатурой public static String checkAccess(int age)." +
            "Метод возвращает Allowed, если число строго больше 18, и Denied — если меньше.")
    void task2(){
        System.out.println(HomeWorkFirst.checkAccess(-3));
        System.out.println(HomeWorkFirst.checkAccess(18));
        System.out.println(HomeWorkFirst.checkAccess(19));
    }
    @Test
    @DisplayName("Задача 3: разработать метод с сигнатурой public static boolean isPositive(int n)." +
            "Метод должен возвращать true, если переданное число больше или равно нулю, и false, если переданное число меньше нуля." +
            "Проверка внутри метода должна происходить с помощью тернарного оператора.")
    void task3(){
        System.out.println(HomeWorkFirst.isPositive(-1));
        System.out.println(HomeWorkFirst.isPositive(0));
        System.out.println(HomeWorkFirst.isPositive(1));
    }
    @Test
    @DisplayName("Задача 4: разработать метод с сигнатурой public static String getGrade(int score)." +
            "Метод возвращает строку, соответствующую строгому вхождению в границы:" +
            "0–20: E;" +
            "21–40: D;" +
            "41–60: C;" +
            "61–80: B;" +
            "81–100: A")
    void task4(){
        System.out.println(HomeWorkFirst.getGrade(-1));
        System.out.println(HomeWorkFirst.getGrade(0));
        System.out.println(HomeWorkFirst.getGrade(21));
        System.out.println(HomeWorkFirst.getGrade(41));
        System.out.println(HomeWorkFirst.getGrade(61));
        System.out.println(HomeWorkFirst.getGrade(81));
        System.out.println(HomeWorkFirst.getGrade(101));
    }
    @Test
    @DisplayName("Задача 5: разработать метод с сигнатурой public static String blastOff(int start)." +
            "Метод принимает стартовое число (например, 5)" +
            "и возвращает строку со всеми числами до 1 и словом «Поехали!» в конце (например, «5 4 3 2 1 Поехали!»).")
    void task5(){
        System.out.println(HomeWorkFirst.blastOff(3));
        System.out.println(HomeWorkFirst.blastOff(0));
    }
    @Test
    @DisplayName("Задача 6: разработать метод с сигнатурой publiс static int sumToN(int n)." +
            "Метод возвращает сумму всех целых чисел от 1 до n.")
    void task6(){
        System.out.println(HomeWorkFirst.sumToN(3));
        System.out.println(HomeWorkFirst.sumToN(0));
    }
    @Test
    @DisplayName("Задача 7: разработать метод с сигнатурой publiс static boolean hasBug(String[] messages)." +
            "Метод принимает массив строк и возвращает true, если хотя бы одна строка в массиве равна Bug." +
            "Сравнение можно выполнять без учёта регистра.")
    void task7(){
        System.out.println(HomeWorkFirst.hasBug(new String[]{"Bug","frost","perimetr"}));
        System.out.println(HomeWorkFirst.hasBug(new String[]{"front","bug"}));
    }
    @Test
    @DisplayName("Задача 8: разработать метод с сигнатурой publiс static getEvenInRange(int start, int end)." +
            "Метод принимает границы диапазона и возвращает строку," +
            "состоящую только из чётных чисел внутри этого промежутка (включая границы), разделённых пробелом." +
            "Перед первым и после последнего числа пробел не ставится. Например: (2, 5) -> “2 4”")
    void task8(){
        System.out.println(HomeWorkFirst.getEvenInRange(2, 7));
        System.out.println(HomeWorkFirst.getEvenInRange(8,2));
    }
    @Test
    @DisplayName("Задача 9: разработать метод с сигнатурой publiс static public int findMax(int[] arr)." +
            "Метод находит и возвращает самое большое число в переданном массиве.")
    void task9(){
        System.out.println(HomeWorkFirst.findMax(new int[]{2,7,-2,3}));
        System.out.println(HomeWorkFirst.findMax(new int[]{-1,-8,-3,-4}));
    }
    @Test
    @DisplayName("Задача 10: разработать метод с сигнатурой publiс static String[] reverse(String[] arr)." +
            "Метод возвращает новый массив, в котором элементы исходного массива расположены в обратном порядке." +
            "Например, {“One”, “Two”, “Zero”} -> {“Zero”, “Two”, “One}")
    void task10(){
        System.out.println(Arrays.toString(HomeWorkFirst.reverse(new String[]{"One","Two","Zero"} )));
        //System.out.println(HomeWorkFirst.findMax(new int[]{-1,-8,-3,-4}));
    }
    @Test
    @DisplayName("Задача 11: разработать метод с сигнатурой publiс static calcAverage(List<Integer> list)." +
            "Метод вычисляет и возвращает среднее арифметическое всех чисел в списке.")
    void task11(){
        System.out.println(HomeWorkFirst.calcAverage(List.of(1,2,3,4,5)));
        //System.out.println(HomeWorkFirst.findMax(new int[]{-1,-8,-3,-4}));
    }
    @Test
    @DisplayName("Задача 12: разработать метод с сигнатурой " +
            "publiс static List<String> removeSpecificName(List<String> list, String nameToRemove)" +
            "Метод принимает список и имя, которое нужно исключить." +
            "Возвращает новый список, не содержащий указанного имени.")
    void task12(){
        System.out.println(HomeWorkFirst.removeSpecificName(List.of("Петя","Андрей","Ваня","Игорь"),"Ваня"));
        System.out.println(HomeWorkFirst.removeSpecificName(List.of("Ваня","Андрей","Ваня","Игорь"),"Ваня"));
    }
}
