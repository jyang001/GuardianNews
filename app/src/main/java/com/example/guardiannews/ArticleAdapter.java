package com.example.guardiannews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * An Array Adapter to display a list of articles in the UI
 */
public class ArticleAdapter extends ArrayAdapter<Article> {

    /**
     * Constructor
     * @param context
     * @param articles: list of Articles (data source)
     */
    public ArticleAdapter(Context context, List<Article> articles) {
        super(context,0, articles);
    }

    /**
     * Displays data at specific position
     * @param position: position in dataset of adapter
     * @param convertView: existing View we can reuse
     * @param parent: parent View
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View articleListItem = convertView;
        if (articleListItem == null) {
            articleListItem = LayoutInflater.from(getContext()).inflate(R.layout.article_item, parent, false);
        }

        Article currentArticle = getItem(position);

        ImageView articleThumbnail = (ImageView) articleListItem.findViewById(R.id.article_thumbnail);
        TextView articleTitle = (TextView) articleListItem.findViewById(R.id.article_title);
        TextView articleType = (TextView) articleListItem.findViewById(R.id.article_type);
        TextView articleCategory = (TextView) articleListItem.findViewById(R.id.article_category);

        articleThumbnail.setImageBitmap(currentArticle.getImage());
        articleTitle.setText(currentArticle.getWebTitle());
        articleType.setText(currentArticle.getType());
        articleCategory.setText(currentArticle.getPillarName());

        return articleListItem;
    }
}
