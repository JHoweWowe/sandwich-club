package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getName();

    public static Sandwich parseSandwichJson(String json) {

        String mainName = "";
        List<String> alsoKnownAs = new ArrayList<String>();
        String placeOfOrigin = "";
        String description = "";
        String imageURL = "";
        List<String> ingredientsList = new ArrayList<String>();

        //First one must assume that the JSON string may be empty or invalid, so return null if matches with those cases
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            //Perhaps I will need another "for loop" around here...
            JSONObject rootObject = new JSONObject(json);
            JSONObject nameJSONObject = rootObject.getJSONObject("name");
            mainName = nameJSONObject.getString("mainName");

            JSONArray aliasJSONArray = nameJSONObject.getJSONArray("alsoKnownAs");
            for (int i = 0; i < aliasJSONArray.length(); i++) {
                String extraName = aliasJSONArray.getString(i);
                alsoKnownAs.add(extraName);
            }

            //Might need more information within the mainName Object
            placeOfOrigin = rootObject.getString("placeOfOrigin");
            description = rootObject.getString("description");
            imageURL = rootObject.getString("image");

            JSONArray ingredientsJSONArray = rootObject.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsJSONArray.length(); i++) {
                //Check logic
                String ingredient = ingredientsJSONArray.getString(i);
                ingredientsList.add(ingredient);
            }

            Sandwich sandwichObject = new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,imageURL,ingredientsList);
            return sandwichObject;
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Problem obtaining results" + e);
        }

        //Otherwise return nothing
        return null;
    }
}
