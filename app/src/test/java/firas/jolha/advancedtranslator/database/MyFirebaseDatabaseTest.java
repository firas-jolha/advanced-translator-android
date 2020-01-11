package firas.jolha.advancedtranslator.database;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import java.util.HashMap;

public class MyFirebaseDatabaseTest {

    @Test
    public void writeToDB() {
        //        FirebaseDatabase database = FirebaseDatabase.getInstance();
        HashMap<String, String> data = new HashMap<>();
        data.put("message", "Hello Firebase Database");
        data.put("username", "Firas");
        data.put("password", "a;ldmqwmkdqwd1cke192ce1");
        FirebaseDatabase database = new MyFirebaseDatabase().writeToDB(data);
        Log.v("database", database.getApp().getName());
    }
}