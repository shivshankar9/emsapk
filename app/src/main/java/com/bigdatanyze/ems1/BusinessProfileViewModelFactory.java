//package com.bigdatanyze.ems1;
//
//import android.app.Application;
//import androidx.annotation.NonNull;
//import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.bigdatanyze.ems1.repository.BusinessProfileRepository;
//import com.bigdatanyze.ems1.viewmodel.BusinessProfileViewModel;
//
//public class BusinessProfileViewModelFactory implements ViewModelProvider.Factory {
//	private final Application application;
//
//	public BusinessProfileViewModelFactory(Application application) {
//		this.application = application;
//	}
//
//	@NonNull
//	@Override
//	@SuppressWarnings("unchecked") // To suppress unchecked cast warning
//	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//		if (modelClass.isAssignableFrom(BusinessProfileViewModel.class)) {
//			BusinessProfileRepository repository = new BusinessProfileRepository(application);
//			return (T) new BusinessProfileViewModel(repository);
//		}
//		throw new IllegalArgumentException("Unknown ViewModel class");
//	}
//}
