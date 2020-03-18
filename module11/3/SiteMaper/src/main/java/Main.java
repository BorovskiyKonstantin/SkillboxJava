import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ForkJoinPool;

public class Main {
    static final String rootDomain = "https://skillbox.ru";
    static final String loadPath = "src/load/Map.txt";

    public static void main(String[] args) {
        Set<String> parsedLinks; //Set для
        try {
            Node firstNode = Parser.parseNode(rootDomain, rootDomain);
            LinkParsingTask firstTask = new LinkParsingTask(firstNode, new TreeSet<>());
            new ForkJoinPool().invoke(firstTask);
            parsedLinks = firstTask.getSiteMap();
            System.out.println("Ссылок найдено: " + parsedLinks.size());
            String siteMap = createSiteMap(parsedLinks);
            writeFile(loadPath, siteMap);
            System.out.println("Карта готова!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//main

    //Метод форматирования списка страниц сайта (добавление табуляции)
    private static String createSiteMap(Set<String> linksSet) {
        Map<String, Integer> map = new TreeMap();   //Промежуточная таблица K - обработанная ссылка, V - количество табуляций
        String result = null;   //строка с отформатированными ссылками

        //Обработка каждой ссылки в цикле
        for (String link : linksSet) {
            String selectedParentLink = null;   //родительская ссылка для обработки
            //Поиск самой длинной родительской ссылки среди уже обработанных
            for (Entry entry : map.entrySet()) {
                String parentLink = (String) entry.getKey();
                if (link.matches(parentLink + ".*")) {
                    if (selectedParentLink == null) {
                        selectedParentLink = parentLink;
                        continue;
                    }
                    int selectedLength = selectedParentLink.length();
                    int parentLength = parentLink.length();
                    if (selectedLength < parentLength) {
                        selectedParentLink = parentLink;
                    }
                }
            }
            if (selectedParentLink == null) {
                map.put(link, 0);
                continue;
            }
            //Расчет табуляции и добавление ссылки в map
            int tabsCount = map.get(selectedParentLink) + 1;
            map.put(link, tabsCount);
        }

        StringBuilder builder = new StringBuilder();
        for (Entry entry : map.entrySet()) {
            String link = (String) entry.getKey();
            Integer tabCount = (Integer) entry.getValue();
            for (int i = 0; i < tabCount; i++) {
                builder.append("\t");
            }
            builder.append(link + "\n");
        }

        result = builder.toString();
        return result;
    }

    private static void writeFile(String path, String data) {
        try {
            Files.write(Paths.get(path), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


