package com.koliy82.pr7.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.koliy82.pr7.api.APIBUILDER;
import com.koliy82.pr7.api.APIFACE;
import com.koliy82.pr7.databinding.FragmentHomeBinding;
import com.koliy82.pr7.models.Item;
import com.koliy82.pr7.models.Premier;
import com.koliy82.pr7.models.Top;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;

public class HomeFragment extends Fragment {

    APIFACE api;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        api = APIBUILDER.buildRequest().create(APIFACE.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.backbtn.setOnClickListener(v -> {
            binding.backbtn.setVisibility(View.INVISIBLE);
            binding.ZV.setVisibility(View.INVISIBLE);
            binding.nadaTak.setVisibility(View.VISIBLE);
        });
        binding.nadaTakButton.setOnClickListener(v -> {
            binding.backbtn.setVisibility(View.VISIBLE);
            var mamaHORIZONTAL = new LinearLayoutManager(requireContext());
            mamaHORIZONTAL.setOrientation(LinearLayoutManager.HORIZONTAL);
            binding.ZV.setLayoutManager(mamaHORIZONTAL);
            binding.PB.setVisibility(View.VISIBLE);
            Call<Premier> getPRFilms = api.getPremierList(Integer.parseInt(binding.year.getText().toString()), binding.month.getText().toString());
            getPRFilms.enqueue(new retrofit2.Callback<Premier>() {
                @Override
                public void onResponse(Call<Premier> call, retrofit2.Response<Premier> response) {
                    if (response.isSuccessful()) {
                        binding.nadaTak.setVisibility(View.INVISIBLE);
                        binding.ZV.setVisibility(View.VISIBLE);
                        ArrayList<Item> items = response.body().getItems();
                        binding.ZV.setAdapter(new PremierAdapter(items));
                        binding.PB.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<Premier> call, Throwable t) {

                }
            });
        });

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}