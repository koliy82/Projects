package com.koliy82.prtt8.Recycler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.koliy82.prtt8.MainActivity;
import com.koliy82.prtt8.api.DataBaseHelper;
import com.koliy82.prtt8.databinding.RecyclerItemActivityBinding;

public class RecyclerItemActivity extends AppCompatActivity {
    DataBaseHelper db;
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

        db = new DataBaseHelper(this);
        binding.EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateFood(id, binding.itemFoodName.getText().toString(), binding.itemFoodDetail.getText().toString(),  binding.itemStudentFIO.getText().toString(),Integer.parseInt(binding.itemStudentScore.getText().toString()));
                startActivity(new Intent(view.getContext(), MainActivity.class));
                finish();
            }
        });
        binding.DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteFood(id);
                startActivity(new Intent(view.getContext(), MainActivity.class));
                finish();
            }
        });
    }
}