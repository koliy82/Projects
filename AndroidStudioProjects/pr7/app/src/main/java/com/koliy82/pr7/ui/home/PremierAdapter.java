package com.koliy82.pr7.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koliy82.pr7.R;
import com.koliy82.pr7.models.Item;
import com.koliy82.pr7.ui.RecycleItemActivityActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class PremierAdapter extends RecyclerView.Adapter<PremierAdapter.GroupViewHolder> {
    private static ArrayList<Item> list = null;
    static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView detail;
        ImageView img;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            detail = itemView.findViewById(R.id.detail);
            img = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context con = v.getContext();
                    var item = list.get(getAdapterPosition());
                    Intent intent = new Intent(con, RecycleItemActivityActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("id", item.getKinopoiskId());
                    b.putString("name",item.getNameRu());
                    b.putString("year", Integer.toString(item.getYear()));
                    b.putString("rating","скоро");
                    b.putString("url", item.getPosterUrl());
                    AtomicReference<String> genres = new AtomicReference<>("");
                    item.getGenres().forEach(it -> genres.set( genres + " " + it.getGenre()));
                    b.putString("genres",genres.get());
                    AtomicReference<String> countries = new AtomicReference<>("");
                    item.getCountries().forEach(it -> countries.set( countries + " " + it.getCountry()));
                    b.putString("countries", countries.get());
                    intent.putExtras(b);
                    con.startActivity(intent);
                }
            });
        }

        public void bind(Item pr) {
            name.setText(pr.getNameRu());
            Picasso.get().load(pr.getPosterUrlPreview()).into(img);
            detail.setText(" Кинопоиск ID: "+pr.getKinopoiskId());
            detail.append("\n Год: "+pr.getYear());
            detail.append("\n Гендеры: ");
            pr.getGenres().forEach(genre -> detail.append(genre.getGenre() + " "));
            detail.append("\n Каунтри: ");
            pr.getCountries().forEach(country -> detail.append(country.getCountry() + " "));
        }
    }

    public PremierAdapter(ArrayList<Item> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View view = inf.inflate(R.layout.rv_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
