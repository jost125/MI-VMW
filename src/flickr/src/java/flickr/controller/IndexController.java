package flickr.controller;

import flickr.api.FlickrApi;
import flickr.api.image.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@Autowired
	private FlickrApi flickrApi;

	public void setRestClient(FlickrApi flickrApi) {
		this.flickrApi = flickrApi;
	}

	@RequestMapping(value="/")
	public String index() {
		return "index";
	}

	@RequestMapping(value="/results")
	public String results(Model model) {
		String keyword = "house";
		model.addAttribute("photos", flickrApi.getPhotos(keyword, Color.getInstaceFromHex("FFFFFF"), 10));
		return "results";
	}

}
