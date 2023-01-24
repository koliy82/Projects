package com.koliy82.prtt8;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.koliy82.prtt8.api.DataBaseHelper;
import com.koliy82.prtt8.api.FoodJSON;
import com.koliy82.prtt8.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        db = new DataBaseHelper(this);
        System.out.println("MainActivity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings_delete) {
            db.deleteAllFood();
            System.out.println("Delete");
        }

        if(id == R.id.settings_add){
            ArrayList<FoodJSON> list = new ArrayList<FoodJSON>();
            list.add(new FoodJSON("Чебупицца", "Чебупицца Горячая штучка Курочка по-итальянски.", "Соколов Николай Николаевич", 9));
            list.add(new FoodJSON("Хотстеры", "Хотстеры ГОРЯЧАЯ ШТУЧКА – это сочные сосиски под тонким слоем ароматного теста.", "Соколов Николай Николаевич", 10));
            list.add(new FoodJSON("Чебупели с сыром", "Самое отвратительное что можно купить покушать", "Соколов Николай Николаевич", 1));
            list.add(new FoodJSON("Круггетсы", "Норм тема", "Соколов Николай Николаевич", 10));
            list.add(new FoodJSON("Дошик говяжий", "Без комментариев", "Соколов Николай Николаевич", 10));
            list.add(new FoodJSON("Чебудог 👿", "Сочная копчёная сосиска из молодой курочки, отборной свинины и говядины, пикантным томатным соусом Сальса с легкой остринкой.", "Соколов Николай Николаевич", 7));
            list.add(new FoodJSON("Крабовые палочки", "Круто.", "Соколов Николай Николаевич", 10));
            list.add(new FoodJSON("Суши", "Самый дефотный сет суши.", "Соколов Николай Николаевич", 10));
            list.add(new FoodJSON("Балтика 9", "\"Балтика Крепкое Вишнёвое\" #9 - крепкий пивной напиток со вкусом и ароматом спелой вишни. Сбалансированный вишнёвый вкус имеет умеренную горчинку и даёт приятное согревающее ощущение.", "Соколов Николай Николаевич", 9));

            list.forEach(((it) -> {
                db.addFood(it);
                System.out.println("Add");
            }));
        }
        startActivity(new Intent(MainActivity.this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}