
package com.place.pub.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.place.admin.service.StorageManager;
import com.place.pages.Footer;
import com.place.pages.Header;
import com.place.pages.PropertyCard;
import com.place.pages.Section;


@Controller
public class SearchController {
	// ---------------------------------------------------------------------
	// Properties
	// ---------------------------------------------------------------------
	@Autowired
	StorageManager storageService;
	// ---------------------------------------------------------------------
	// Construction
	// ---------------------------------------------------------------------

	// ---------------------------------------------------------------------
	// Private Helper Methods
	// ---------------------------------------------------------------------

	// ---------------------------------------------------------------------
	// Public Methods
	// ---------------------------------------------------------------------
	
	@GetMapping("/search")
	private String search(HttpServletRequest request, HttpServletResponse response, Model model) {

		Header header = new Header("/");
		model.addAttribute("headerData", header.loadHeader(request, response));

		PropertyCard propertyCard = new PropertyCard();

		model.addAttribute("propertyCards", propertyCard.limitedProperties(request, storageService));

		Section section = new Section();
		model.addAttribute("section", section.createSingleSection(request));

		Footer footer = new Footer();
		model.addAttribute("footer", footer.createFooterContent(request));

		return "public/init";
	}


	@PostMapping("/search")
	private String Search(HttpServletRequest request, HttpServletResponse response, Model model) {

		Header header = new Header("/");
		model.addAttribute("headerData", header.loadHeader(request, response));

		PropertyCard propertyCard = new PropertyCard();

		model.addAttribute("propertyCards", propertyCard.limitedProperties(request, storageService));

		Section section = new Section();
		model.addAttribute("section", section.createSingleSection(request));

		Footer footer = new Footer();
		model.addAttribute("footer", footer.createFooterContent(request));
		
		String keyword = request.getParameter("key_word");
		String type = request.getParameter("type");
		String city = request.getParameter("city");
		List<String>options = new ArrayList<String>();

		Map<String, String[]> parameterMap = request.getParameterMap();

		Set<String> keySet = parameterMap.keySet();
		keySet.forEach(key -> {

			if(key.contains("_option_")) 
			{
				options.add(request.getParameter(key));
			}
			
		});

		storageService.search(keyword,type,city,options);
		return "public/init";
	}

}
