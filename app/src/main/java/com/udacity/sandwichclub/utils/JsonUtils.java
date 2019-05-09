package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwich = new JSONObject(json);
            JSONObject name = sandwich.getJSONObject("name");
            String main_name = name.getString("mainName");
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAsList = fromJSONArrayToList(alsoKnownAs);
            String placeOfOrigin = sandwich.getString("placeOfOrigin");
            String description = sandwich.getString("description");
            String image = sandwich.getString("image");
            JSONArray ingredients = sandwich.getJSONArray("ingredients");
            List<String> ingredientsList = fromJSONArrayToList(ingredients);

            return new Sandwich(main_name, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList );

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
    private static <array> List<String> fromJSONArrayToList (final JSONArray array) throws JSONException {
        final List<String> list = new ArrayList<String>(array.length()); {
            if(array != null){
               for (int i=0; i <array.length(); i++){
                   list.add(array.getString(i));
               }
            }

        }

        return list;
    }
}
