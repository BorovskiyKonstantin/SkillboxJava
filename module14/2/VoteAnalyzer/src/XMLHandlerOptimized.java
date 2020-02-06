import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class XMLHandlerOptimized extends DefaultHandler {
    private String voter;
    private SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private HashMap<String, Integer> votersCount;

    public XMLHandlerOptimized() {
        votersCount = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            if (qName.equals("voter") && voter == null) {
                Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
                voter = attributes.getValue("name") + " (" + birthDayFormat.format(birthDay) + ")";
            } else if (qName.equals("visit") && voter != null) {
                int count = votersCount.getOrDefault(voter, 0);
                votersCount.put(voter, count + 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("voter")) {
            voter = null;
        }
    }

    public void printDuplicatedVoters() {
        for (String voter : votersCount.keySet()) {
            int count = votersCount.get(voter);
            if (count > 1) {
                System.out.println(voter + " = " + count);
            }
        }
    }
}

