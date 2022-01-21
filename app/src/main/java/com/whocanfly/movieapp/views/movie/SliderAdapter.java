package com.whocanfly.movieapp.views.movie;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.whocanfly.movieapp.R;
import com.whocanfly.movieapp.api.Movie;
import com.whocanfly.movieapp.databinding.CardMovieWideBinding;

import java.util.ArrayList;
import java.util.List;
public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<Movie> mSliderItems = new ArrayList<>();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<Movie> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Movie sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    public void addList(List<Movie> list)
    {
        this.mSliderItems=list;
        notifyDataSetChanged();
    }
    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie_wide, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        Movie sliderItem = mSliderItems.get(position);


        Glide.with(viewHolder.binding.getRoot())
                .load(sliderItem.getCompleteImageUrl())
                .fitCenter()
                .into(viewHolder.binding.movieImgWide);
        viewHolder.binding.tvMovieTitleWide.setText(sliderItem.getTitle() );
        viewHolder.binding.tvReleaseDateWide.setText("( "+sliderItem.getReleaseDate()+" )");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        CardMovieWideBinding binding;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            binding=CardMovieWideBinding.bind(itemView);
        }
    }

}