package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView descriptionTextView;
    private TextView alsoKnownAsTextView;
    private TextView ingredientsTextView;
    private TextView placeOfOriginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        //For testing purposes to show the Sandwich details
        descriptionTextView = findViewById(R.id.description_tv);
        alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        placeOfOriginTextView = findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
    //Important Note: I don't need a SandwichAdapter as the adapter is already shown in the MainActivity as a simplicatino
    private void populateUI(Sandwich sandwich) {

        placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAsTextView.setText(R.string.no_also_known_as);
        }
        else {
            alsoKnownAsTextView.setText(sandwich.getAlsoKnownAs().toString());
        }
        descriptionTextView.setText(sandwich.getDescription());
        ingredientsTextView.setText(sandwich.getIngredients().toString());
    }
}
