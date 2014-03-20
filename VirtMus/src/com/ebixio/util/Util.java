/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebixio.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author gburca
 */
public class Util {

    public static String join(Collection<String> s, String delimiter) {
        if (s.isEmpty()) {
            return "";
        }
        Iterator<String> iter = s.iterator();
        StringBuilder buffer = new StringBuilder(iter.next());
        while (iter.hasNext()) {
            buffer.append(delimiter).append(iter.next());
        }
        return buffer.toString();
    }

    public static boolean validateXml(File xml, InputStream xsd) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();

            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = parser.parse(xml);
            validator.validate(new DOMSource(document));

            return true;    // No exceptions == valid document
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (SAXException ex) {
            Exceptions.printStackTrace(ex);
        }

        return false;
    }
}