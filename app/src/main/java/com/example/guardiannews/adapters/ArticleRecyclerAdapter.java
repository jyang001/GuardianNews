package com.example.guardiannews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guardiannews.R;
import com.example.guardiannews.models.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * An Array Adapter to display a list of articles in the UI
 */
public class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.ArticleViewHolder> {

    private List<Article> mArticles;

    /**
     * Constructor
     * @param articles: list of Articles (data source)
     */
    public ArticleRecyclerAdapter(ArrayList<Article> articles) {
        this.mArticles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_item, viewGroup, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder articleViewHolder, int i) {
        Article currentArticle = mArticles.get(i);
        articleViewHolder.thumbnail.setImageBitmap(currentArticle.getImage());
        articleViewHolder.title.setText(currentArticle.getWebTitle());
        articleViewHolder.type.setText(currentArticle.getType());
        articleViewHolder.category.setText(currentArticle.getPillarName());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void refreshLayout(List<Article> articles) {
        ArrayList<Article> newArticles = new ArrayList<>();
        for (Article article: articles) {
            newArticles.add(article);
        }
        mArticles = newArticles;
    }


    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView title, type, category;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.article_thumbnail);
            title = itemView.findViewById(R.id.article_title);
            type = itemView.findViewById(R.id.article_type);
            category = itemView.findViewById(R.id.article_category);
        }
    }

}
