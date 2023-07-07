
package com.place.admin.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.place.AmazonS3Util;
import com.place.Constants;
import com.place.admin.dao.Dao;
import com.place.admin.model.Address;
import com.place.admin.model.Detail;
import com.place.admin.model.Image;
import com.place.admin.model.Property;
import com.place.admin.model.PropertyRequest;
import com.place.admin.model.Settings;
import com.place.admin.model.TUser;
import org.springframework.util.StringUtils;



@Service
public class StorageManager {

	@Autowired
	private Dao dao;
	private final Path root = Paths.get("uploads");
	private final Path usersDir = Paths.get(root+"/users");
	private final Path propertiesDir = Paths.get(root+"/properties");

	public Path createDirectory(Long id) {

		Path directory = Paths.get(root+"/"+id);
		try {

			Files.createDirectories(directory);
		} catch (IOException e) {
			System.out.println("Create directory: "+e.getMessage());
		}
		return directory;
	}

	public Path createDirectory(String folder) {

		Path directory = Paths.get(root+"/"+folder);
		try {

			Files.createDirectories(directory);
		} catch (IOException e)
		{
			System.out.println("Create directory: "+e.getMessage());
		}
		return directory;
	}

	/**
	 * uploads -->properties -->id-->images
	 * eg: uploads -->properties --> 1 -->cover.png
	 * 								   --> images --> img1.png, img2.png
	 * 									     	 
	 * @param id
	 * @return
	 */
	public Map<String,Path> createPropertyDirectory(Long id) {

		Map<String,Path> directories = new HashMap<String,Path>();
		Path identityDir = Paths.get(propertiesDir+"/"+id);
		Path imagesDir = Paths.get(identityDir+"/images");

		try {

			Files.createDirectories(identityDir);
			Files.createDirectories(imagesDir);
			directories.put("propertyDirId", identityDir);
			directories.put("imagesDir", imagesDir);

		} catch (IOException e)
		{
			System.out.println("Create directory: "+e.getMessage());
		}
		return directories;
	}
	/**
	 * uploads -->users -->id
	 * eg: uploads --> users -->id -->img.png
	 * @param id
	 * @return
	 */
	public Map<String,Path> createUserDirectory(
			Long id) {


		Map<String,Path> directories = new HashMap<String,Path>();
		Path userDirectory = Paths.get(usersDir+"/"+id);

		try {

			Files.createDirectories(userDirectory);
			directories.put("userDirectory", userDirectory);

		} catch (IOException e)
		{
			System.out.println("Create directory: "+e.getMessage());
		}
		return directories;
	}



