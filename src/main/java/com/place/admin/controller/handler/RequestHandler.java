
package com.place.admin.controller.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.place.Constants;
import com.place.admin.model.Address;
import com.place.admin.model.Detail;
import com.place.admin.model.Image;
import com.place.admin.model.Property;
import com.place.admin.model.PropertyRequest;
import com.place.admin.model.TUser;
import com.place.admin.service.StorageManager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestHandler {
	// ---------------------------------------------------------------------
	// Properties
	// ---------------------------------------------------------------------
	// ---------------------------------------------------------------------
	// Construction
	// ---------------------------------------------------------------------

	// ---------------------------------------------------------------------
	// Private Helper Methods
	// ---------------------------------------------------------------------

	// ---------------------------------------------------------------------
	// Public Methods
	// ---------------------------------------------------------------------
	public Double toDouble(String value) {
		try {

			return (value != null && !value.isEmpty()) ? Double.valueOf(value) : (double) 0;

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return (double) 0;

		} // end of exception handling

	}

	public Long toLong(String value) {
		try {
			return (value != null && !value.isEmpty()) ? Long.valueOf(value) : 0L;
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return 0L;

		} // end of exception handling

	}

	public Boolean toBoolean(String value) {
		try {

			return (value != null && !value.isEmpty()) ? Boolean.valueOf(value) : false;

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return false;

		} // end of exception handling

	}

	public Property bind(Long propertyId, MultipartFile file, MultipartFile video, MultipartFile[] files,
			HttpServletRequest request, HttpServletResponse response) {

		Property property = new Property();
		if (propertyId > 0) {
			property.setId(propertyId);
		}
		property.setName(request.getParameter("name"));
		property.setType(request.getParameter("type"));
		property.setStatus(request.getParameter("status"));
		property.setCondition(request.getParameter("condition"));
		property.setReason(request.getParameter("reason"));
		property.setPrice(toDouble(request.getParameter("price")));
		property.setRooms(request.getParameter("rooms"));
		property.setOverview(request.getParameter("overview"));
		property.setDescription(request.getParameter("description"));
		property.setArea(request.getParameter("area"));

		if (file != null && !file.getOriginalFilename().isEmpty()) {
			property.setCover(file.getOriginalFilename());
		} else {
			property.setCover(request.getParameter("cover_name"));
		}
		if (video != null && !video.getOriginalFilename().isEmpty()) {
			property.setVideo(video.getOriginalFilename());

		} else {

			property.setVideo(request.getParameter("video_name"));
		}

		property.setAddress(createAddress(request));

		if (propertyId == 0) {
			property.setReference(generateReference());
			property.setTuser(new TUser(request.getParameter("user_email")));
			addImages(property, files);

		} else {
			if (files != null && !files[0].getOriginalFilename().isEmpty()) {
				List<Image> images = prepareUploadedImages(request, files);
				property.setImages(images);
				property.setImagesToRemove(filesToRemove(request));
			}

		}

		return property;
	}

	List<String> filesToRemove(HttpServletRequest request) {
		List<String> images = new ArrayList<String>();

		Set<String> keySet = request.getParameterMap().keySet();

		keySet.forEach(key -> {

			if (key.contains("_on_update")) {
				images.add(request.getParameter(key));
			}
		});
		return images;
	}

	public List<Detail> bind(HttpServletRequest request) {
		List<Detail> details = new ArrayList<Detail>();

		String reference = request.getParameter(Constants.REFERENCE);

		for (int i = 0; i < request.getParameterMap().size(); i++) {
			Detail detail = new Detail();

			String id = (String) request.getParameter(Constants.INPUT_ID + i);
			String section = (String) request.getParameter(Constants.INPUT_SECTION + i);
			String name = (String) request.getParameter(Constants.INPUT_NAME + i);
			String availability = request.getParameter(Constants.INPUT_AVAILABILITY + i);

			if (section == null && name == null && availability == null) {
				return details;
			}
			if (id != null) {
				if (reference != null) {
					detail.AddReference(reference);
				}
				detail.setId(Long.valueOf(id));
				detail.setSection(section);
				detail.setName(name);
				detail.setAvailability(Boolean.valueOf(availability));
				details.add(detail);
			} else {
				if (reference != null) {
					detail.AddReference(reference);
				}
				detail.setSection(section);
				detail.setName(name);
				detail.setAvailability(Boolean.valueOf(availability));
				details.add(detail);
			}
		}
		return details;
	}

	public TUser bind(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		TUser user = new TUser();

		try {
			String strId = request.getParameter("user_id");
			if (strId != null) {
				Long id = Long.valueOf(strId);
				if (id != null)
					user.setId(id);
			}
			user.setPhoto(file.getOriginalFilename());
			user.setName(request.getParameter("user_name"));
			user.setEmail(request.getParameter("email"));
			user.setPhoneNumber(request.getParameter("phone_number"));
			user.setAddress(createAddress(request));

		} catch (Exception e) {
			e.printStackTrace();
		} // end of exception handling

		return user;

	}

	public Address createAddress(HttpServletRequest request) {
		Address address = new Address();

		Long id = toLong(request.getParameter("address_id"));
		if (id != null)
			address.setId(id);
		address.setLatitude(Double.valueOf(request.getParameter("latitude")));
		address.setLongitude(Double.valueOf(request.getParameter("longitude")));
		address.setCountry(request.getParameter("country"));
		address.setCity(request.getParameter("city"));
		address.setStreet(request.getParameter("street"));
		address.setHouseNumber(request.getParameter("house_number"));
		address.setZipcode(request.getParameter("zipcode"));
		return address;
	}

	private void addImages(Property property, MultipartFile[] files) {
		try {
			List<Image> images = new ArrayList<Image>();
			for (int i = 0; i < files.length; i++) {
				Image image = new Image();
				MultipartFile multipartFile = files[i];
				image.setName(multipartFile.getOriginalFilename());
				images.add(image);
				property.addImage(image);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} // end of exception handling

	}

	public String generateReference() {
		return RandomStringUtils.randomAlphanumeric(8).toUpperCase();

	} // end of createTransactionId

	/**
	 * get stored images name and db ids from cookies and create image object from
	 * real image and assing the correspondent db ids.
	 * 
	 * @param request request to retrieve cookies which contains image name and its
	 *                db ids
	 * @param files   files a multipartFile which contains the real file.
	 * @return image object
	 */
	public List<Image> prepareUploadedImages(HttpServletRequest request, MultipartFile[] files) {
		List<Image> images = new ArrayList<Image>();
		// get image name and id from cookies
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getOriginalFilename();

				for (Cookie cookie : cookies) {
					if (cookie.getValue().equals(fileName)) {
						String name = cookie.getName().replace("img_", "");
						Image image = new Image();
						image.setId(toLong(name));
						image.setName(fileName);
						image.setFile(files[i]);
						images.add(image);
						break;

					}
				}
			}
		}
		return images;
	}

	public void eraseCookie(HttpServletRequest req, HttpServletResponse resp) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if (name.contains("img_")) {

					cookie.setValue("");
					cookie.setPath("/");
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}

			}
	}

	public PropertyRequest bind(HttpServletRequest request, HttpServletResponse response) {
		
		PropertyRequest propertyRequest = new PropertyRequest();
		propertyRequest.setPropertyId(Long.parseLong(request.getParameter("propertyId")));
		propertyRequest.setToEmail(request.getParameter("toEmail"));
		propertyRequest.setPropertyReference(request.getParameter("propertyReference"));
		propertyRequest.setFromEmail(request.getParameter("fromEmail"));
		propertyRequest.setFromPhoneNumber(request.getParameter("fromPhoneNumber"));
		propertyRequest.setFromName(request.getParameter("fromName"));
		propertyRequest.setFromMessage(request.getParameter("fromEmailContent"));
		return propertyRequest;

	}

}
