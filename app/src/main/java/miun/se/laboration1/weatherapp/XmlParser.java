package miun.se.laboration1.weatherapp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class XmlParser {

    public CityWeather parseStringData(String stringData){

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(stringData));
            Document document = builder.parse(inputSource);


            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            String objectPath = "/weatherdata/product/time[1]/location";

            String temperatureStr = xpath.evaluate(objectPath + "/temperature/@value", document).parseDouble();
            double temperature = Double.parseDouble(temperatureStr);

            String windSpeedStr = xpath.evaluate(objectPath + "/windSpeed/@mps", document);
            double windSpeed = Double.parseDouble(windSpeedStr);

            String cloudinessStr = xpath.evaluate(objectPath + "/cloudiness/@percent", document);
            double cloudiness = Double.parseDouble(cloudinessStr);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }





        return 0;
    }
}
