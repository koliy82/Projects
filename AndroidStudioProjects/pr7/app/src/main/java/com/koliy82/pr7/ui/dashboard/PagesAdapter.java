package com.koliy82.pr7.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koliy82.pr7.R;
import com.koliy82.pr7.api.APIBUILDER;
import com.koliy82.pr7.api.APIFACE;
import com.koliy82.pr7.models.Film;
import com.koliy82.pr7.models.Item;
import com.koliy82.pr7.models.Top;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;


public class PagesAdapter extends RecyclerView.Adapter<com.koliy82.pr7.ui.dashboard.PagesAdapter.GroupViewHolder> {
    private static ArrayList<Button> list = null;
    public static int selectedBtn = 1;
    static class GroupViewHolder extends RecyclerView.ViewHolder {
    RecyclerView RV;
        Button btn;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btnPages);
        }

        public void bind(Button btn) {
            this.btn.setText(btn.getText());
        }
    }

    APIFACE api;
    public PagesAdapter(Context context, RecyclerView RV) {
        api = APIBUILDER.buildRequest().create(APIFACE.class);
        list = new ArrayList<Button>();
        for (int i = 1; i <= 25; i++) {
            Button button = new Button(context);
            button.setText(String.valueOf(i));
            int page = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    api.getTopList(page).enqueue(new retrofit2.Callback<Top>() {
                        @Override
                        public void onResponse(Call<Top> call, retrofit2.Response<Top> response) {
                            if (response.isSuccessful()) {
                                ArrayList<Film> films = response.body().getFilms();
                                RV.setAdapter(new TopAdapter(films));
                            }
                        }

                        @Override
                        public void onFailure(Call<Top> call, Throwable t) {
                            System.out.println(t.getMessage());
                        }
                    });
                }
            });
            list.add(button);
        }
    }

    @NonNull
    @Override
    public com.koliy82.pr7.ui.dashboard.PagesAdapter.GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View view = inf.inflate(R.layout.pages_item, parent, false);
        return new com.koliy82.pr7.ui.dashboard.PagesAdapter.GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.koliy82.pr7.ui.dashboard.PagesAdapter.GroupViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public int getSelectedBtn() {
        return selectedBtn;
    }
}
