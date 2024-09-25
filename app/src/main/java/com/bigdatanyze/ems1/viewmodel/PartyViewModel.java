package com.bigdatanyze.ems1.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bigdatanyze.ems1.database.AppDatabase;
import com.bigdatanyze.ems1.model.Party;

import java.util.List;

public class PartyViewModel extends AndroidViewModel {
	private final AppDatabase database;
	private final MutableLiveData<Party> partyByNameLiveData = new MutableLiveData<>();
	private final LiveData<List<Party>> allPartiesLiveData;

	public PartyViewModel(Application application) {
		super(application);
		database = AppDatabase.getDatabase(application);
		// Fetch all parties as LiveData
		allPartiesLiveData = database.partyDao().getAllPartiesLive();
	}

	public void insert(Party party) {
		new Thread(() -> database.partyDao().insert(party)).start();
	}

	public LiveData<List<Party>> getAllParties() {
		return allPartiesLiveData;
	}

	public LiveData<Party> getPartyByName(String name) {
		new Thread(() -> {
			Party party = database.partyDao().getPartyByName(name);
			partyByNameLiveData.postValue(party);
		}).start();
		return partyByNameLiveData;
	}
}
