package flickr.controller;

import flickr.rest.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@Autowired
	private RestClient restClient;

	public void setRestClient(RestClient restClient) {
		this.restClient = restClient;
	}

	@RequestMapping(value="/")
	public String index() {
		return "index";
	}

	@RequestMapping(value="/results")
	public String results(Model model) {
		String keyword = "house";
		model.addAttribute("photos", restClient.searchPhotosByKeyword(keyword));
		return "results";
	}

}
