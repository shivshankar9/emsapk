package com.bigdatanyze.ems1.MenuActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bigdatanyze.ems1.MainActivity;
import com.bigdatanyze.ems1.R;

public class DashboardFragment extends Fragment {

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the new XML layout for this fragment
		return inflater.inflate(R.layout.fragment_dashboard, container, false);
	}

//	@Override
//	public void onResume() {
//		super.onResume();
//		// Hide toolbar when DashboardFragment is visible
//		// If MainActivity has a method to hide toolbar, call it here
//		((MainActivity) requireActivity()).hideToolbar();
//	}
}
