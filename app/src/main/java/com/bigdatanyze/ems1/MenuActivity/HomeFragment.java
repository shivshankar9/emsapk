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

public class HomeFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		// Hide toolbar when HomeFragment is visible
		((MainActivity) requireActivity()).hideToolbar();
	}

	@Override
	public void onPause() {
		super.onPause();
		// Show toolbar when leaving HomeFragment
		((MainActivity) requireActivity()).showToolbar();
	}
}
