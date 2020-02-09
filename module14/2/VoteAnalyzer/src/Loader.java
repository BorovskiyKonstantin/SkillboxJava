import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.*;
import java.io.File;

import java.text.SimpleDateFormat;
import java.util.*;

public class Loader {
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private static HashMap<Integer, WorkTime> voteStationWorkTimes;
    private static HashMap<Voter, Integer> voterCounts;

    private static String fileName = "res/data-18M.xml";

    public static void main(String[] args) throws Exception {
        long usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        /**Список различных коллекций из 1_000_000 Voters и занимаемая память*/
//        Voter[] voters = new Voter[1_000_000];    //101 МБ
//        Vector<Voter> voters = new Vector<>();    //109 МБ
//        ArrayList<Voter> voters = new ArrayList<>();  //115 МБ
//        LinkedList<Voter> voters = new LinkedList<>();    //122 МБ
//        TreeSet<Voter> voters = new TreeSet<>();  //141 МБ
//        HashSet<Voter> voters = new HashSet<>();  //154 МБ
//        TreeMap<Voter,Integer> voters = new TreeMap<>();  //158 МБ
//        HashMap<Voter, Integer> voters = new HashMap<>(); //168 МБ


//        for (int i = 0; i < 1_000_000; i++) {
//            voters.add(new Voter(Integer.toString(i),new Date()));
//            voters.put(new Voter(Integer.toString(i),new Date()),i);
//            voters[i] = new Voter(Integer.toString(i),new Date());
//        }

//        methodDOM();// -Xmx200M
//        methodSAX();// -Xmx24M до и -Xmx23M после оптимизации Voter
        methodSAXOptimized();// -Xmx17M (Оптимизированный вариант - 14,7 МБ памяти и 90 секунд выполнения)

        usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
        System.out.println("Результат измерения занимаемой памяти:");
        System.out.println(((double) usage / 1_000_000) + " MB");
    }

    private static void methodSAX() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        parser.parse(new File(fileName), handler);
        handler.printDuplicatedVoters();
    }

    private static void methodSAXOptimized() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandlerOptimized handler = new XMLHandlerOptimized();
        parser.parse(new File(fileName), handler);
        handler.printDuplicatedVoters();
    }

    private static void methodDOM() throws Exception {
        voteStationWorkTimes = new HashMap<>();
        voterCounts = new HashMap<>();

        parseFile(fileName);

        //Printing results
        System.out.println("Voting station work times: ");
        for (Integer votingStation : voteStationWorkTimes.keySet()) {
            WorkTime workTime = voteStationWorkTimes.get(votingStation);
            System.out.println("\t" + votingStation + " - " + workTime);
        }

        System.out.println("Duplicated voters: ");
        for (Voter voter : voterCounts.keySet()) {
            Integer count = voterCounts.get(voter);
            if (count > 1) {
                System.out.println("\t" + voter + " - " + count);
            }
        }
    }

    private static void parseFile(String fileName) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(fileName));

        findEqualVoters(doc);
        fixWorkTimes(doc);
    }

    private static void findEqualVoters(Document doc) throws Exception {
        NodeList voters = doc.getElementsByTagName("voter");
        int votersCount = voters.getLength();
        for (int i = 0; i < votersCount; i++) {
            Node node = voters.item(i);
            NamedNodeMap attributes = node.getAttributes();

            String name = attributes.getNamedItem("name").getNodeValue();
            Date birthDay = birthDayFormat.parse(attributes.getNamedItem("birthDay").getNodeValue());

            Voter voter = new Voter(name, birthDay);
            Integer count = voterCounts.get(voter);
            voterCounts.put(voter, count == null ? 1 : count + 1);
        }
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