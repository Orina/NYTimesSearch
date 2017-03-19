package me.elmira.nytimessearch.articlesearch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.elmira.nytimessearch.R;
import me.elmira.nytimessearch.data.model.ArticleView;

/**
 * Created by elmira on 3/14/17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ArticleView> articles = new ArrayList<>();

    private static final int ARTICLE_ITEM_TYPE = 0;
    private static final int MEDIA_ARTICLE_ITEM_TYPE = 1;

    private OnArticleClickListener mArticleClickListener;

    public interface OnArticleClickListener {
        void onArticleClick(ArticleView articleView);
    }

    public void setArticleClickListener(OnArticleClickListener mArticleClickListener) {
        this.mArticleClickListener = mArticleClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MEDIA_ARTICLE_ITEM_TYPE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media_article, parent, false);
            return new MediaArticleViewHolder(v);
        }
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
            return new ArticleViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ArticleView article = articles.get(position);

        if (getItemViewType(position) == MEDIA_ARTICLE_ITEM_TYPE) {
            ((MediaArticleViewHolder) holder).onBind(article, mArticleClickListener);
        }
        else {
            ((ArticleViewHolder) holder).onBind(article, mArticleClickListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (articles.get(position).isMedia()) {
            return MEDIA_ARTICLE_ITEM_TYPE;
        }
        else return ARTICLE_ITEM_TYPE;
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof MediaArticleViewHolder) {
            Glide.clear(((MediaArticleViewHolder) holder).getImageView());
        }
    }

    public void setArticles(List<ArticleView> newArticles) {
        articles = newArticles == null ? new ArrayList<>() : new ArrayList<>(newArticles);
        notifyDataSetChanged();
    }

    public void addArticles(List<ArticleView> newArticles) {
        if (newArticles == null || newArticles.size() == 0) return;
        if (articles != null && articles.size() > 0) {
            int startPosition = getItemCount();
            articles.addAll(newArticles);
            notifyItemRangeInserted(startPosition, newArticles.size());
        }
        else {
            articles = new ArrayList<>(newArticles);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        articles.clear();
        notifyDataSetChanged();
    }
}