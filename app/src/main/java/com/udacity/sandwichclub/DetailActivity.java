package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {


    private TextView   mOriginTv;
    private TextView   mKnownAsLabel;
    private TextView   mIngredientsLabel;
    private TextView   mPlaceOfOriginLabel;
    private TextView   mDescriptionTv;
    private TextView   mIngredientsTv;
    private TextView   mAlsoKnownTv;
    private TextView   mDescriptionLabel;

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownTv = findViewById(R.id.also_known_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);
        mOriginTv = findViewById(R.id.origin_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mPlaceOfOriginLabel = findViewById(R.id.origin_label);
        mKnownAsLabel = findViewById(R.id.also_known_label);
        mDescriptionLabel = findViewById(R.id.description_label);
        mIngredientsLabel = findViewById(R.id.ingredients_label);


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

    private void populateUI(Sandwich sandwich) {
        //display alsoKnownAs TextView
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0){
            StringBuilder builder = new StringBuilder();
            for (String x:sandwich.getAlsoKnownAs() ){
                builder.append(x +", ");
            }
            mAlsoKnownTv.setText(builder);
        }else{
            mKnownAsLabel.setVisibility(View.GONE);
            mAlsoKnownTv.setVisibility(View.GONE);
        }

        //display place of Origin

        if (sandwich.getPlaceOfOrigin().isEmpty()){
            mPlaceOfOriginLabel.setVisibility(View.GONE);
            mOriginTv.setVisibility(View.GONE);
        }else {
            mOriginTv.setText(sandwich.getPlaceOfOrigin());
        }

        //Display description TextView
        if (sandwich.getDescription().isEmpty()){
            mDescriptionLabel.setVisibility(View.GONE);
            mDescriptionTv.setVisibility(View.GONE);
        }else {
            mDescriptionTv.setText(sandwich.getDescription());
        }

        //Display Ingredients
        if(sandwich.getIngredients() != null && sandwich.getIngredients().size() >0){

            StringBuilder builder = new StringBuilder();
            for (String x:sandwich.getIngredients() ){
                builder.append("* " + x +"\n");
            }
            mIngredientsTv.setText(builder);
        }else{
            mIngredientsLabel.setVisibility(View.GONE);
            mIngredientsTv.setVisibility(View.GONE);
        }

    }

}
