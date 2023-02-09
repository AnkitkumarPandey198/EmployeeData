package com.example.empdata.news.newsview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.empdata.R;
import com.example.empdata.news.newsmodel.NewsHeadlines;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    NewsHeadlines newsHeadlines; //object of the NewsHeadlines java

    TextView txt_title,txt_author,txt_time,txt_details,txt_content;
    ImageView img_news;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        //getting the id of the TextView
        txt_title = findViewById(R.id.text_details_title) ; //title of the news
        txt_author = findViewById(R.id.text_details_author);// author of the news
        txt_time = findViewById(R.id.text_details_time); //times of the news
        txt_details= findViewById(R.id.text_details_details); //details of the news
        txt_content= findViewById(R.id.text_details_content); //content of the news
        img_news = findViewById(R.id.img_head); //image of the news

        //getting data from the NewsHeadlines Class
        newsHeadlines = (NewsHeadlines) getIntent().getSerializableExtra("data");


        //setting data from the NewsHeadlines Class
        txt_title.setText(newsHeadlines.getTitle());
        txt_author.setText(newsHeadlines.getAuthor());
        txt_time.setText(newsHeadlines.getPublishedAt());
        txt_details.setText(newsHeadlines.getDescription());
        txt_content.setText(newsHeadlines.getContent());
        Picasso.get().load(newsHeadlines.getUrlToImage()).into(img_news);
    }

}
