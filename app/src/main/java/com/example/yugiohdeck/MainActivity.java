package com.example.yugiohdeck;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.example.yugiohdeck.dao.CardDAO;
import com.example.yugiohdeck.dao.DeckCardDAO;
import com.example.yugiohdeck.dao.DeckDAO;
import com.example.yugiohdeck.models.Card;
import com.example.yugiohdeck.models.Deck;
import com.example.yugiohdeck.models.DeckCard;
import com.example.yugiohdeck.utils.DAOCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.yugiohdeck.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    DeckDAO deckDAO;
    CardDAO cardDAO;
    DeckCardDAO deckCardDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        deckDAO = new DeckDAO(getApplicationContext());
        cardDAO = new CardDAO(getApplicationContext());
        deckCardDAO = new DeckCardDAO(getApplicationContext());


        cardDAO.listar(Arrays.asList(1), new DAOCallback() {
            @Override
            public void onJobFinish(Object result) {
                List<Card> cards = (List<Card>) result;

                if (cards.size() < 1)
                {
                    cardDAO.salvar(new Card(-1, "", "", "", -1, -1,-1, "", "", "", ""), result1 -> {

                    });
                }
            }
        });


        deckCardDAO.listar(1, new DAOCallback() {
            @Override
            public void onJobFinish(Object result) {

                List<Deck> decks = (List<Deck>) result;

                if (decks.size() < 1)
                {
                    deckCardDAO.salvar(new DeckCard(-1, -1), result1 -> {

                    });
                }
            }
        });






         deckDAO.listar(result -> {
             List<Deck> decks = (List<Deck>) result;

             if (decks.size() < 1)
             {
                 Deck firstDeck = new Deck();

                 firstDeck.setName("Deck Padrão");
                 firstDeck.setDescription("Deck Predefinido");
                 firstDeck.setId(0);


                 deckDAO.salvar(firstDeck, r1 -> {});


                 Deck defaultDeck = new Deck();

                 defaultDeck.setName("Deck Padrão");
                 defaultDeck.setDescription("Deck Predefinido");
                 defaultDeck.setId(1);


                 deckDAO.salvar(defaultDeck, r2 -> {});
             }

         });






    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}