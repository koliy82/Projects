package com.koliy82.prtt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;
import com.koliy82.prtt.api.ApiInterface;
import com.koliy82.prtt.api.FoodJSON;
import com.koliy82.prtt.api.RequestBuilder;
import com.koliy82.prtt.databinding.FragmentSecondBinding;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    ApiInterface api;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        api = RequestBuilder.buildRequest().create(ApiInterface.class);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.PostAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodJSON food = new FoodJSON(
                        binding.foodName.getText().toString(),
                        binding.foodDetail.getText().toString(),
                        binding.foodImg.getText().toString(),
                        binding.studentFIO.getText().toString(),
                        Integer.parseInt(binding.studentScore.getText().toString())
                );
                Call<FoodJSON> cf = api.createFood(food);
                cf.enqueue(new Callback<FoodJSON>() {
                    @Override
                    public void onResponse(Call<FoodJSON> call, Response<FoodJSON> response) {
                        var code = response.raw().code();
                        if(code >= 100 && code <= 300)
                            NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
                        else binding.ErrorTxt.setText("[CODE " + code + "]");
                    }

                    @Override
                    public void onFailure(Call<FoodJSON> call, Throwable t) {
                        System.out.println("dd");
                    }
                });
            }
        });
    }


//    api = RequestBuilder.buildRequest().create(ApiInterface.class);
//    Call<ArrayList<FoodJSON>> getFoodList = api.getList();
//        getFoodList.enqueue(new Callback<>() {
//        @Override
//        public void onResponse(@NonNull Call<ArrayList<FoodJSON>> call, @NonNull Response<ArrayList<FoodJSON>> response) {
//            if (response.isSuccessful()) {
//                Objects.requireNonNull(response.body()).forEach((it) -> {
//                    System.out.println("ID: " + it.getId() + " Name " + it.getName());
//                });
//            }
//        }
//
//        @Override
//        public void onFailure(@NonNull Call<ArrayList<FoodJSON>> call, @NonNull Throwable t) {
//
//        }
//    });

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}