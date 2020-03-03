package com.example.guardiannews.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
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
 * An Recycler Adapter to display a list of articles in the UI
 */
public class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.ArticleViewHolder> {

    private List<Article> mArticles;
    private OnArticleListener onArticleListener;
    private String colorTheme;
    private Context context;

    /**
     * Constructor
     * @param articles: list of Articles (data source)
     */
    public ArticleRecyclerAdapter(ArrayList<Article> articles, OnArticleListener onArticleListener, String colorTheme, Context context) {
        this.mArticles = articles;
        this.onArticleListener = onArticleListener;
        this.colorTheme = colorTheme;
        this.context = context;
    }

    public void setColorTheme(String colorTheme) {
        this.colorTheme = colorTheme;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_item_main, viewGroup, false);
        return new ArticleViewHolder(view, onArticleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder articleViewHolder, int i) {
        Article currentArticle = mArticles.get(i);
        articleViewHolder.thumbnail.setImageBitmap(currentArticle.getImage());
        articleViewHolder.title.setText(currentArticle.getArticleTitle());
        articleViewHolder.sectionName.setText(currentArticle.getSectionName());

        if ("article".equals(currentArticle.getArticleType()))
            articleViewHolder.mediaType.setImageResource(R.drawable.ic_web_black);
        else {
            articleViewHolder.mediaType.setImageResource(R.drawable.ic_videocam);
        }
        giveTheme(articleViewHolder);
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void refreshLayout(List<Article> articles) {
        mArticles = articles;
    }

    private void giveTheme(ArticleViewHolder articleViewHolder) {
        if(colorTheme == "Original Theme"){
            //finish
            articleViewHolder.mCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.emeraldGreen));
            articleViewHolder.title.setTextColor(Color.WHITE);
            articleViewHolder.sectionName.setTextColor(Color.WHITE);
        }
        else if(colorTheme == "Standard Theme") {
            articleViewHolder.mCardView.setCardBackgroundColor(Color.WHITE);
            articleViewHolder.title.setTextColor(Color.BLACK);
            articleViewHolder.sectionName.setTextColor(Color.BLACK);
        }
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumbnail;
        TextView title, sectionName;
        ImageView mediaType;
        OnArticleListener onArticleListener;
        CardView mCardView;

        private ArticleViewHolder(@NonNull View itemView, OnArticleListener onArticleListener) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.article_card_view);
            thumbnail = itemView.findViewById(R.id.article_thumbnail);
            title = itemView.findViewById(R.id.article_title);
            mediaType = itemView.findViewById(R.id.article_type);
            sectionName = itemView.findViewById(R.id.article_section);
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
