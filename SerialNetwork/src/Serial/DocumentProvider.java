package Serial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class DocumentProvider {
	// attributes
	protected Document document = null;
	
	// associates
	protected static DocumentBuilder builder = null;
	protected static Transformer transformer = null;
	protected static XPath xpath = null;
	
	public static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		if (builder == null) {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		}
		return builder;
	}
	
	public static Transformer getTransformer() throws TransformerConfigurationException, TransformerFactoryConfigurationError {
		if (transformer == null) {
			transformer = TransformerFactory.newInstance().newTransformer();
		}
		return transformer;
	}
	
	public static XPath getXPath() {
		if (xpath == null) {
			xpath = XPathFactory.newInstance().newXPath(); 
		}
		return xpath;
	}
	
	public DocumentProvider() throws ParserConfigurationException {
		setContent(getDocumentBuilder().newDocument());
	}
	
	public DocumentProvider(Document document) {
		setContent(document);
	}
	
	public DocumentProvider(InputStream stream) throws SAXException, IOException, ParserConfigurationException {
		setContent(stream);
	}
	
	public DocumentProvider(String content) throws SAXException, IOException, ParserConfigurationException {
		setContent(content);
	}
	
	public String getContent() {
		try {
			DOMSource src = new DOMSource(getDocument());
			ByteArrayOutputStream dst = new ByteArrayOutputStream();
			getTransformer().transform(src, new StreamResult(dst));
			return new String(dst.toByteArray());
		}
		catch (TransformerException e) {
			return null;
		}
	}
	
	public void setContent(Document document) {
		this.document = document;
	}
	
	public void setContent(InputStream stream) throws SAXException, IOException, ParserConfigurationException {
		this.document = getDocumentBuilder().parse(stream);
	}
	
	public void setContent(String content) throws SAXException, IOException, ParserConfigurationException {
		setContent(new ByteArrayInputStream(content.getBytes()));
	}
	
	public Document getDocument() {
		return document;
	}
	
	public Element getElement() {
		return getDocument().getDocumentElement();
	}
	
	public void setElement(Node node) {
		getDocument().appendChild(node);
	}
	
	public void setElement(String key) {
		setElement(createChild(key));
	}
	
	public Element createChild(String key) {
		return getDocument().createElement(key);
	}
	
	public Element createChild(String key, String value) {
		Element element = createChild(key); element.setTextContent(value);
		return element;
	}
	
	public void appendChild(Node node) {
		getElement().appendChild(node);
	}
	
	public Attr getAttribute(String key) {
		return getElement().getAttributeNode(key);
	}
	
	public void setAttribute(String key, String value) {
		getElement().setAttribute(key, value);
	}
	
	public boolean hasAttribute(String key) {
		return getElement().hasAttribute(key);
	}
	
	public Object path(String expr, QName type) throws XPathExpressionException {
		return getXPath().evaluate(expr, getDocument(), type);
	}
}
