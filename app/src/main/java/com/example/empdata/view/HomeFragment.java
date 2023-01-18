package com.example.empdata.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.empdata.R;
import com.example.empdata.presenter.HomePresenter;

public class HomeFragment extends Fragment {

    HomePresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mPresenter = new HomePresenter(this);
        Button mAddEmployeeButton = view.findViewById(R.id.addEmployee);
        Button mShowEmployeeButton = view.findViewById(R.id.showEmployee);

        mAddEmployeeButton.setOnClickListener(v -> mPresenter.onAddEmployeeButtonClicked());

        mShowEmployeeButton.setOnClickListener(v -> mPresenter.onShowEmployeeButtonClicked());
        return view;
    }

    public void navigateToAddEmployeeFragment(){
        AddEmployeeFragment mFragment = new AddEmployeeFragment();
        mFragment.setArguments(new Bundle());
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,mFragment).addToBackStack("name").commit();
    }

    public void navigateToShowEmployeeFragment(){
        ShowEmployeeFragment mFragment = new ShowEmployeeFragment();
        mFragment.setArguments(new Bundle());
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,mFragment).addToBackStack("name").commit();
    }


}