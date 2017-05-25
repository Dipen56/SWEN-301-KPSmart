package main;

import java.net.URL;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * This class is used to populate the initial data, so the programme won't have to be demonstrated with blank data.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class DataPopulater {

    // TODO: use XML writer to write stuff into xml files

    public Document parse(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    public static Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "users" );

        Element author1 = root.addElement( "clerk" )
                .addAttribute( "uid", "000" )
                .addAttribute( "name", "James" )
                .addAttribute( "password", "test123" )
                .addText( "James Strachan" );

        Element author2 = root.addElement( "manager" )
                .addAttribute( "uid", "001" )
                .addAttribute( "name", "Bob" )
                .addAttribute( "password", "test123" )
                .addText( "Bob McWhirter" );

        return document;
    }

    public static void testXMLWrite(){
        try {
            Document document = createDocument();

            // Pretty print the document to System.out
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer;
            writer = new XMLWriter( new FileWriter("KPSSoftware/resources/xml/user-logins.xml"), format);
            writer.write( document );
            writer.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
