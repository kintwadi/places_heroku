package com.place.admin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.place.Constants;
import com.place.admin.controller.handler.RequestHandler;
import com.place.admin.model.Address;
import com.place.admin.model.Detail;
import com.place.admin.model.Image;
import com.place.admin.model.Property;
import com.place.admin.model.Settings;
import com.place.admin.model.TUser;
import com.place.admin.service.StorageManager;
@Controller
public class AdminController {
	
	
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


		@GetMapping("/admin")
		private String adminDashboard(HttpServletRequest request, HttpServletResponse response,Model model) {
		
	        return "admin/admin_dashboard";
		}
		
		
		@GetMapping("/add_user")
		public String addUser(HttpServletRequest request, HttpServletResponse response) {

			return "admin/add_user";
		}

		@PostMapping("/add_user")
		public String addUser(@RequestParam("photo") MultipartFile file, HttpServletRequest request,
				HttpServletResponse response) {

			RequestHandler requestHandler = new RequestHandler();

			TUser user = requestHandler.bind(file, request, response);
			storageService.store(user, file);

			return "admin/add_user";

		}
		
		@GetMapping("/view_users")
		public String viewUsers(Model model) {

			List<Property> properties = storageService.getProperties();
			List<TUser> allUsers = storageService.getAllUsers();

			model.addAttribute("properties", properties);

			model.addAttribute("users", allUsers);
			return "admin/view_users";
		}
		
		
		@GetMapping("/update_user")
		public String viewUpdateUsers(HttpServletRequest request, Model model) {

			Long id = Long.valueOf((String) request.getParameter("id"));
			model.addAttribute("user", new TUser());
			if (id != null) {

				TUser user = storageService.getUserById(id);
				model.addAttribute("user", user);

			}

			return "admin/update_user";
		}

		@PostMapping("/update_user")
		public String updateUser(HttpServletRequest request, HttpServletResponse response,
				@RequestParam(value = "photo") MultipartFile file, Model model) {
			RequestHandler requestHandler = new RequestHandler();

			TUser user = requestHandler.bind(file, request, response);
			storageService.updateUser(user,file);

			return "admin/view_users";
		}
		
		// to do
		// when removing a user, remove also its properties
		@GetMapping("remove_user")
		public String removeUser(HttpServletRequest request, HttpServletResponse response, Model model) {

			Long id = Long.valueOf((String) request.getParameter("id"));

			System.out.println("user id: to remove " + id);

			if (id != null && id > 0) {

				storageService.deleteUser(id);
			}

			return "admin/view_users";
		}

		// via ajax
		@PostMapping("delete_user_image")
		@ResponseBody
		public String removeUserImage(HttpServletRequest request, HttpServletResponse response, Model model) {
			TUser user = new TUser();
			Long id = Long.valueOf((String) request.getParameter("id"));
			model.addAttribute("user", new TUser());
			if (id != null) {
				user = storageService.getUserById(id);
			

				String fileName = Constants.S3_BASE_URI+File.separator+"uploads"+File.separator+"users"+File.separator+user.getId()+File.separator+user.getPhoto();
				String folderName = Constants.S3_BASE_URI+File.separator+"uploads"+File.separator+"users"+File.separator+user.getId();
		
				
				System.out.println("File to remove file: "+fileName);
				System.out.println("File to remove folderName: "+folderName);
				
				//storageService.delete(user,user.getId() + "_" + user.getPhoto());
				storageService.delete(user,fileName,folderName);

				model.addAttribute("user", user);
			}
			return user.getImagePath();
		}
		
		@GetMapping("/add_property")
		public String addProperty(HttpServletRequest request, HttpServletResponse response) {

			return "admin/add_property";
		}

		@PostMapping("/add_property")
		public String addProperty(@RequestParam("cover") MultipartFile cover,
				@RequestParam("files") MultipartFile[] files,
				HttpServletRequest request, HttpServletResponse response) {

			RequestHandler requestHandler = new RequestHandler();

			Property property = requestHandler.bind(0L, cover, null,files, request, response);
			storageService.store(property, cover,null, files);
			return "admin/add_property";

		}
		
		
		@PostMapping("/update_property")
		public String updateProperty(@RequestParam(value = "cover") MultipartFile cover,
				@RequestParam(value = "video") MultipartFile video,
				@RequestParam(value = "files") MultipartFile[] files, HttpServletRequest request,
				HttpServletResponse response, Model model) {

			RequestHandler requestHandler = new RequestHandler();

			Long id = requestHandler.toLong(request.getParameter("property_id"));

			if (id > 0) {
				Property property = requestHandler.bind(id, cover, video,files, request, response);

				storageService.propertyUpdate(property, cover, files);

				requestHandler.eraseCookie(request, response);

			}

			List<Property> properties = storageService.getProperties();

			model.addAttribute("properties", properties);

			return "redirect:/admin/view_properties";

		}


		@GetMapping("/add_property_detail")
		public String addPropertyDetail() {

			return "admin/admin_dashboard";
		}

