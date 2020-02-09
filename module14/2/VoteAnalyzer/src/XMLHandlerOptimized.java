import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class XMLHandlerOptimized extends DefaultHandler {
    private Voter voter;
    private SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private List<Voter> voters;

    public XMLHandlerOptimized() {
        voters = new Vector<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            if (qName.equals("voter") && voter == null) {
                Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
                voter = new Voter(attributes.getValue("name"), birthDay);
            } else if (qName.equals("visit") && voter != null) {
                if (voters.contains(voter)) {
                    voters.get(voters.indexOf(voter)).addVote();
                } else {
                    voter.addVote();
                    voters.add(voter);
                }
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
        for (Voter voter : voters) {
            int count = voter.getVoteCount();
            if (count > 1) {
                System.out.println(voter.toString() + " = " + count);
            }
        }
    }
}
