package com.example.ashish.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cart extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent getintent=getIntent();

        String item_id=getintent.getStringExtra("Contents_id");
        Toast.makeText(this,item_id,Toast.LENGTH_SHORT).show();

        DatabaseReference ref = database.getReference("addproduct").child(item_id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("pname").getValue().toString();
                String prices=dataSnapshot.child("price").getValue().toString();

                Log.i("cname",name);
                Log.i("cprice",prices);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
