package com.koliy82.prtt8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.koliy82.prtt8.api.DataBaseHelper;
import com.koliy82.prtt8.api.FoodJSON;
import com.koliy82.prtt8.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    DataBaseHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        db = new DataBaseHelper(requireContext());
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
                        binding.studentFIO.getText().toString(),
                        Integer.parseInt(binding.studentScore.getText().toString())
                );
                db.addFood(food);
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}