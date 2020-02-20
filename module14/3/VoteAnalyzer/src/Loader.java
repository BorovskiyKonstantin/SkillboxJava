import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.*;
import java.io.File;

import java.text.SimpleDateFormat;
import java.util.*;

public class Loader {
    private static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private static HashMap<Integer, WorkTime> voteStationWorkTimes;
    private static final String FILE_NAME = "res/data-1572M.xml";

    public static void main(String[] args) throws Exception {
        long usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        //Парсинг файла
        long timer1 = System.currentTimeMillis();
        parseFile(FILE_NAME);
        timer1 = System.currentTimeMillis() - timer1;

        //Получение списка проголосоваших более 1 раза
        long timer2 = System.currentTimeMillis();
        DBConnection.printVoterCounts();
        timer2 = System.currentTimeMillis() - timer2;

        long fullTime = timer1 + timer2;

        //Вывод результатов измерения работы программы
        System.out.println("Время парсинга: " + timer1 + " ms. (" + timer1 / 1000 / 60 + " min)");
        System.out.println("Время выполнения printVoterCounts() : " + timer2 + " ms. (" + timer2 / 1000 / 60 + " min)");
        System.out.println("Общее время выполнения: " + fullTime + " ms. (" + fullTime / 1000 / 60 + " min)");

        usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
        System.out.println("Результат измерения занимаемой памяти: " + (usage / 1_000_000 + " МБ"));
        System.out.println();

        //РЕЗУЛЬТАТЫ парсинга data-1572M.xml:
        /**
         * Парсинг с вариантом программы из видеоурока multiinsert:
         *             Время парсинга: 8423195 ms. 140min
         *             Время выполнения printVoterCounts() : 14376 ms.
         *             Результат измерения занимаемой памяти: 266 МБ
         * */

        /**
         * Парсинг после оптимизации:
         *             Время парсинга: 137864 ms. (2 min)
         *             Время выполнения printVoterCounts() : 229469 ms. (3 min)
         *             Общее время выполнения: 367333 ms. (6 min)
         *             Результат измерения занимаемой памяти: 237 МБ
         * */

    }

    private static void parseFile(String fileName) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        parser.parse(new File(fileName), handler);
//        fixWorkTimes(doc);
    }

    private static void fixWorkTimes(Document doc) throws Exception {
        NodeList visits = doc.getElementsByTagName("visit");
        int visitCount = visits.getLength();
        for (int i = 0; i < visitCount; i++) {
            Node node = visits.item(i);
            NamedNodeMap attributes = node.getAttributes();

            Integer station = Integer.parseInt(attributes.getNamedItem("station").getNodeValue());
            Date time = visitDateFormat.parse(attributes.getNamedItem("time").getNodeValue());
            WorkTime workTime = voteStationWorkTimes.get(station);
            if (workTime == null) {
                workTime = new WorkTime();
                voteStationWorkTimes.put(station, workTime);
            }
            workTime.addVisitTime(time.getTime());
        }
    }
}

