
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
    private static int newWidth = 300;
    private static int processorsCount = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {


        String srcFolder = "res/src";
        String dstFolder = "res/dst";

        File srcDir = new File(srcFolder);
        long start = System.currentTimeMillis();
        File[] files = srcDir.listFiles();
        int filesCount = files.length;

        //подсчет необходимого количества потоков
        int threadsCount = filesCount;
        if (threadsCount >= processorsCount) threadsCount = processorsCount;
        System.out.println("Количество потоков: " + threadsCount);

        //массив с кол-вом файлов в каждом потоке
        int[] sizeOfThreads = new int[threadsCount];
        //равномерное распределение файлов по потокам
        int fileNumber = 0;
        while (fileNumber < filesCount) {
            for (int i = 0; i < sizeOfThreads.length && fileNumber < filesCount; i++) {
                sizeOfThreads[i]++;
                fileNumber++;
            }
        }

        //Разделение общего списка файлов на фрагменты
        int scrPos = 0; //Отслеживаемая позиция копируемого элеммента
        for (int i = 0; i < sizeOfThreads.length; i++) {
            File[] filesPart = new File[sizeOfThreads[i]];
            System.arraycopy(files, scrPos, filesPart, 0, filesPart.length);
            ImageResizer resizer = new ImageResizer(filesPart, dstFolder, newWidth, start);
            scrPos = scrPos + filesPart.length;
            new Thread(resizer).start();
        }
    }//main
}
