package com.koliy82.prtt;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.koliy82.prtt.Recycler.Adapter;
import com.koliy82.prtt.Recycler.FoodList;
import com.koliy82.prtt.api.ApiInterface;
import com.koliy82.prtt.api.FoodJSON;
import com.koliy82.prtt.api.RequestBuilder;
import com.koliy82.prtt.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    ApiInterface api;
    RecyclerView RV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RV = binding.RecycleV;
        RV.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        api = RequestBuilder.buildRequest().create(ApiInterface.class);
        Call<ArrayList<FoodJSON>> getFoodList = api.getList();
        getFoodList.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<FoodJSON>> call, @NonNull Response<ArrayList<FoodJSON>> response) {
                if (response.isSuccessful()) {
                    FoodList list = new FoodList();
                    Objects.requireNonNull(response.body()).forEach((it) -> {
                        list.add(it.getId(),it.getName(), it.getDetail(), it.getImg(), it.getStudent(), it.getScore());
                        //System.out.println("ID: " + it.getId() + " Name " + it.getName());
                    });
                    RV.setAdapter(new Adapter(list));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<FoodJSON>> call, @NonNull Throwable t) {

            }
        });
    }



        @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}