package com.example.guardiannews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * An Recylcer Adapter to display a list of articles in the UI
 */
public class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.ArticleViewHolder> {

    private List<Article> mArticles;
    private OnArticleListener onArticleListener;

    /**
     * Constructor
     * @param articles: list of Articles (data source)
     */
    public ArticleRecyclerAdapter(ArrayList<Article> articles, OnArticleListener onArticleListener) {
        this.mArticles = articles;
        this.onArticleListener = onArticleListener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_item, viewGroup, false);
        return new ArticleViewHolder(view, onArticleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder articleViewHolder, int i) {
        Article currentArticle = mArticles.get(i);
        articleViewHolder.thumbnail.setImageBitmap(currentArticle.getImage());
        articleViewHolder.title.setText(currentArticle.getArticleTitle());

        Log.d("article type is this", currentArticle.getArticleType());

        if ("article".equals(currentArticle.getArticleType()))
            articleViewHolder.mediaType.setImageResource(R.drawable.ic_web_black);
        else {
            articleViewHolder.mediaType.setImageResource(R.drawable.ic_videocam);
        }
        articleViewHolder.pillarName.setText(currentArticle.getPillarName());
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

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumbnail;
        TextView title, pillarName;
        ImageView mediaType;
        OnArticleListener onArticleListener;

        public ArticleViewHolder(@NonNull View itemView, OnArticleListener onArticleListener) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.article_thumbnail);
            title = itemView.findViewById(R.id.article_title);
            mediaType = itemView.findViewById(R.id.article_type);
            pillarName = itemView.findViewById(R.id.article_pillar);
            this.onArticleListener = onArticleListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onArticleListener.onArticleClick(getAdapterPosition(), mArticles.get(getAdapterPosition()));
        }
    }

    public interface OnArticleListener {
        void onArticleClick(int position, Article article);
    }

}
