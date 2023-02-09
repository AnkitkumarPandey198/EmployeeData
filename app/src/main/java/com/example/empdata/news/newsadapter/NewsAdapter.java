package com.example.empdata.news.newsadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.empdata.R;
import com.example.empdata.news.newsmodel.NewsHeadlines;
import com.example.empdata.news.newsview.SelectListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    int targetWidth = 500;
    int targetHeight = 500;

    private Context context;
    private List<NewsHeadlines> headlines;

    private SelectListener listener;

    public NewsAdapter(Context context, List<NewsHeadlines> headlines, SelectListener listener) {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.headline_list_items,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.text_title.setText(headlines.get(position).getTitle());
        holder.text_source.setText(headlines.get(position).getSource().getName());

        if(headlines.get(position).getUrlToImage()!= null){
            Picasso.get().load(headlines.get(position).getUrlToImage()).resize(targetWidth,targetHeight).into(holder.img_headline);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnNewsClicked(headlines.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_title,text_source;
        ImageView img_headline;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.text_title);
            text_source = itemView.findViewById(R.id.text_sources);
            img_headline = itemView.findViewById(R.id.img_headline);
            cardView = itemView.findViewById(R.id.main_container);
        }
    }
}
