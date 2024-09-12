package com.bigdatanyze.ems1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.bigdatanyze.ems1.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

	private FragmentDashboardBinding binding;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = FragmentDashboardBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		// Nothing here anymore

		return view;
	}
}
