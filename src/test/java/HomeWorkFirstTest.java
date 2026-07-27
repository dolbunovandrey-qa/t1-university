
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HomeWorkFirstTest {

    @BeforeEach
    void startMessage(){
        System.out.println("========================\nTest method start");
    }
    @AfterEach
    void endMessage(){
        System.out.println("Test method end\n========================");
    }
    @Test
    @DisplayName("Задача 1: разработать метод с сигнатурой publiс static boolean isEven(int n)." +
            "Метод возвращает true, если число чётное, и false — если нечётное.")
    void testIsEven(){
        Random random = new Random();
        int number = random.nextInt(1,100);
        boolean expectedResult = true;
        boolean actualityResult = HomeWorkFirst.isEven(number);
        if(actualityResult == expectedResult){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
    @RepeatedTest(20)
    @DisplayName("Задача 2: разработать метод с сигнатурой public static String checkAccess(int age)." +
            "Метод возвращает Allowed, если число строго больше 18, и Denied — если меньше.")
    void testCheckAccess(){
        Random random = new Random();
        int age = random.nextInt(0,99);
        String expectedResult = "Allowed";
        String actualityResult = HomeWorkFirst.checkAccess(age);
        if(actualityResult.equals(expectedResult)){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
    @RepeatedTest(5)
    @DisplayName("Задача 3: разработать метод с сигнатурой public static boolean isPositive(int n)." +
            "Метод должен возвращать true, если переданное число больше или равно нулю, и false, если переданное число меньше нуля." +
            "Проверка внутри метода должна происходить с помощью тернарного оператора.")
    void testIsPositive(){
        Random random = new Random();
        int num = random.nextInt();
        boolean expectedResult = true;
        boolean actualityResult = HomeWorkFirst.isEven(num);
        if(actualityResult == expectedResult){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
    //Метод для заполнения массива
    static int[] getArr(){
        Random random = new Random();
        int lengthArr = random.nextInt(1,10);
        int[] arrNumber = new int[lengthArr];
        for (int i = 0; i<lengthArr;i++){
            arrNumber[i] = random.nextInt(0,100);
        }
        return arrNumber;
    }
    @ParameterizedTest
    @MethodSource("getArr")
    @DisplayName("Задача 4: разработать метод с сигнатурой public static String getGrade(int score)." +
            "Метод возвращает строку, соответствующую строгому вхождению в границы:" +
            "0–20: E;" +
            "21–40: D;" +
            "41–60: C;" +
            "61–80: B;" +
            "81–100: A")
    void testGetGrade(int value){
        String expectedResult = "B";
        String actualityResult = HomeWorkFirst.getGrade(value);
        if (actualityResult.equals(expectedResult)){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
    @Test
    @DisplayName("Задача 5: разработать метод с сигнатурой public static String blastOff(int start)." +
            "Метод принимает стартовое число (например, 5)" +
            "и возвращает строку со всеми числами до 1 и словом «Поехали!» в конце (например, «5 4 3 2 1 Поехали!»).")
    void testBlastOff(){
        Random random = new Random();
        int start = random.nextInt(0,10);
        String expectedResult = "5 4 3 2 1 Поехали!";
        String actualityResult = HomeWorkFirst.blastOff(start);
        System.out.println(actualityResult);
        if (actualityResult.equals(expectedResult)){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
    @RepeatedTest(10)
    @DisplayName("Задача 6: разработать метод с сигнатурой publiс static int sumToN(int n)." +
            "Метод возвращает сумму всех целых чисел от 1 до n.")
    void testSumToN(){
        Random random = new Random();
        int number = random.nextInt(15);
        int expectedResult = 36;
        int actualityResult = HomeWorkFirst.sumToN(number);
        if (actualityResult==expectedResult){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
    @RepeatedTest(3)
    @DisplayName("Задача 7: разработать метод с сигнатурой publiс static boolean hasBug(String[] messages)." +
            "Метод принимает массив строк и возвращает true, если хотя бы одна строка в массиве равна Bug." +
            "Сравнение можно выполнять без учёта регистра.")
    void testHasBug(){
        Random random = new Random();
        int arrLength = random.nextInt(1,10);
        String[] arrMessages = new String[arrLength];
        for (int i=0; i<arrLength;i++){
            arrMessages[i] = RandomStringUtils.insecure().next(5);
        }
        boolean expectedResult = true;
        boolean actualityResult = HomeWorkFirst.hasBug(arrMessages);
        if (actualityResult==expectedResult){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    @DisplayName("Задача 8: разработать метод с сигнатурой publiс static getEvenInRange(int start, int end)." +
            "Метод принимает границы диапазона и возвращает строку," +
            "состоящую только из чётных чисел внутри этого промежутка (включая границы), разделённых пробелом." +
            "Перед первым и после последнего числа пробел не ставится. Например: (2, 5) -> “2 4”")
    void testGetEvenInRange(){
        Random random = new Random();
        int start = random.nextInt(10);
        int end = random.nextInt(20);
        String expectedResult = "2 4 6 8 10";
        String actualityResult = HomeWorkFirst.getEvenInRange(start,end);
        System.out.println(actualityResult);
        if (actualityResult.equals(expectedResult)){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
    @Test
    @DisplayName("Задача 9: разработать метод с сигнатурой publiс static public int findMax(int[] arr)." +
            "Метод находит и возвращает самое большое число в переданном массиве.")
    void testFindMax(){
        Random random = new Random();
        int lengthArr = random.nextInt(1,20);
        int[] numbers = new int[lengthArr];
        for (int i = 0; i<lengthArr;i++){
            numbers[i]= random.nextInt(30);
        }
        int expectedResult = 30;
        int actualityResult = HomeWorkFirst.findMax(numbers);
        if (actualityResult == expectedResult){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        System.out.println(actualityResult);
    }
    @Test
    @DisplayName("Задача 10: разработать метод с сигнатурой publiс static String[] reverse(String[] arr)." +
            "Метод возвращает новый массив, в котором элементы исходного массива расположены в обратном порядке." +
            "Например, {“One”, “Two”, “Zero”} -> {“Zero”, “Two”, “One}")
    void testReverse(){
        String[] arr = new String[]{"One","two","zero"};
        String[] expectedResult = new String[]{"zero","two","One"};
        String[] actualityResult = HomeWorkFirst.reverse(arr);
        if (Arrays.equals(actualityResult,expectedResult)){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
    @Test
    @DisplayName("Задача 11: разработать метод с сигнатурой publiс static calcAverage(List<Integer> list)." +
            "Метод вычисляет и возвращает среднее арифметическое всех чисел в списке.")
    void testCalcAverage(){
        Random random = new Random();
        List<Integer> numbers = new ArrayList<>();
        int lengthList = random.nextInt(10);
        for (int i = 0;i<=lengthList;i++){
            numbers.add(random.nextInt(10));
        }
        int expectedResult = 12;
        int actualityResult = HomeWorkFirst.calcAverage(numbers);
        System.out.println(actualityResult);
        if (actualityResult == expectedResult){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
    @Test
    @DisplayName("Задача 12: разработать метод с сигнатурой " +
            "publiс static List<String> removeSpecificName(List<String> list, String nameToRemove)" +
            "Метод принимает список и имя, которое нужно исключить." +
            "Возвращает новый список, не содержащий указанного имени.")
    void testRemoveSpecificName(){
        List<String> names = List.of("Ваня","Андрей","Ваня","Игорь");
        String removeName = "Ваня";
        List<String> expectedResult = List.of("Андрей","Игорь");
        List<String> actualityResult = HomeWorkFirst.removeSpecificName(names,removeName);

        System.out.println(actualityResult);
        if (actualityResult.equals(expectedResult)){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
}
