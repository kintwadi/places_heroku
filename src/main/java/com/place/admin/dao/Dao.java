
package com.place.admin.dao;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.apache.bcel.classfile.Module.Open;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.place.admin.model.Address;
import com.place.admin.model.Detail;
import com.place.admin.model.Image;
import com.place.admin.model.Property;
import com.place.admin.model.Settings;
import com.place.admin.model.TUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
public class Dao {
	// ---------------------------------------------------------------------
	// Properties
	// ---------------------------------------------------------------------
	@PersistenceContext
	private EntityManager entityManager;
	// ---------------------------------------------------------------------
	// Construction
	// ---------------------------------------------------------------------

	// ---------------------------------------------------------------------
	// Private Helper Methods
	// ---------------------------------------------------------------------

	// ---------------------------------------------------------------------
	// Public Methods
	// ---------------------------------------------------------------------
	@Transactional
	public void store(Object entity) {

		entityManager.persist(entity);
	}

	@Transactional
	public Object merge(Object entity) {

		return entityManager.merge(entity);
	}

	// to do create standard update SQL
	@Transactional
	public int updateProperty(Property property) {

		Query query = entityManager.createNativeQuery(" UPDATE property " + " SET " + " name = ? , " + " cover = ?, "
				+ " description = ?, " + " area = ?, " + " condition = ?,  " + " overview = ?,  " + " reason = ? , "
				+ " rooms = ?, " + " status = ?, " + " type = ? " + " WHERE id = ?", Property.class);
		query.setParameter(1, property.getName());
		query.setParameter(2, property.getCover());
		query.setParameter(3, property.getDescription());
		query.setParameter(4, property.getArea());
		query.setParameter(5, property.getCondition());
		query.setParameter(6, property.getOverview());
		query.setParameter(7, property.getReason());
		query.setParameter(8, property.getRooms());
		query.setParameter(9, property.getStatus());
		query.setParameter(10, property.getType());
		query.setParameter(11, property.getId());

		return query.executeUpdate();

	}

	@Transactional
	public void updateAddress(Address address) {

		Query query = entityManager.createNativeQuery(
				" UPDATE address  SET " + " city = ?, " + " country = ?, " + " house_number = ?, " + " latitude = ?, "
						+ " longitude = ?," + " street = ?, " + " zipcode = ?  " + " WHERE id = ? ",
				Address.class);
		query.setParameter(1, address.getCity());
		query.setParameter(2, address.getCountry());
		query.setParameter(3, address.getHouseNumber());
		query.setParameter(4, address.getLatitude());
		query.setParameter(5, address.getLongitude());
		query.setParameter(6, address.getStreet());
		query.setParameter(7, address.getZipcode());
		query.setParameter(8, address.getId());
		query.executeUpdate();

	}

	@Transactional
	public int updatePropertyImage(Image image) {

		Query query = entityManager.createNativeQuery(" UPDATE property_image  SET " + " name = ? " + " WHERE id = ?  ",
				Image.class);
		query.setParameter(1, image.getName());
		query.setParameter(2, image.getId());
		return query.executeUpdate();

	}

	@Transactional
	public void updatePropertyDetail(Detail detail) {

		Query query = entityManager.createNativeQuery("UPDATE property_detail  SET " + " section = ?, " + " name = ?,"
				+ " availability = ? " + " WHERE id = ? ", Detail.class);
		query.setParameter(1, detail.getSection());
		query.setParameter(2, detail.getName());
		query.setParameter(3, detail.isAvailability());
		query.setParameter(4, detail.getId());
		query.executeUpdate();

	}

	@Transactional
	public void remove(Object entity) {

		entityManager.remove(entity);
	}

	public TUser findUserByEmail(String email) {
		Query query = entityManager.createNativeQuery("select * from tuser where email = ?", TUser.class);
		query.setParameter(1, email);
		return (TUser) query.getSingleResult();
	}

	public Property findPropertyByReference(String reference) {
		Query query = entityManager.createNativeQuery("select * from property where reference = ?", Property.class);
		query.setParameter(1, reference);
		return (Property) query.getSingleResult();
	}

