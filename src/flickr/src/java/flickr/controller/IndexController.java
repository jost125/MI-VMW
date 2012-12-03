package flickr.controller;

import flickr.form.Search;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping(value="/index.htm")
	public String index(Model model) {
		model.addAttribute("searchForm", new Search());
		return "index";
	}

}
