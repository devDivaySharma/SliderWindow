package com.example.div.slider;


import android.content.Context;
import android.content.SharedPreferences;

public class PrefrenceManager {

    private Context context;
    private SharedPreferences sharedPreferences;

    public PrefrenceManager(Context context){
        this.context=context;
        getSharedPrefrence();
    }


    private void getSharedPrefrence(){
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.my_prefrence),Context.MODE_PRIVATE);
    }

    public void writePrefrence(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.my_prefrence_key),"INIT_OK");
        editor.commit();
    }

    public boolean checkPrefrence(){

        boolean status = false;

        if(sharedPreferences.getString(context.getString(R.string.my_prefrence_key),"null").equals("null")){
            status = false;
        }else{
            status = true;
        }

        return status;
    }

    public void clearPrefreence(){
        sharedPreferences.edit().clear().commit();
    }
}
