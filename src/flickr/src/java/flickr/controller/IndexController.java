package flickr.controller;

import flickr.api.FlickrApi;
import flickr.api.image.Color;
import flickr.form.Search;
import flickr.form.SearchValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@Autowired
	private FlickrApi flickrApi;

	@Autowired
	private SearchValidator searchValidator;

	public void setRestClient(FlickrApi flickrApi) {
		this.flickrApi = flickrApi;
	}

	public void setSearchValidator(SearchValidator searchValidator) {
		this.searchValidator = searchValidator;
	}

	@RequestMapping(value="/")
	public String index(Model model, @ModelAttribute("searchForm") Search search, BindingResult result) {
		model.addAttribute("searchForm", new Search());
		searchValidator.validate(search, result);
		if (!result.hasErrors()) {
			return "redirect:results";
		}
		return "index";
	}

	@RequestMapping(value="/results")
	public String results(@ModelAttribute("searchForm") Search search, BindingResult result, Model model) {
		String color = search.getColor().replaceFirst("^#", "");
		model.addAttribute("photos", flickrApi.getPhotos(search.getTerm(), Color.getInstaceFromHex(color), search.getNumberOfResults()));
		model.addAttribute("searchForm", new Search());
		model.addAttribute("search", search);
		return "results";
	}

}
