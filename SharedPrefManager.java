package com.example.midpos;
import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefManager {
    private static SharedPrefManager minst;
    private static Context mct;

    private static final String SHARD_PERFNAME="myshardperf624";
    private static final String KEY_ID="id";
    private static final String KEY_USERNAME="name";
    private static final String KEY_store="store";
    private SharedPrefManager(Context context){
        mct=context;
    }
    public static synchronized SharedPrefManager getInstans(Context context){
        if (minst==null){
            minst=new SharedPrefManager(context);
        }
        return minst;
    }
    public boolean userLogin(String id,String name){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_ID,id);
        editor.putString(KEY_USERNAME,name);
        editor.apply();
        return true;
    }
    public boolean sotre_id(String store){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_store,store);
        editor.apply();
        return true;
    }
    public boolean isLogin(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_ID,null)!=null){
            return true;
        }
        return false;
    }
    public boolean logout(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }
    public String getUserId(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID,null);

    }
    public String getUsername(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null);

    }

    public String get_store(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_store,null);

    }
}

