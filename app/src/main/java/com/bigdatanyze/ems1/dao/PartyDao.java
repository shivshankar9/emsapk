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

	@Query("SELECT * FROM party ORDER BY name ASC")
	List<Party> getAllParties();

	@Query("SELECT * FROM party WHERE id = :partyId LIMIT 1")
	Party getPartyById(int partyId);

	@Query("SELECT * FROM party WHERE name = :name LIMIT 1")
	Party getPartyByName(String name);
	@Query("SELECT * FROM party ORDER BY name ASC")
	LiveData<List<Party>> getAllPartiesLive();

}