		@PostMapping("/add_property_detail")
		public String addPropertyDetail(HttpServletRequest request, HttpServletResponse response) {
			RequestHandler requestHandler = new RequestHandler();

			List<Detail> details = requestHandler.bind(request);

			storageService.store(details);
			return "admin/admin_dashboard";

		}

		
		@GetMapping("/property")
		public String viewUpdateProperties(HttpServletRequest request, Model model) {

			Long propertyId = Long.valueOf((String) request.getParameter("id"));

			Property property = storageService.getPropertyById(propertyId);

			Address address = property.getAddress();
			List<Detail> details = property.getDetails();
			model.addAttribute("property", property);
			model.addAttribute("propertyReference", property.getReference());
			model.addAttribute("address", address);
			model.addAttribute("details", details);
			model.addAttribute("images", property.getImages());

			return "admin/update_property";
		}
		


		@GetMapping("remove_property")
		public String removeProperty(HttpServletRequest request, HttpServletResponse response, Model model) {

			Long id = Long.valueOf((String) request.getParameter("id"));

			if (id > 0) {

				storageService.deleteProperty(id);
			}

			return "redirect:/admin/view_properties";
		}

		@PostMapping("/update_property_detail")
		public String updatePropertyDetail(HttpServletRequest request, HttpServletResponse response, Model model) {
			RequestHandler requestHandler = new RequestHandler();
			List<Detail> details = requestHandler.bind(request);
			storageService.updatePropertyDetails(details);

			List<Property> properties = storageService.getProperties();

			model.addAttribute("properties", properties);

			return "admin/view_properties";

		}

		@GetMapping("remove_detail")
		public String removeDetail(HttpServletRequest request, HttpServletResponse response, Model model) {

			Long propertyId = Long.valueOf((String) request.getParameter("id"));
			Long propertyDetailId = Long.valueOf((String) request.getParameter("detail_id"));

			storageService.deleteDetail(propertyDetailId);

			List<Property> properties = storageService.getProperties();

			model.addAttribute("properties", properties);
			return "redirect:/admin/property?id=" + propertyId;
		}

		// via ajax
		@PostMapping("delete_property_cover")
		@ResponseBody
		public String removePropertyCover(HttpServletRequest request, HttpServletResponse response, Model model) {
			Property property = new Property();
			Long id = Long.valueOf((String) request.getParameter("id"));
			model.addAttribute("property", new Property());
			if (id != null) {
				property = storageService.getPropertyById(id);

				String folderName = Constants.S3_BASE_URI+File.separator+"uploads"+File.separator+"properties";
				
				storageService.delete(property,property.getId() + "_" + property.getCover(),folderName);

				//storageService.delete(property,property.getId() + "_" + property.getCover());
				
				model.addAttribute("property", new Property());
			}

			System.out.println("property: " + property);
			return property.getImagePath();
		}
		// via ajax
		@PostMapping("delete_property_video")
		@ResponseBody
		public String removePropertyVideo(HttpServletRequest request, HttpServletResponse response, Model model) {
			Property property = new Property();
			Long id = Long.valueOf((String) request.getParameter("id"));
			model.addAttribute("property", new Property());
			if (id != null) {
				property = storageService.getPropertyById(id);

				storageService.deleteVideo(property,property.getId() + "_" + property.getVideo());

				model.addAttribute("property", new Property());
			}

			System.out.println("property: " + property);
			return property.getImagePath();
		}
		// via ajax
		@PostMapping("delete_property_image")
		@ResponseBody
		public String removePropertyImage(HttpServletRequest request, HttpServletResponse response, Model model) {
			
			Image image = new Image();
			Long id = Long.valueOf((String) request.getParameter("id"));
			model.addAttribute("image", new Image());
			
			if (id != null) {
				
				image = storageService.findImageById(id);
				
				String folderName = Constants.S3_BASE_URI+File.separator+"uploads"+File.separator+"properties"+File.separator+image.getId();

				storageService.delete(image,image.getId() + "_" + image.getName(),folderName); 
				
				//storageService.delete(image,image.getId() + "_" + image.getName()); 
				
				

				model.addAttribute("image", image);
			}
			return image.getImagePath();
		}
		
		@GetMapping("/home")
		private String back(HttpServletRequest request, HttpServletResponse response,Model model) {
			
			
			return "admin/admin_dashboard";
		}
		
		@GetMapping("/view_properties")
		public String viewProperties(Model model) {

			List<Property> properties = storageService.getProperties();
			List<TUser> allUsers = storageService.getAllUsers();

			model.addAttribute("properties", properties);

			model.addAttribute("users", allUsers);
			return "admin/view_properties";
		}
		
		@GetMapping("store_settings")
		public String storeSettings(HttpServletRequest request, HttpServletResponse response,Model model) {
		
			List<Settings>settings = new ArrayList<>();
			Settings settings1 = new Settings();
			settings1.setName("username");
			settings1.setValue("chskoop@gmail.com");
			
			Settings settings2 = new Settings();
			settings2.setName("password");
			settings2.setValue("vwtyihfxnydgzsuf");
			
			Settings settings3 = new Settings();
			settings3.setName("mail.smtp.host");
			settings3.setValue("smtp.gmail.com");
			
			Settings settings4 = new Settings();
			settings4.setName("mail.smtp.port");
			settings4.setValue("587");
			
			Settings settings5 = new Settings();
			settings5.setName("mail.smtp.auth");
			settings5.setValue("true");
			
			Settings settings6 = new Settings();
			settings6.setName("mail.smtp.starttls.enable");
			settings6.setValue("true");
			settings.add(settings1);
			settings.add(settings2);
			settings.add(settings3);
			settings.add(settings4);
			settings.add(settings5);
			settings.add(settings6);
			storageService.storeSettings(settings);
			
			return "settings";
		}


		
		
}
