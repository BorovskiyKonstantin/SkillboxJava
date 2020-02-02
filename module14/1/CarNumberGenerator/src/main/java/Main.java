import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {
        defaultMethod();
        threadsMethod(1, 99, Runtime.getRuntime().availableProcessors());
    }

    //Запуск изначального метода и вывод времени его выполнения в консоль
    private static void defaultMethod() {
        Long start = System.currentTimeMillis();

        generateNumbers("numbers.txt", 1, 99);

        System.out.println("Дефолтный метод записи в 1 файл: " + (System.currentTimeMillis() - start));
    }

    //Запуск метода с запуском n потоков c записью n файлов и вывод времени его выполнения в консоль
    private static void threadsMethod(int firstRegion, int lastRegion, int threadsNumber) {

        Long start = System.currentTimeMillis();

        //Разделение регионов по нескольким файлам (частям) по количеству потоков
        int regionCounter = (lastRegion + 1) - firstRegion;  //счетчик регионов
        int regionsInOnePart = (int) Math.ceil((double) regionCounter / threadsNumber);    //количество регионов в одной части
        HashMap<String, Integer[]> parts = new HashMap<>();  //Карта для хранения имени файла и диапазона регионов

        int first = firstRegion;
        int last;
        int partNumber = 1;
        while (regionCounter > 0) {
            if (regionsInOnePart < regionCounter) {
                last = first + regionsInOnePart - 1;
                regionCounter -= regionsInOnePart;
            } else {
                last = first + regionCounter - 1;
                regionCounter = 0;
            }
            parts.put("Part_" + partNumber + ".txt", new Integer[]{first, last});
            first = last + 1;
            partNumber++;
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(threadsNumber);
        parts.forEach((name, range) -> threadPool
                .execute(() -> generateNumbers(name, range[0], range[1])));

        threadPool.shutdown();
        while (true) {
            if (threadPool.isTerminated()) break;
        }
        System.out.println("Многопоточный метод: " + (System.currentTimeMillis() - start));
    }

    //Метод генерации номеров для заданного диапазона регионов и их запись в файл с заданным именем
    private static void generateNumbers(String fileName, int firstRegionCode, int lastRegionCode) {
        String savePath = "res/" + fileName;
        char[] letters = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(savePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int regionCode = firstRegionCode; regionCode <= lastRegionCode; regionCode++) {
            StringBuilder builder = new StringBuilder();
            for (int number = 1; number < 1000; number++) {
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            builder
                                    .append(firstLetter)
                                    .append(padNumber(number, 3))
                                    .append(secondLetter)
                                    .append(thirdLetter)
                                    .append(regionCode)
                                    .append("\n");
                        }
                    }
                }
            }
            writer.write(builder.toString());
        }
        writer.flush();
        writer.close();
    }

    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        StringBuilder builder = new StringBuilder();
        int padSize = numberLength - numberStr.length();
        for (int i = 0; i < padSize; i++) {
            builder.append("0");
        }
        builder.append(number);

        return builder.toString();
    }
}
