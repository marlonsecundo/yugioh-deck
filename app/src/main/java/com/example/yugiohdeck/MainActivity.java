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




    }

    @Override
    protected void onStart() {


        deckDAO = new DeckDAO(getApplicationContext());
        cardDAO = new CardDAO(getApplicationContext());
        deckCardDAO = new DeckCardDAO(getApplicationContext());

        super.onStart();

        cardDAO.listar(Arrays.asList(1), result -> {
            List<Card> cards = (List<Card>) result;

            if (cards.size() < 1)
            {

                cardDAO.salvar(new Card(46986414, "Dark Magician", "Normal Monster", "''The ultimate wizard in terms of attack and defense.''", 2500, 2100,7, "Spellcaster", "DARK", "https://storage.googleapis.com/ygoprodeck.com/pics/46986414.jpg", "https://storage.googleapis.com/ygoprodeck.com/pics_small/46986414.jpg"), result1 -> {

                    cardDAO.salvar(new Card(38033121, "Dark Magician Girl the Dragon Knight", "Fusion Monster", "\\\"Dark Magician Girl\\\" + 1 Dragon monster\\r\\nMust be Fusion Summoned with the above Fusion Materials or with \\\"The Eye of Timaeus\\\". Once per turn (Quick Effect): You can send 1 card from your hand to the GY, then target 1 face-up card on the field; destroy that target.", 2600, 1700,7, "Dragon", "DARK", "https://storage.googleapis.com/ygoprodeck.com/pics/43892408.jpg", "https://storage.googleapis.com/ygoprodeck.com/pics_small/43892408.jpg"), result2 -> {

                        cardDAO.salvar(new Card(50725996, "Dark Magician Knight", "Effect Monster", "Cannot be Normal Summoned/Set. Must be Special Summoned with \\\"Knight's Title\\\" and cannot be Special Summoned by other ways. When this card is Special Summoned: Target 1 card on the field; destroy that target.", 2500, 2100,7, "Warrior", "DARK", "https://storage.googleapis.com/ygoprodeck.com/pics/50725996.jpg", "https://storage.googleapis.com/ygoprodeck.com/pics_small/50725996.jpg"), result3 -> {

                        });
                    });
                });



            }
        });


        deckCardDAO.listar(1, result -> {
            List<Deck> decks = (List<Deck>) result;

            if (decks.size() < 1)
            {
                for (int i = 0; i < 5; i++) {
                    deckCardDAO.salvar(new DeckCard(1, 1), result1 -> {
                        deckCardDAO.salvar(new DeckCard(1, 2), result2 -> {

                            deckCardDAO.salvar(new DeckCard(1, 3), result3 -> {

                            });
                        });

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


                deckDAO.salvar(firstDeck, r1 -> {


                    Deck defaultDeck = new Deck();

                    defaultDeck.setName("Deck Padrão");
                    defaultDeck.setDescription("Deck Predefinido");
                    defaultDeck.setId(1);

                    deckDAO.salvar(defaultDeck, r2 -> {});

                });




            }

        });

    }
}