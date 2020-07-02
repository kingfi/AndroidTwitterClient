package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {

    public String body;
    public String createdAt;
    public User user;
    public long id;
    public ArrayList<String> urls;

    // empty constructor requited for Parcel
    public Tweet() {}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.urls = new ArrayList<String>();
        try{
            JSONObject entities = jsonObject.getJSONObject("extended_entities");
            JSONArray media = entities.getJSONArray("media");
            ArrayList links = new ArrayList<String>();

            for (int i = 0; i < media.length(); i++) {
                if (media.getJSONObject(i).getString("type").equals("photo")) {
                    links.add(media.getJSONObject(i).getString("media_url_https"));
                }
            }

            tweet.urls = links;

        } catch (JSONException e) {
            Log.e("TWEET", "No media entities");
        }

        return tweet;
    }


    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i ++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

}
