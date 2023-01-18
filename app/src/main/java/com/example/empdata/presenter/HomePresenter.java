package com.example.empdata.presenter;

import com.example.empdata.view.HomeFragment;

public class HomePresenter {
     final HomeFragment mHomeFragment;

    public HomePresenter(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }

    public void onAddEmployeeButtonClicked() {
        mHomeFragment.navigateToAddEmployeeFragment();

    }

    public void onShowEmployeeButtonClicked() {
        mHomeFragment.navigateToShowEmployeeFragment();
    }
}
