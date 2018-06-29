package me.jtopete135.flicksmovieapp2;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.jtopete135.flicksmovieapp2.models.Config;
import me.jtopete135.flicksmovieapp2.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.Viewholder>{

    //list of movies
    ArrayList<Movie> movies;
    //config needed for image urls
    Config config;
    Context context;

    //initialize the list
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    //creates and inflates a new view
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //get the the context and create the inflater
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //create the view using the item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie,parent,false);
        //return a new ViewHolder
        return new Viewholder(movieView);
    }

    //binds an inflated view to a new item
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int i) {
        //get the movie data at the specified location
        Movie movie = movies.get(i);
        //populate the view with the movie data
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

        //determine current orientation
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        //build url for poster
        String imageUrl;

        //if in portrait mode, load the poster image
        if(isPortrait){
            imageUrl = config.getImageUrl(config.getPosterSize(),movie.getPosterPath());
        }else{
            imageUrl = config.getImageUrl(config.getBackdropSize(),movie.getBackdropPath());
        }

        //get the correct placeholder
        int placeholderId = isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isPortrait ? holder.ivPosterImage : holder.ivBackdropImage;
        //load image using Glide
        Glide.with(context)
                .load(imageUrl)
                .apply(
                        RequestOptions.placeholderOf(placeholderId)
                        .error(placeholderId)
                        .fitCenter()
                        .transform(new RoundedCornersTransformation(20,0))
                )
                .into(imageView);


    }

    //returns the total number of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    //create the viewholder as a static
    public static class Viewholder extends RecyclerView.ViewHolder {

        //track view objecrts
        ImageView ivPosterImage;
        ImageView ivBackdropImage;
        TextView tvTitle;
        TextView tvOverview;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            // lookup view objects by id
            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPosterImage);
            ivBackdropImage = (ImageView) itemView.findViewById((R.id.ivBackdropImage));
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
}
