package com.beratemir.movieadvisor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder> {

    private ArrayList<String> userEmailList;
    private ArrayList<String> movieCommentList;
    private ArrayList<String> movieNameList;
    private ArrayList<String> movieTypeList;
    private ArrayList<String> movieTopicList;
    private ArrayList<String> userImageList;

    public FeedRecyclerAdapter(ArrayList<String> userEmailList, ArrayList<String> movieCommentList,
                               ArrayList<String> movieNameList, ArrayList<String> movieTypeList,
                               ArrayList<String> movieTopicList, ArrayList<String> userImageList) {
        this.userEmailList = userEmailList;
        this.movieCommentList = movieCommentList;
        this.movieNameList = movieNameList;
        this.movieTypeList = movieTypeList;
        this.movieTopicList = movieTopicList;
        this.userImageList = userImageList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedRecyclerAdapter.PostHolder holder, int position) {

        holder.userEmailText.setText(userEmailList.get(position));
        holder.movieCommentText.setText(movieCommentList.get(position));
        holder.movieNameText.setText(movieNameList.get(position));
        holder.movieTypeText.setText(movieTypeList.get(position));
        holder.movieTopicText.setText(movieTopicList.get(position));
        Picasso.get().load(userImageList.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return userEmailList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView userEmailText;
        TextView movieCommentText;
        TextView movieNameText;
        TextView movieTypeText;
        TextView movieTopicText;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recyclerview_row_imageview);
            userEmailText = itemView.findViewById(R.id.recyclerview_row_useremail_text);
            movieCommentText = itemView.findViewById(R.id.recyclerview_row_moviecomment);
            movieNameText = itemView.findViewById(R.id.recyclerview_row_moviename);
            movieTypeText = itemView.findViewById(R.id.recyclerview_row_movietype);
            movieTopicText = itemView.findViewById(R.id.recyclerview_row_movietopic);

        }
    }


}