	public Property findPropertyById(Long id) {
		Query query = entityManager.createNativeQuery("select * from property where id = ?", Property.class);
		query.setParameter(1, id);
		return (Property) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Property> getProperties() {

		Query query = entityManager.createNativeQuery("select * from property", Property.class);
		return (List<Property>) query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Property> getRelatedProperties(long id) {

		Query query = entityManager.createNativeQuery("select * from property where id != ?", Property.class);
		query.setParameter(1, id);
		return (List<Property>) query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Property> getLimitedProperties(int limit) {

		Query query = entityManager.createNativeQuery("select * from property ", Property.class);
		query.setMaxResults(limit);
		return (List<Property>) query.getResultList();

	}

	public Property getPropertyById(Long id) {

		Query query = entityManager.createNativeQuery("select * from property where id = ?", Property.class);
		query.setParameter(1, id);
		return (Property) query.getSingleResult();

	}

	public Image findImageById(Long id) {
		Query query = entityManager.createNativeQuery("select * from property_image where id = ?", Image.class);
		query.setParameter(1, id);
		return (Image) query.getSingleResult();
	}

	public Detail findDetailById(Long id) {
		Query query = entityManager.createNativeQuery("select * from property_detail where id = ?", Detail.class);
		query.setParameter(1, id);
		return (Detail) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Detail> findDetailByPropertyId(Long id, boolean availability) {
		Query query = entityManager.createNativeQuery("select * from property_detail where property_id = ?"
				+ " and availability = ? " + " ORDER BY section ASC ", Detail.class);
		query.setParameter(1, id);
		query.setParameter(2, availability);
		return (List<Detail>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<TUser> getAllUsers() {

		Query query = entityManager.createNativeQuery("select * from tuser", TUser.class);
		return (List<TUser>) query.getResultList();

	}

	public TUser findUserById(Long id) {
		Query query = entityManager.createNativeQuery("select * from tuser where id = ?", TUser.class);
		query.setParameter(1, id);
		return (TUser) query.getSingleResult();
	}

	@Transactional
	public void setDefaultUserPhoto(TUser user) {

		Query query = entityManager.createNativeQuery("UPDATE tuser  SET " + " photo = ? " + " WHERE id = ? ",
				TUser.class);
		query.setParameter(1, user.getPhoto());
		query.setParameter(2, user.getId());
		query.executeUpdate();

	}

	@Transactional
	public void setDefaultPropertyCover(Property property) {
		Query query = entityManager.createNativeQuery("UPDATE property  SET " + " cover = ? " + " WHERE id = ? ",
				Property.class);
		query.setParameter(1, property.getCover());
		query.setParameter(2, property.getId());
		query.executeUpdate();

	}

	@Transactional
	public void setDefaultPropertyVideo(Property property) {
		Query query = entityManager.createNativeQuery("UPDATE property  SET " + " video = ? " + " WHERE id = ? ",
				Property.class);
		query.setParameter(1, property.getVideo());
		query.setParameter(2, property.getId());
		query.executeUpdate();

	}

	@Transactional
	public void setDefaultPropertyImage(Image image) {
		Query query = entityManager.createNativeQuery("UPDATE property_image  SET " + " name = ? " + " WHERE id = ? ",
				TUser.class);
		query.setParameter(1, image.getName());
		query.setParameter(2, image.getId());
		query.executeUpdate();

	}

	@Transactional
	public int updateUser(TUser user) {
		Query query = entityManager.createNativeQuery("UPDATE tuser  SET " + " name = ? ," + " email = ? , "
				+ " phone_number = ?, " + " photo = ? " + " WHERE id = ? ", TUser.class);

		query.setParameter(1, user.getName());
		query.setParameter(2, user.getEmail());
		query.setParameter(3, user.getPhoneNumber());
		query.setParameter(4, user.getPhoto());
		query.setParameter(5, user.getId());

		return query.executeUpdate();

	}

	public List<Property> search(String keyword, String type, String city, List<String> options) {

		StringBuilder optionList = new StringBuilder();
		
	
		for (int i = 0; i < options.size(); i++) {

			
			if(i < options.size()-1 ) 
			{
				optionList.append(options.get(i));
				optionList.append(",");
			}else 
			{
				optionList.append(options.get(i));
			}
		}
		
		//String option = " ( "+optionList.toString() +" ) ";
		
		String sql = " SELECT p.id,p.type, p.area,p.price,p.cover,p.name,p.condition, p.description,p.overview,p.reason, p.reference,p.status FROM property_detail pd " + 
				" INNER JOIN property p on p.id = pd.property_id " + 
				" INNER JOIN address ad on ad.id =  p.address_id " + 
				" WHERE CONCAT(p.name,p.area, p.condition,p.description,p.overview,p.reason ,p.rooms) LIKE ? " + 
				" AND ad.city = ? " + 
				" AND p.type = ? " ; 
				//" OR pd.name in (?) ";
		
		Query query = entityManager.createNativeQuery(sql);
		
		query.setParameter(1, "%"+keyword+"%");
		query.setParameter(2, city);
		query.setParameter(3, type);
		//query.setParameter(4, option);
		
		List<Object[]> list = query.getResultList();
		
		
	for(Object [] obj: list) {
		
		
		System.out.println("id: "+ obj[0]);
		System.out.println("type: "+ obj[1]);
		System.out.println("area: "+ obj[2]);
		System.out.println("price: "+ obj[3]);
		System.out.println("cover: "+ obj[4]);
		System.out.println("name: "+ obj[5]);
		System.out.println("condition: "+ obj[6]);
		System.out.println("description: "+ obj[7]);
		System.out.println("overview: "+ obj[8]);
		System.out.println("reason: "+ obj[9]);
		System.out.println("reference: "+ obj[10]);
		System.out.println("status: "+ obj[11]);
		System.out.println("--------------------");
	}

		return null;
	}

	public List<Property> pagination(int pageNumber, int pageSize) {
		Query query = entityManager.createNativeQuery("select * from property ", Property.class);
		
		System.out.println("pageNumber: "+pageNumber);
		System.out.println("pageSize: "+pageSize);
		
		query.setFirstResult( ( pageNumber - 1 ) * pageSize );
        query.setMaxResults( pageSize);
		return (List<Property>) query.getResultList();
	}

	public List<Settings> getAllSettings() {
		
		Query query = entityManager.createNativeQuery("select * from settings ", Settings.class);
		return (List<Settings>) query.getResultList();
		
	}

}
