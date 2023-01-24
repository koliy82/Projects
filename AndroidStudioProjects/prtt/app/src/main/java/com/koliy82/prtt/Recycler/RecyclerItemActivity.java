package com.koliy82.prtt.Recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.koliy82.prtt.MainActivity;
import com.koliy82.prtt.R;
import com.koliy82.prtt.api.ApiInterface;
import com.koliy82.prtt.api.FoodJSON;
import com.koliy82.prtt.api.RequestBuilder;
import com.koliy82.prtt.databinding.ActivityMainBinding;
import com.koliy82.prtt.databinding.RecyclerItemActivityBinding;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerItemActivity extends AppCompatActivity {
    ApiInterface api;
    private RecyclerItemActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RecyclerItemActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Смотреть на еда");

        Bundle b = getIntent().getExtras();
        int id = b.getInt("id");
        binding.itemFoodName.setText(b.getString("name"));
        binding.itemFoodDetail.setText(b.getString("detail"));
        binding.itemFoodImg.setText(b.getString("img"));
        binding.itemStudentScore.setText(Integer.toString(b.getInt("score")));
        binding.itemStudentFIO.setText(b.getString("student"));

        try{
            Picasso.with(this).load(b.getString("img")).into(binding.imgItem);
        }catch(Exception e){
            binding.imgItem.setImageResource(R.mipmap.ic_launcher);
        }

        api = RequestBuilder.buildRequest().create(ApiInterface.class);
        binding.DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                var response = api.deleteFood(id);
                response.enqueue(new Callback<FoodJSON>() {
                    @Override
                    public void onResponse(Call<FoodJSON> call, Response<FoodJSON> response) {

                    }

                    @Override
                    public void onFailure(Call<FoodJSON> call, Throwable t) {

                    }
                });
                startActivity(new Intent(view.getContext(), MainActivity.class));
                finish();
            }
        });
    }
}