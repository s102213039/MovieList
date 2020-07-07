package com.example.movielist;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movielist.LoaderViewHolder;import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SimpleAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected boolean showLoader;
    private static final int VIEWTYPE_ITEM = 1;
    private static final int VIEWTYPE_LOADER = 2;

    protected LayoutInflater mInflater;

    private List<T> mList = new ArrayList<>();

    public SimpleAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    public SimpleAdapter(Context context, List<T> list) {
        mInflater = LayoutInflater.from(context);
        if (list != null) {
            mList.addAll(list);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEWTYPE_LOADER) {

            // Your Loader XML view here
            View view = mInflater.inflate(R.layout.loader_item_layout, viewGroup, false);

            // Your LoaderViewHolder class
            return new LoaderViewHolder(view);

        } else if (viewType == VIEWTYPE_ITEM) {
            return getYourItemViewHolder(viewGroup);
        }

        throw new IllegalArgumentException("Invalid ViewType: " + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        // Loader ViewHolder
        if (viewHolder instanceof LoaderViewHolder) {
            LoaderViewHolder loaderViewHolder = (LoaderViewHolder) viewHolder;
            if (showLoader) {
                loaderViewHolder.getMProgressBar().setVisibility(View.VISIBLE);
            } else {
                loaderViewHolder.getMProgressBar().setVisibility(View.GONE);
            }

            return;
        }

        bindYourViewHolder(viewHolder, position);

    }


    public List<T> getList() {
        return mList;
    }

    public void addAll(Collection<? extends T> collection) {
        int oldSize = mList.size();
        mList.addAll(collection);
        notifyItemRangeInserted(oldSize, collection.size());
    }

    public void replace(Collection<? extends T> collection) {
        mList.clear();
        mList.addAll(collection);
        notifyDataSetChanged();
    }

    public void add(T item) {
        mList.add(item);
        notifyItemInserted(mList.size() - 1);
    }

    public void addToTop(T item) {
        mList.add(0, item);
        notifyItemInserted(0);
        // 加入如下代码保证position的位置正确性
        if (0 != mList.size() - 1) {
            notifyItemRangeChanged(0, mList.size());
        }
    }

    public void set(int position, T item) {
        mList.set(position, item);
        notifyItemChanged(position);
    }

    public T remove(int position) {
        T item = mList.remove(position);
//        notifyItemRemoved(position);
//        // 加入如下代码保证position的位置正确性
//        if (position != mList.size() - 1) {
//            notifyItemRangeChanged(position, mList.size() - position);
//        }
        notifyDataSetChanged();
        return item;
    }

    public void clear() {
        int oldSize = mList.size();
        mList.clear();
        notifyItemRangeRemoved(0, oldSize);
    }

    public int findPositionById(long id) {
        int count = getItemCount();
        for (int i = 0; i < count; ++i) {
            if (getItemId(i) == id) {
                return i;
            }
        }
        return RecyclerView.NO_POSITION;
    }

    public void notifyItemChangedById(long id) {
        int position = findPositionById(id);
        if (position != RecyclerView.NO_POSITION) {
            notifyItemChanged(position);
        }
    }

    public T removeById(long id) {
        int position = findPositionById(id);
        if (position != RecyclerView.NO_POSITION) {
            return remove(position);
        } else {
            return null;
        }
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {

        // If no items are present, there's no need for loader
        if (mList == null || mList.size() == 0) {
            return 0;
        }

        // +1 for loader
        return mList.size() + 1;
    }

    @Override
    public long getItemId(int position) {

        // loader can't be at position 0
        // loader can only be at the last position
        if (position != 0 && position == getItemCount() - 1) {

            // id of loader is considered as -1 here
            return -1;
        }
        return getYourItemId(position);
    }

    @Override
    public int getItemViewType(int position) {

        // loader can't be at position 0
        // loader can only be at the last position
        if (position != 0 && position == getItemCount() - 1) {
            return VIEWTYPE_LOADER;
        }

        return VIEWTYPE_ITEM;
    }

    public void showLoading(boolean status) {
        showLoader = status;
    }

    public void setItems(List<T> items) {
        mList = items;
    }

    public abstract long getYourItemId(int position);

    public abstract RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent);

    public abstract void bindYourViewHolder(RecyclerView.ViewHolder holder, int position);

}
