package Serial;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class xmlChild extends DocumentProvider {
	public static final String ROOT_NAME = "sensor";
	public static final String VALUE_NAME = "value";
	public static final String VALUE_PATH = "//sensor/@value";
	
	public xmlChild(int value) throws ParserConfigurationException {
		super();
		setElement(ROOT_NAME);
		setValue(value);
	}
	
	public xmlChild() throws ParserConfigurationException{
		super();
	}
	
	public xmlChild(Document document) {
		super(document);
	}
	
	public xmlChild(InputStream stream) throws SAXException, IOException, ParserConfigurationException {
		super(stream);
	}
	
	public xmlChild(String content) throws SAXException, IOException, ParserConfigurationException {
		super(content);
	}
	
	public void setValue(int value) {
		setAttribute(VALUE_NAME, Integer.toString(value));
	}
	
	public int getValue() {
		try {
			return Integer.parseInt((String) path(VALUE_PATH, XPathConstants.STRING));
		}
		catch (XPathExpressionException e) {
			return -1;
		}
	}
}

