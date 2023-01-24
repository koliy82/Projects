package com.koliy82.pr7.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.koliy82.pr7.api.APIBUILDER;
import com.koliy82.pr7.api.APIFACE;
import com.koliy82.pr7.databinding.RecycleItemActivityBinding;
import com.koliy82.pr7.models.item2.Item;
import com.koliy82.pr7.models.FilmVideos;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecycleItemActivityActivity extends AppCompatActivity {
    private RecycleItemActivityBinding binding;
    APIFACE api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RecycleItemActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = APIBUILDER.buildRequest().create(APIFACE.class);

        Bundle b = getIntent().getExtras();
        int id = b.getInt("id");
        Call<FilmVideos>  videos = api.getVideosByFilmID(id);
        videos.enqueue(new Callback<FilmVideos>() {
            @Override
            public void onResponse(@NonNull Call<FilmVideos> call, @NonNull Response<FilmVideos> response) {
                if (response.isSuccessful())
                {
                    if (response.body() == null) {
                        return;
                    }
                    ArrayList<Item> items = response.body().getItems();
                    AtomicReference<Item> item = new AtomicReference<>(new Item());
                    items.forEach(it -> {
                        if (it.getSite().equals("YOUTUBE"))
                            item.set(it);
                    });
                    if (item.get().getUrl() != null) {
                        getLifecycle().addObserver(binding.vidos);
                        binding.vidos.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                super.onReady(youTubePlayer);
                                String url = item.get().getUrl();
                                String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
                                Pattern compiledPattern = Pattern.compile(pattern);
                                Matcher matcher = compiledPattern.matcher(url); //url is youtube url for which you want to extract the id.
                                if(matcher.find())
                                    youTubePlayer.loadVideo(matcher.group(), 0);
                                else
                                    youTubePlayer.cueVideo("SPtnUW2fbSg", 0);
                            }
                        });
                    }else{
                        binding.vidos.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                super.onReady(youTubePlayer);
                                youTubePlayer.cueVideo("SPtnUW2fbSg", 0);
                            }
                        });
                    }

                }
                else
                {
                    if (response.errorBody() != null) {
                        Toast.makeText(getBaseContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FilmVideos> call, @NonNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

       binding.kinoName.setText( b.getString("name"));
       binding.year.setText( b.getString("year"));
       binding.genders.setText( b.getString("genres"));
       binding.countries.setText( b.getString("countries"));
       binding.rating.setText( b.getString("rating"));
       Picasso.get().load(b.getString("url")).into(binding.imageView);
       setTitle(b.getString("name"));
    }
}