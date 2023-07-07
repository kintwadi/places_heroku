
package com.place.pub.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.place.admin.service.StorageManager;
import com.place.pages.Footer;
import com.place.pages.Header;
import com.place.pages.PropertyCard;
import com.place.pages.Section;


@Controller
public class PaginationController {
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
	@GetMapping("/all_properties")
	private String getAllProperties(HttpServletRequest request, HttpServletResponse response,Model model) {
		
		Header header = new Header("/");
		model.addAttribute("headerData", header.loadHeader(request, response));
		
		PropertyCard propertyCard = new PropertyCard();
		
		model.addAttribute("propertyCards", propertyCard.limitedProperties(request,storageService));
		
		Section section = new Section ();
		model.addAttribute("section", section.createSingleSection(request));
		Footer footer = new Footer();
		model.addAttribute("footer",footer.createFooterContent(request));
		
        return "public/properties";
	}
	
	@GetMapping("/properties")
	@ResponseBody
	private List<PropertyCard> properties(HttpServletRequest request, HttpServletResponse response,Model model) {
		
		PropertyCard propertyCard = new PropertyCard();
		
        return propertyCard.pagination(request,storageService);
	}
	
	
	
	
	


}
