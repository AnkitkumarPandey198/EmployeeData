package com.example.empdata.presenter;



import com.example.empdata.model.EmployeeDatabase;
import com.example.empdata.view.HomeFragment;
import com.example.empdata.view.LoginFragment;

public class HomePresenter {
     final HomeFragment mHomeFragment;
    LoginFragment loginFragment = new LoginFragment();
    EmployeeDatabase employeeDatabase = EmployeeDatabase.getInstance(loginFragment.getContext());

    public HomePresenter(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }


    public void onAddEmployeeButtonClicked() {
        mHomeFragment.navigateToAddEmployeeFragment();

    }

    public void  onNewsButtonClicked(){
        mHomeFragment.navigateToNewsFragment();
    }

    public void onShowEmployeeButtonClicked() {
        mHomeFragment.navigateToShowEmployeeFragment();
    }

    public void onLoginButtonClicked() {
        mHomeFragment.navigateToLoginEmployeeFragment();
    }

    public  void OnImagePickerButtonClicked(){mHomeFragment.navigateImagePickerFragment();}

    public  void OnMyDeviceButtonClicked(){mHomeFragment.navigateToBluetoothFragment();}

    public  void OnMoviesButtonClicked(){mHomeFragment.navigateToMoviesFragment();}






}
