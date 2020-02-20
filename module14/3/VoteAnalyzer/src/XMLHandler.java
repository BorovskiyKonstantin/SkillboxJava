import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;

public class XMLHandler extends DefaultHandler {
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            if (qName.equals("voter")) {
                String name = attributes.getValue("name");
                String birthDay = attributes.getValue("birthDay");
                DBConnection.countVoter(name, birthDay);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endDocument() throws SAXException {
        try {
            DBConnection.executeMultiInsert();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
