package com.example.joser.liceoelroble;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import Model.Noticia;

/**
 * Created by Jose Mario on 22/05/2017.
 */

public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaViewModel>
{
    private LayoutInflater _mlayoutInflater;
    private NoticiaListFragment.OnNoticiaListener mListener;
    private ArrayList<Model.Noticia> _noticias;

    public NoticiaAdapter(Context context)
    {
        _mlayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NoticiaViewModel onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new NoticiaViewModel(_mlayoutInflater.inflate(R.layout.recycler_item_noticia, parent, false));
    }

    @Override
    public void onBindViewHolder(NoticiaViewModel holder, int position)
    {
        final String name = _noticias.get(position).get_name();
        final String imageURL = _noticias.get(position).get_newsImageURL();
        holder.setData(imageURL, name);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                mListener.OnNoticiaListenerSelected(imageURL, name, "Descripción");
            }
        });
    }

    @Override
    public int getItemCount()
    {
        if (this._noticias != null)
        {
            return _noticias.size();
        }
        return 0;
    }

    public void setListener(NoticiaListFragment.OnNoticiaListener mListener)
    {
        this.mListener = mListener;
    }

    public void set_noticias(ArrayList<Noticia> _noticias)
    {
        this._noticias = _noticias;
    }
}
