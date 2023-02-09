package com.example.empdata.news.newsview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.empdata.R;
import com.example.empdata.news.newsadapter.NewsAdapter;
import com.example.empdata.news.newsmodel.NewsHeadlines;
import com.example.empdata.news.newsmodel.NewsApiResponse;

import java.util.List;

public class NewsFragment extends Fragment implements SelectListener, View.OnClickListener {
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;

    ProgressDialog dialog;

    Button btn_business,btn_entertainment,btn_general,btn_health,btn_science,btn_sports,btn_technology;

    SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_news, container, false);

        //Dialog box for Fetching the data
        dialog = new ProgressDialog(requireContext());
        dialog.setTitle("Fetching new articles..");
        dialog.show();

        //searchView for getting
        searchView = view.findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetching news article of " + query);
                dialog.show();
                RequestManager manager = new RequestManager(requireContext());
                manager.getNewsHeadlines(listener,"general",null);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        RequestManager manager = new RequestManager(requireContext());
        manager.getNewsHeadlines(listener,"general",null);
        recyclerView = view.findViewById(R.id.recycler_main);

        btn_business = view.findViewById(R.id.btn_business);
        btn_business.setOnClickListener(this);
        btn_entertainment = view.findViewById(R.id.btn_entertainment);
        btn_entertainment.setOnClickListener(this);
        btn_general = view.findViewById(R.id.btn_general);
        btn_general.setOnClickListener(this);
        btn_health = view.findViewById(R.id.btn_health);
        btn_health.setOnClickListener(this);
        btn_science = view.findViewById(R.id.btn_science);
        btn_science.setOnClickListener(this);
        btn_sports = view.findViewById(R.id.btn_sports);
        btn_sports.setOnClickListener(this);
        btn_technology = view.findViewById(R.id.btn_technology);
        btn_technology.setOnClickListener(this);


        return view;
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if(list.isEmpty()){
                Toast.makeText(requireContext(),"No Data Found!!",Toast.LENGTH_LONG).show();
            }else{
            showList(list);
            dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(requireContext(),"An Error Occurred!!",Toast.LENGTH_LONG).show();

        }
    };

    private void showList(List<NewsHeadlines> list) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),1));
        newsAdapter = new NewsAdapter(requireContext(),list,this);
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void OnNewsClicked(NewsHeadlines newsHeadlines) {

       startActivity(new Intent(requireActivity(),DetailsActivity.class).putExtra("data",newsHeadlines));

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        dialog.setTitle("Fetching news article of " + category);
        dialog.show();
        RequestManager manager = new RequestManager(requireContext());
        manager.getNewsHeadlines(listener,category,null);

    }
}