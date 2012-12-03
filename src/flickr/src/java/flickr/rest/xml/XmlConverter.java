package flickr.rest.xml;

import flickr.rest.response.Photo;
import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlConverter {

	private XmlParser xmlParser;

	public XmlConverter(XmlParser xmlParser) {
		this.xmlParser = xmlParser;
	}

	public List<Photo> mapPhotos(String xml) {
		NodeList nodeList = (NodeList) xmlParser.getNodesByXpath(xml, "/rsp/photos/photo");

		List<Photo> photos = new LinkedList<Photo>();

		Integer length = nodeList.getLength();
		for (int i = 0; i < length; i++) {
			Node node = nodeList.item(i);

			Photo photo = new Photo();

			String id = xmlParser.getAttributeValue(node, "id");
			String owner = xmlParser.getAttributeValue(node, "owner");
			String secret = xmlParser.getAttributeValue(node, "secret");
			String server = xmlParser.getAttributeValue(node, "server");
			String farm = xmlParser.getAttributeValue(node, "farm");
			String title = xmlParser.getAttributeValue(node, "title");

			photo.setId(id);
			photo.setOwner(owner);
			photo.setSecret(secret);
			photo.setServer(server);
			photo.setFarm(farm);
			photo.setTitle(title);

			System.out.println(photo);

			photos.add(photo);
		}

		return photos;
	}
}
