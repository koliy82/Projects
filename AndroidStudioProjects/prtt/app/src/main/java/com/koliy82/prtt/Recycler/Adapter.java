package com.koliy82.prtt.Recycler;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.annotations.SerializedName;
import com.koliy82.prtt.R;
import com.koliy82.prtt.api.FoodJSON;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.GroupViewHolder> {
    private static ArrayList<FoodJSON>  foodlist = null;
    static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView detail;
        ImageView img;
        TextView student;
        TextView score;
        Context context;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = itemView.findViewById(R.id.name);
            detail = itemView.findViewById(R.id.detail);
            img = itemView.findViewById(R.id.img);
            student = itemView.findViewById(R.id.student);
            score = itemView.findViewById(R.id.score);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context con = v.getContext();
                    var item = foodlist.get(getAdapterPosition());
                    Intent intent = new Intent(con, RecyclerItemActivity.class); //СУда мы передаём все данные кароч
                    Bundle b = new Bundle();
                    b.putInt("id", item.getId());
                    b.putString("name",item.getName());
                    b.putString("detail",item.getDetail());
                    b.putString("img",item.getImg());
                    b.putInt("score", item.getScore());
                    b.putString("student",item.getStudent());
                    intent.putExtras(b);
                    con.startActivity(intent); //Здесь мы запускаем активити кароч
                }
            });
        }

        public void bind(FoodJSON food) {
            name.setText(food.getName());
            detail.setText(food.getDetail());
            try{
                Picasso.with(context).load(food.getImg()).into(img);
            }catch(Exception e){
                img.setImageResource(R.mipmap.ic_launcher);
            }
            student.setText(food.getStudent());
            score.setText(food.getScore().toString());
        }
    }

    public Adapter(FoodList list) {
        this.foodlist = list.list;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View view = inf.inflate(R.layout.food_item, parent, false);

//        view.setOnClickListener(it->{
//            Context con = it.getContext();
//            FoodList lisItem = food.get(getAdapterPosition());
//            Intent intent = new Intent(view.getContext(), RecyclerItemActivity.class); //СУда мы передаём все данные кароч
//            Bundle b = new Bundle();
//            b.putInt("key", ); //Your id
//            intent.putExtras(b);
//            //intent.putExtra("key", "значение"); Kirill
//            con.startActivity(intent); //Здесь мы запускаем активити кароч
//        });



        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.bind(foodlist.get(position));
    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }
}
