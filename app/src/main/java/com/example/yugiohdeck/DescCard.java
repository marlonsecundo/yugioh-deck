package com.example.yugiohdeck;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yugiohdeck.dao.DeckCardDAO;
import com.example.yugiohdeck.dao.DeckDAO;
import com.example.yugiohdeck.models.Card;
import com.example.yugiohdeck.models.Deck;
import com.example.yugiohdeck.models.DeckCard;
import com.example.yugiohdeck.tasks.DownloadImageTask;
import com.example.yugiohdeck.utils.DeckArrayAdapterHelper;

import java.util.List;

public class DescCard extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    DeckCardDAO deckCard;
    DeckDAO deckDAO;
    List<Deck> decks;

    private Button btnAddToDeck;
    private ImageView imageCard;
    private TextView nameCard;
    private TextView typeCard;
    private TextView descCard;

    private Integer idCard;
    private Integer idDeck;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_card);

        deckDAO = new DeckDAO(getApplicationContext());

        btnAddToDeck = findViewById(R.id.btnAddToDeck);
        imageCard = findViewById(R.id.imageCard);
        nameCard = findViewById(R.id.nameCard);
        typeCard = findViewById(R.id.typeCard);
        descCard = findViewById(R.id.descCard);

        deckDAO.listar(result -> {
            decks = (List<Deck>) result;
        });

        Log.e("deck", "DECK:" + decks);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Card valueCard = (Card) extras.get("card");

            DownloadImageTask task = new DownloadImageTask(imageCard);
            task.execute(valueCard.getSmallImgUrl());

            nameCard.setText(valueCard.getName());
            typeCard.setText(valueCard.getType());
            descCard.setText(valueCard.getDesc());

            idCard = valueCard.getId();
        }

        imageCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        btnAddToDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // deckCard.salvar(new DeckCard(idCard, idDeck), result1 -> {});
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageCard.setImageBitmap(photo);
        }
    }

}
