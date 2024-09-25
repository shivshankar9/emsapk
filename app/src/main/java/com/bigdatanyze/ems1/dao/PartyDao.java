package com.bigdatanyze.ems1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.bigdatanyze.ems1.model.Party;
import java.util.List;

@Dao
public interface PartyDao {

	@Insert
	void insert(Party party);

	// Return List<Party> for non-LiveData method
	@Query("SELECT * FROM party ORDER BY name ASC")
	List<Party> getAllParties();

	// Return a Party by ID
	@Query("SELECT * FROM party WHERE id = :partyId LIMIT 1")
	Party getPartyById(int partyId);

	// Return a Party by name
	@Query("SELECT * FROM party WHERE name = :name LIMIT 1")
	Party getPartyByName(String name);

	// Return LiveData<List<Party>> for observing changes in the Party table
	@Query("SELECT * FROM party ORDER BY name ASC")
	LiveData<List<Party>> getAllPartiesLive();
}
