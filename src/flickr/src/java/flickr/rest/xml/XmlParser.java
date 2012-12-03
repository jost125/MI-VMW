package flickr.rest.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlParser {

	DocumentBuilder documentBuilder;
	XPath xpathCompiler;

	public XmlParser(DocumentBuilder documentBuilder, XPath xpathCompiler) {
		this.documentBuilder = documentBuilder;
		this.xpathCompiler = xpathCompiler;
	}

	public NodeList getNodesByXpath(String xml, String xpath) {
		return (NodeList) evaluateXpathOnXml(xml, xpath, XPathConstants.NODESET);
	}

	public Node getNodeByXpath(String xml, String xpath) {
		return (Node) evaluateXpathOnXml(xml, xpath, XPathConstants.NODE);
	}

	public String getStringByXpath(String xml, String xpath) {
		return (String) evaluateXpathOnXml(xml, xpath, XPathConstants.STRING);
	}

	public String getAttributeValue(Node node, String attributeName) {
		NamedNodeMap namedNodeMap = node.getAttributes();
		Node attribute = namedNodeMap.getNamedItem(attributeName);
		String attributeValue = null;

		if (attribute != null) {
			attributeValue = attribute.getNodeValue();
		}

		return attributeValue;
	}

	private Object evaluateXpathOnXml(String xml, String xpath, QName returnType) {
		Object result = null;
		try {
			result = tryEvaluateXpathOnXml(xml, xpath, returnType);
		} catch (Exception ex) {
			Logger.getLogger(XmlParser.class.getName()).log(Level.SEVERE, null, ex);
			throw new CannotParseXmlException(ex);
		}

		return result;
	}

	private Object tryEvaluateXpathOnXml(String xml, String xpath, QName returnType) throws SAXException, IOException, XPathExpressionException {
		Document document = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		XPathExpression expression = xpathCompiler.compile(xpath);
		return expression.evaluate(document, returnType);
	}
}