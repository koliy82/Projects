package com.koliy82.pr7.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koliy82.pr7.api.APIBUILDER;
import com.koliy82.pr7.api.APIFACE;
import com.koliy82.pr7.databinding.FragmentDashboardBinding;
import com.koliy82.pr7.models.Film;
import com.koliy82.pr7.models.Item;
import com.koliy82.pr7.models.Premier;
import com.koliy82.pr7.models.Top;
import com.koliy82.pr7.ui.home.PremierAdapter;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;

public class DashboardFragment extends Fragment {
    APIFACE api;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        api = APIBUILDER.buildRequest().create(APIFACE.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        var mamaHORIZONTAL = new LinearLayoutManager(requireContext());
        mamaHORIZONTAL.setOrientation(LinearLayoutManager.HORIZONTAL);
        //binding.pages.setLayoutManager(mamaHORIZONTAL);

        binding.RV.setLayoutManager(new LinearLayoutManager(requireContext()));

        api.getTopList(Integer.parseInt(binding.pageText.getText().toString())).enqueue(new retrofit2.Callback<Top>() {
            @Override
            public void onResponse(Call<Top> call, retrofit2.Response<Top> response) {
                if (response.isSuccessful()) {
                    ArrayList<Film> films = response.body().getFilms();
                    binding.RV.setAdapter(new TopAdapter(films));
                    binding.ProgressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Top> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        //binding.pages.setAdapter(new PagesAdapter(requireContext(), binding.RV));

        binding.nextPage.setOnClickListener(v -> {
            if(binding.pageText.getText().equals("25")) return;
            binding.ProgressBar.setVisibility(View.VISIBLE);
            binding.pageText.setText(String.valueOf(Integer.parseInt(binding.pageText.getText().toString()) + 1));
            api.getTopList(Integer.parseInt(binding.pageText.getText().toString())).enqueue(new retrofit2.Callback<Top>() {
                @Override
                public void onResponse(Call<Top> call, retrofit2.Response<Top> response) {
                    if (response.isSuccessful()) {
                        ArrayList<Film> films = response.body().getFilms();
                        binding.RV.setAdapter(new TopAdapter(films));
                        binding.ProgressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<Top> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
        });

        binding.backPage.setOnClickListener(v -> {
            if(binding.pageText.getText().equals("1")) return;
            binding.ProgressBar.setVisibility(View.VISIBLE);
            binding.pageText.setText(String.valueOf(Integer.parseInt(binding.pageText.getText().toString()) - 1));
            api.getTopList(Integer.parseInt(binding.pageText.getText().toString())).enqueue(new retrofit2.Callback<Top>() {
                @Override
                public void onResponse(Call<Top> call, retrofit2.Response<Top> response) {
                    if (response.isSuccessful()) {
                        ArrayList<Film> films = response.body().getFilms();
                        binding.RV.setAdapter(new TopAdapter(films));
                        binding.ProgressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<Top> call, Throwable t) {
                    System.out.println(t.getMessage());
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