	public void init() {
		try {
			Files.createDirectories(root);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}
	public void save(MultipartFile[] files) {
		try {

			for (int i = 0; i < files.length; i++) {

				MultipartFile file = files[i];
				InputStream inputStream = file.getInputStream();
				Files.copy(inputStream, this.root.resolve(file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
				inputStream.close();
			}

		} catch (Exception e) {
			if (e instanceof FileAlreadyExistsException) {
				throw new RuntimeException("A file of that name already exists.");
			}

			throw new RuntimeException(e.getMessage());
		}
	}

	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			}

		} catch (MalformedURLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;
	}

	public boolean delete(String filename) {
		try {
			Path file = root.resolve(filename);

			return Files.deleteIfExists(file);
		} catch (IOException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}
	
	

	public boolean delete(Object entity, String filename,String folderName) {
		Boolean result = false;
		
	
		try {
			//Path file = root.resolve(filename);
			if (AmazonS3Util.deleteFile(filename)) {
				AmazonS3Util.removeFolder(folderName);
				if (entity instanceof TUser) {
					TUser user = (TUser) entity;
					user.setPhoto(Constants.DEFAULT_USER_IMAGE);
					dao.setDefaultUserPhoto(user);

				} else if (entity instanceof Property) {
					Property property = (Property) entity;
					property.setCover(Constants.DEFAULT_PROPERTY_COVER_IMAGE);
					dao.setDefaultPropertyCover((property));

				} else if (entity instanceof Image) {
					Image image = (Image) entity;
					image.setName(Constants.DEFAULT_PROPERTY_IMAGE);
					dao.setDefaultPropertyImage(image);
				}
				result = true;
			}

		} catch (Exception e) {
			System.out.println("Error: removing file " + e.getMessage());
		}
		return result;
	}
	
	/*
	public boolean delete(Object entity, String filename) {
		Boolean result = false;
		try {
			Path file = root.resolve(filename);
			if (Files.deleteIfExists(file)) {

				if (entity instanceof TUser) {
					TUser user = (TUser) entity;
					user.setPhoto(Constants.DEFAULT_USER_IMAGE);
					dao.setDefaultUserPhoto(user);

				} else if (entity instanceof Property) {
					Property property = (Property) entity;
					property.setCover(Constants.DEFAULT_PROPERTY_COVER_IMAGE);
					dao.setDefaultPropertyCover((property));

				} else if (entity instanceof Image) {
					Image image = (Image) entity;
					image.setName(Constants.DEFAULT_PROPERTY_IMAGE);
					dao.setDefaultPropertyImage(image);
				}
				result = true;
			}

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return result;
	}
	*/

	public boolean deleteVideo(Object entity, String filename) {
		Boolean result = false;
		try {
			Path file = root.resolve(filename);
			if (Files.deleteIfExists(file)) {
				if (entity instanceof Property) {
					Property property = (Property) entity;
					property.setVideo(Constants.DEFAULT_PROPERTY_VIDEO);
					dao.setDefaultPropertyVideo((property));
					result = true;
				} 

			}

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return result;
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}

	// new code
	public void store(TUser user, MultipartFile file) {
		try {

			dbStore(user);

			if (user != null && user.getId() > 0) 
			{
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				String foldeName = "uploads/users/"+user.getId();
				AmazonS3Util.createFolder(foldeName);
				AmazonS3Util.uploadFile(foldeName, fileName, file.getInputStream());
				//local storage
				//fileStore(user.getId(), file,"user");
			}

		} catch (Exception e) {

			System.out.println("store: "+e.getLocalizedMessage());
		}

	}

	public void store(Property property, MultipartFile cover, MultipartFile video, MultipartFile[] files) {
		try {

			dbStore(property);
			
			String separator = "/";

			if (property != null && property.getId() > 0) {
				
				String propertyBaseFolder = "uploads"
						+separator
						+"properties"
						+separator+property.getId();
				
				// create property  base folder
				AmazonS3Util.createFolder(propertyBaseFolder);
				
				// upload property cover
				AmazonS3Util.uploadFile(propertyBaseFolder, StringUtils.cleanPath(cover.getOriginalFilename()), cover.getInputStream());

				// create property images folder 
				String imagesFolder = propertyBaseFolder+separator+"images";
				AmazonS3Util.createFolder(imagesFolder);
				
				for(int i = 0; i < files.length; i++)
				{
					
					String imageName = StringUtils.cleanPath(files[i].getOriginalFilename());
					
					AmazonS3Util.uploadFile(imagesFolder, imageName, files[i].getInputStream());
					
				}
				if(video != null) 
				{
					String fileVideo = StringUtils.cleanPath(video.getOriginalFilename());
					AmazonS3Util.uploadFile(propertyBaseFolder, fileVideo, video.getInputStream());
				}
				//fileStore(property, cover, video, files);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("store: "+e.getLocalizedMessage());
		}
	}


	public void fileStore(Long id, MultipartFile file,String type) {

		Map<String, Path> directoryPool = null;
		Path directory = null;

		if(type.equals("user")) {

			directoryPool = createUserDirectory(id);
			directory = directoryPool.get("userDirectory");

		}
		if(type.equals("Property")) {

			directoryPool = createPropertyDirectory(id);
			directory = directoryPool.get("properties");
		}
		try {

			InputStream inputStream = file.getInputStream();
			Files.copy(inputStream, directory.resolve(file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			inputStream.close();
		} catch (Exception e)
		{
			System.out.println("fileStore: "+e.getMessage());
		}

	}

	public void fileStore(Property property, MultipartFile cover, 
			MultipartFile video, 
			MultipartFile[] files)
					throws Exception {
		InputStream inputStream = cover.getInputStream();
		Map<String, Path> directories = createPropertyDirectory(property.getId());
		Path directory = directories.get("propertyDirId");


		Files.copy(inputStream, directory.resolve(cover.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
		inputStream.close();

		if(video != null && !video.getOriginalFilename().isEmpty()) {

			inputStream = video.getInputStream();

			Files.copy(inputStream,directory.resolve(video.getOriginalFilename()));
			inputStream.close();
		}

		Path dirImages = directories.get("imagesDir");

		for (int i = 0; i < files.length; i++) {

			MultipartFile file = files[i];
			inputStream = file.getInputStream();
			Files.copy(inputStream,
					dirImages.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			inputStream.close();

		}
	}

	private void dbStore(Object entity) {
		if (entity instanceof Property) {

			Property propery = (Property) entity;
			TUser user = dao.findUserByEmail(propery.getTuser().getEmail());

			if (user != null) {
				user.removeProperty(propery);
				propery.setTuser(user);

				Address address = (Address) dao.merge(propery.getAddress());
				propery.setAddress(address);
				dao.store(propery);
			}
		} else if (entity instanceof TUser) {
			TUser user = (TUser) entity;
			Address address = (Address) dao.merge(user.getAddress());
			user.setAddress(address);
			dao.store(user);

		} else {
			dao.store(entity);
		}

	}

	public void store(List<Detail> details) {
		Property product = dao.findPropertyByReference(details.get(0).getReference());
		if (product != null) {
			details.forEach(detail -> {
				detail.setProperty(product);
				dbStore(detail);
			});

		}
	}

	public List<Property> getProperties() {

		return dao.getProperties();

	}

	public List<Property> getLimitedProperties(int limit) {

		return dao.getLimitedProperties(limit);

	}

	public List<Property> pagination(int pageNumber,int limit) {

		return dao.pagination(pageNumber,limit);

	}

	public List<TUser> getAllUsers() {

		return dao.getAllUsers();

	}

	public Property getPropertyById(Long id) {

		return dao.getPropertyById(id);

	}

	public List<Detail> findDetailByPropertyId(Long id, boolean availability) {

		return dao.findDetailByPropertyId(id, availability);

	}

	public List<Property> getRelatedProperties(long id) {

		return dao.getRelatedProperties(id);
	}

	public void propertyUpdate(Property property, MultipartFile cover, MultipartFile[] files) {
		try {

			Property dbProperty = dao.getPropertyById(property.getId());

			if (dao.updateProperty(property) > 0) {
				if (cover != null && !cover.getOriginalFilename().isEmpty()) {
					fileStore(property.getId(), cover,"property");

					//String fileName = dbProperty.getId() + "_" + dbProperty.getCover();
					String fileName = "properties/"+dbProperty.getId() + "/" + dbProperty.getCover();
					delete(fileName);
				}

			}

			dao.updateAddress(property.getAddress());

			property.getImages().forEach(image -> {

				Image oldImage = dao.findImageById(image.getId());

				if (dao.updatePropertyImage(image) > 0) {
					//String fileName = oldImage.getId() + "_" + oldImage.getName();
					String fileName = "properties/"+oldImage.getId() + "/images/" + oldImage.getName();
					System.out.println("file: "+fileName);
					if(fileName != null)
						delete(fileName);

				}

			});

			for (int i = 0; i < property.getImages().size(); i++) {

				String fileName = property.getImages().get(i).getName();

				for (int j = 0; j < files.length; j++) {

					String name = files[j].getOriginalFilename();

					if (fileName.equals(name)) {

						fileStore(property.getImages().get(i).getId(), files[j],"images");
					}

				}

			}

			property.getDetails().forEach(detail -> {

				dao.updatePropertyDetail(detail);

			});

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void updatePropertyDetails(List<Detail> details) {

		details.forEach(detail -> {

			dao.updatePropertyDetail(detail);
		});

	}

	public void deleteDetail(Long id) {
		Detail detail = dao.findDetailById(id);

		if (detail != null) {
			dao.remove(detail);
		}
	}

	public void deleteProperty(Long id) {
		Property property = dao.findPropertyById(id);
		if (property != null) {
			dao.remove(property.getAddress());

			dao.remove(property);
			List<Image> images = property.getImages();
			images.forEach(image -> {

				// unlink images from local folder
				delete(image.getId() + "_" + image.getName());

			});
			// unlink cover from local folder
			delete(property.getId() + "_" + property.getCover());

		}

	}

	public TUser getUserById(Long id) {
		return dao.findUserById(id);

	}

	public void deleteUser(Long id) {
		TUser user = dao.findUserById(id);

		if (user != null) {

			dao.remove(user);
		}

	}

	public Image findImageById(Long id) {
		return dao.findImageById(id);

	}

	public void updateUser(TUser user, MultipartFile file) {

		TUser dbUser = dao.findUserById(user.getId());
		if (dao.updateUser(user) > 0 && !file.getOriginalFilename().isEmpty()) {
			dao.updateAddress(user.getAddress());
			
		
			try {
				
				String fileName = Constants.S3_BASE_URI+ "/uploads/users/"+user.getId()+"/"+StringUtils.cleanPath(file.getOriginalFilename());
				System.out.println("filename:  "+fileName);
				
				String cleanPath = StringUtils.cleanPath(file.getOriginalFilename());
				System.out.println("cleanPath:  "+cleanPath);
				AmazonS3Util.deleteFile(cleanPath);
				
				String foldeName = "uploads/users/"+user.getId(); 
				fileName = StringUtils.cleanPath(file.getOriginalFilename());
				AmazonS3Util.uploadFile(foldeName, fileName, file.getInputStream());
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*
			fileStore(user.getId(), file,"user");

			String fileName = dbUser.getId() + "_" + dbUser.getPhoto();
			// delete previous stored user image
			delete(fileName);
			*/

		}

	}

	public List<Property> search(String keyword, String type, String city, List<String> options) 
	{

		return dao.search(keyword,type,city, options);

	}

	public boolean processRequest(PropertyRequest propertyRequest) {

		Properties settings = getSettings();

		if(sendEmail(settings,propertyRequest)) {
			
			dao.store(propertyRequest);
			
			return true;
		}
		return false;
	}

	public Properties getSettings() {

		Properties properties = new Properties();
		List<Settings> allSettings = dao.getAllSettings();

		for(Settings setting : allSettings) {

			properties.setProperty(setting.getName(), setting.getValue());
		}
		return properties;

	}

	boolean sendEmail(Properties properties,PropertyRequest propertyRequest) {

		SendEmailTLS mail = new SendEmailTLS(properties);
		boolean result = mail.sendMail(propertyRequest.getToEmail(), propertyRequest.getPropertyReference(), propertyRequest.getFromMessage());
		return result;

	}

	public void storeSettings(List<Settings> settings) {

		settings.forEach(setting ->{

			dao.store(setting);
		});

	}


}
