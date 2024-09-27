package com.bigdatanyze.ems1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bigdatanyze.ems1.model.BusinessProfile;

@Dao
public interface BusinessProfileDao {

	@Insert
	void insert(BusinessProfile businessProfile);

	@Update
	void update(BusinessProfile businessProfile);

	@Delete
	void delete(BusinessProfile businessProfile); // Added delete method

	@Query("SELECT * FROM business_profile LIMIT 1")
	LiveData<BusinessProfile> getBusinessProfile();
}
