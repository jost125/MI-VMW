package flickr.rest;

import flickr.rest.response.Photo;
import flickr.rest.xml.XmlConverter;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class RestClient {

	private String apiKey;
	private RestTemplate restTemplate;
	private XmlConverter xmlConverter;

	public RestClient(RestTemplate restTemplate, String apiKey, XmlConverter xmlConverter) {
		this.apiKey = apiKey;
		this.restTemplate = restTemplate;
		this.xmlConverter = xmlConverter;
	}

	public List<Photo> searchPhotosByKeyword(String keyword, Integer page) {
		String method = "flickr.photos.search";
		String parameters = "text=" + keyword + "&sort=relevance&per_page=500&page=" + page;
		String xml = query(method, parameters);

		return xmlConverter.mapPhotos(xml);
	}

	private String query(String method, String parameters) {
		final String xml = restTemplate.getForObject("http://api.flickr.com/services/rest/?method=" + method + "&api_key=" + apiKey + "&" + parameters + "&format=rest", String.class);
		return xml;
	}
}