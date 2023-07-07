
package com.place.pub.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.place.admin.controller.handler.RequestHandler;
import com.place.admin.model.PropertyRequest;
import com.place.admin.service.StorageManager;
import com.place.pages.Footer;
import com.place.pages.Header;
import com.place.pages.PropertyCard;
import com.place.pages.Section;
import com.place.pages.SinglePropertyCard;

@Controller
public class PublicController {
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


	@GetMapping("/")
	private String home(HttpServletRequest request, HttpServletResponse response,Model model) {
		
		Header header = new Header("/");
		model.addAttribute("headerData", header.loadHeader(request, response));
		
		PropertyCard propertyCard = new PropertyCard();
		
		model.addAttribute("propertyCards", propertyCard.limitedProperties(request,storageService));
		
		Section section = new Section ();
		model.addAttribute("section", section.createSingleSection(request));
		
		Footer footer = new Footer();
		model.addAttribute("footer",footer.createFooterContent(request));
		
        return "public/init";
	}
	
	
	

	@GetMapping("/find_property")
	private String findProperty(HttpServletRequest request, HttpServletResponse response,Model model) {
		
		
		Header header = new Header("/");
		model.addAttribute("headerData", header.loadHeader(request, response));
		
		PropertyCard propertyCard = new PropertyCard();
		
		model.addAttribute("propertyCards", propertyCard.limitedProperties(request,storageService));
		
		Section section = new Section ();
		model.addAttribute("section", section.createSingleSection(request));
		Footer footer = new Footer();
		model.addAttribute("footer",footer.createFooterContent(request));
		
		SinglePropertyCard singlePropertyCard = new SinglePropertyCard();
		SinglePropertyCard singleProperty = singlePropertyCard.createSingleProperty(request, storageService);
		
		System.out.println("singleProperty: "+singleProperty.toString());
		
		model.addAttribute("singlePropertyCard", singleProperty);
		model.addAttribute("propertyCards", propertyCard.limitedProperties(request,storageService));
		
        return "public/property";
	}
	
	
	// ajax 
	@PostMapping("/post_email")
	@ResponseBody
	private String postEmail(HttpServletRequest request, HttpServletResponse response,Model model) {
		
		RequestHandler requestHandler = new RequestHandler();
		PropertyRequest propertyRequest = requestHandler.bind(request, response);
		boolean result = storageService.processRequest(propertyRequest);
		String message = "Your message was sent";
        return result ? message : "failed";
	}
	

	
}
