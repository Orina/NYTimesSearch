package me.elmira.nytimessearch.articlesearch;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.elmira.nytimessearch.BR;
import me.elmira.nytimessearch.data.model.ArticleView;

/**
 * Created by elmira on 3/14/17.
 */

public class ArticleViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void onBind(final ArticleView article, final ArticleAdapter.OnArticleClickListener listener) {
        binding.setVariable(BR.article, article);
        binding.executePendingBindings();
        if (listener != null) {
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onArticleClick(article);
                }
            });
        }
    }
}
