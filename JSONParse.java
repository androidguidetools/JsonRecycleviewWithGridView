package com.indianbhaskartools.whatsappstatusdownloader;

import android.media.Image;

import com.indianbhaskartools.whatsappstatusdownloader.models.AppModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KP on 2/26/2018.
 */

public class JSONParse {

    //Declare the arrays of fields you require
    public static String[] App_names;
    public static String[] Image_urls;
    public static String[] Package_Name;
    private JSONArray movies = null;


    List<AppModel> Movies ;


    private String json;

    public JSONParse(String json){

        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;

        try {

            movies = new JSONArray(json);


            App_names = new String[movies.length()];
            Image_urls = new String[movies.length()];
            Package_Name = new String[movies.length()];
            Movies = new ArrayList<AppModel>();



            for(int i=0;i< movies.length();i++){
                AppModel movie_object =  new AppModel();

                 jsonObject = movies.getJSONObject(i);

                App_names[i] = jsonObject.getString("app_name");
                Image_urls[i] = jsonObject.getString("app_image_url");
                Package_Name[i] = jsonObject.getString("app_package");

                movie_object.setApp_name(App_names[i]);
                movie_object.setImage_url(Image_urls[i]);
                movie_object.setPackage_name(Package_Name[i]);
                Movies.add(movie_object);



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    List<AppModel> getMovies()
    {
        //function to return the final populated list
        return Movies;
    }


}
