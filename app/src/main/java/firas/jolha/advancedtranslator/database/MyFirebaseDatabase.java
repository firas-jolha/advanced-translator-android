package firas.jolha.advancedtranslator.database;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseDatabase {

    private FirebaseDatabase firebaseDatabase;
    static final private String TAG = "tag";

    public void writeToDB() {
        HashMap<String, String> data = new HashMap<>();
        data.put("message", "Hello Firebase Database");
        data.put("username", "Firas");
        data.put("password", "a;ldmqwmkdqwd1cke192ce1");
        this.writeToDB(data);
    }
//
//    public static void main(String... args) {
//        new MyFirebaseDatabase().writeToDB();
//    }


    public FirebaseDatabase writeToDB(HashMap<String, String> data) {
//        FirebaseApp.initializeApp(new Activity());

        firebaseDatabase = FirebaseDatabase.getInstance();
        for (Map.Entry<String, String> e : data.entrySet()) {
            DatabaseReference reference = firebaseDatabase.getReference(e.getKey());
            reference.setValue(e.getValue());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                    Log.d(TAG, "Value is: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
        return firebaseDatabase;

    }


}
