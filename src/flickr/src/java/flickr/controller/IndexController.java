package flickr.controller;

import flickr.api.FlickrApi;
import flickr.api.image.Color;
import flickr.form.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@Autowired
	private FlickrApi flickrApi;

	public void setRestClient(FlickrApi flickrApi) {
		this.flickrApi = flickrApi;
	}

	@RequestMapping(value="/")
	public String index(Model model) {
		model.addAttribute("searchForm", new Search());
		return "index";
	}

	@RequestMapping(value="/results")
	public String results(@ModelAttribute("searchForm") Search search, Model model) {
		String color = search.getColor().replaceFirst("^#", "");
		model.addAttribute("photos", flickrApi.getPhotos(search.getTerm(), Color.getInstaceFromHex(color), search.getNumberOfResults()));
		return "results";
	}

}
