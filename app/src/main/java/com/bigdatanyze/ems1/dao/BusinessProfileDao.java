// BusinessProfileDao.java
package com.bigdatanyze.ems1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.bigdatanyze.ems1.model.BusinessProfile;

@Dao
public interface BusinessProfileDao {
	@Insert
	void insert(BusinessProfile profile);

	@Query("SELECT * FROM business_profile LIMIT 1")
	LiveData<BusinessProfile> getBusinessProfile();
}
