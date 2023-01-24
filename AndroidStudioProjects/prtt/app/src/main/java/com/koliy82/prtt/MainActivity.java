package com.koliy82.prtt;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.koliy82.prtt.Recycler.Adapter;
import com.koliy82.prtt.Recycler.FoodList;
import com.koliy82.prtt.Recycler.RecyclerItemActivity;
import com.koliy82.prtt.api.ApiInterface;
import com.koliy82.prtt.api.FoodJSON;
import com.koliy82.prtt.api.RequestBuilder;
import com.koliy82.prtt.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    ApiInterface api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        api = RequestBuilder.buildRequest().create(ApiInterface.class);
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
            Call<ArrayList<FoodJSON>> getFoodList = api.getList();
            getFoodList.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<FoodJSON>> call, @NonNull Response<ArrayList<FoodJSON>> response) {
                    if (response.isSuccessful()) {
                        Objects.requireNonNull(response.body()).forEach((it) -> {
                            var delete = api.deleteFood(it.getId());
                            delete.enqueue(new Callback<FoodJSON>() {
                                @Override
                                public void onResponse(Call<FoodJSON> call, Response<FoodJSON> response) {
                                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<FoodJSON> call, Throwable t) {

                                }
                            });
                        });
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<FoodJSON>> call, @NonNull Throwable t) {

                }
            });
            return true;
        }

        if(id == R.id.settings_add){
            ArrayList<FoodJSON> list = new ArrayList<FoodJSON>();
            list.add(new FoodJSON("Чебупицца", "Чебупицца Горячая штучка Курочка по-итальянски.", "https://img.napolke.ru/image/get?uuid=ea71f5c6-c6b2-4017-a465-6adb4a8d7970&size=370x370","Соколов Николай Николаевич", 9));
            list.add(new FoodJSON("Хотстеры", "Хотстеры ГОРЯЧАЯ ШТУЧКА – это сочные сосиски под тонким слоем ароматного теста.", "https://globus-online.kg/upload/iblock/681/6816ce712a40d5d665cf53d0e158ad5d.png","Соколов Николай Николаевич", 10));
            list.add(new FoodJSON("Чебупели с сыром", "Самое отвратительное что можно купить покушать", "https://cdn-img.perekrestok.ru/i/400x400-fit/xdelivery/files/84/9e/fc3ac50349f8c7f0d1acd92bfb09.jpg","Соколов Николай Николаевич", 1));
            list.add(new FoodJSON("Круггетсы", "Норм тема", "https://storum.ru/image/cache/products/263185-800x800.jpg","Соколов Николай Николаевич", 10));
            list.add(new FoodJSON("Дошик говяжий", "Без комментариев", "https://lenta.servicecdn.ru/globalassets/1/-/05/162/25/258644_3.png?preset=fulllossywhite","Соколов Николай Николаевич", 10));
            list.add(new FoodJSON("Чебудог 👿", "Сочная копчёная сосиска из молодой курочки, отборной свинины и говядины, пикантным томатным соусом Сальса с легкой остринкой.", "https://main-cdn.sbermegamarket.ru/big1/hlr-system/101/465/058/792/119/20/100029281002b0.jpg","Соколов Николай Николаевич", 7));
            list.add(new FoodJSON("Крабовые палочки", "Круто.", "https://sun9-57.userapi.com/impf/DO8WBXLcDk7BhMTzyXNKJYkIKjt9N9n6adWgMw/L4vZq1zjsu0.jpg?size=604x403&quality=96&sign=e1d2636d58fcc60d896aa4c2ddc8bfb9&type=album","Соколов Николай Николаевич", 10));
            list.add(new FoodJSON("Суши", "Самый дефотный сет суши.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAVGlzRjXHpfTjcHIgqqBiE_K_Af9e_s-4kQ&usqp=CAU","Соколов Николай Николаевич", 10));
            list.add(new FoodJSON("Балтика 9", "\"Балтика Крепкое Вишнёвое\" #9 - крепкий пивной напиток со вкусом и ароматом спелой вишни. Сбалансированный вишнёвый вкус имеет умеренную горчинку и даёт приятное согревающее ощущение.", "https://i.ytimg.com/vi/n_8pb5vpUfc/maxresdefault.jpg","Соколов Николай Николаевич", 9));

            list.forEach(((it) -> {
                Call<FoodJSON> cf = api.createFood(it);
                cf.enqueue(new Callback<FoodJSON>() {
                    @Override
                    public void onResponse(Call<FoodJSON> call, Response<FoodJSON> response) {
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<FoodJSON> call, Throwable t) {
                        System.out.println("dd");
                    }
                });
            }));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}