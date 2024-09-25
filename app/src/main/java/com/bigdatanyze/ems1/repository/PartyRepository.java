package com.bigdatanyze.ems1.repository;

import androidx.lifecycle.LiveData;

import com.bigdatanyze.ems1.dao.PartyDao;
import com.bigdatanyze.ems1.model.Party;

import java.util.List;

public class PartyRepository {
	private PartyDao partyDao;
	private LiveData<List<Party>> allParties;

	public PartyRepository(PartyDao partyDao) {
		this.partyDao = partyDao;
		allParties = (LiveData<List<Party>>) partyDao.getAllParties();
	}

	public LiveData<List<Party>> getAllParties() {
		return allParties;
	}

	public Party getPartyByName(String name) {
		return partyDao.getPartyByName(name);
	}

	public void insert(Party party) {
		new Thread(() -> partyDao.insert(party)).start();
	}
}
