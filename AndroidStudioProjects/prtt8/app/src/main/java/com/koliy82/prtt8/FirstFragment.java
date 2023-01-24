package com.koliy82.prtt8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koliy82.prtt8.Recycler.Adapter;
import com.koliy82.prtt8.api.DataBaseHelper;
import com.koliy82.prtt8.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    DataBaseHelper db;
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
        RV.setHasFixedSize(true);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        db = new DataBaseHelper(requireContext());
        RV.setAdapter(new Adapter(db.getAllFood()));
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}