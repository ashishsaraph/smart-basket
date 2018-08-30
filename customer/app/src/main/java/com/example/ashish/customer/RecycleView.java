package com.example.ashish.customer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RecycleView extends AppCompatActivity {
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    public MoviesAdapter mAdapter;
    String date;
    String price2,pname3;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref_cart,ref_cart2;
    FirebaseUser currentFirebaseUser;

    String name,prices;
    int totalprices=0;
    int auto_id=1;
    String global_content_id;
    String pid;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

         date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        ref_cart = database.getReference("cat_total");

       // getSupportActionBar().setTitle("Add item")
    }




    public void addto_cart(View view) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(RecycleView.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }

    }


    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
               // Intent intent1=new Intent(RecycleView.this,RecycleView.class);
                //intent1.putExtra("Contents_id",contents);
                //startActivity(intent1);
                String local_content_id=contents;
                fetch_details_from_database_and_add_to_cart(contents);
               // fetch_details_from_database_and_add_to_cart(contents);
                //Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                //toast.show();
            }
        }
    }

    private void fetch_details_from_database_and_add_to_cart(String local_content_id) {

        //Toast.makeText(this,local_content_id,Toast.LENGTH_SHORT).show();

        DatabaseReference ref = database.getReference("addproduct").child(local_content_id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    name = dataSnapshot.child("pname").getValue().toString();
                    prices = dataSnapshot.child("price").getValue().toString();
                    pid = dataSnapshot.child("pid").getValue().toString();

                    Log.i("cname", name);
                    Log.i("cprice", prices);


                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

                    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                        @Override
                        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                            // Row is swiped from recycler view
                            // remove it from adapter


                            //for Total Calculations of the price
                            Log.i("cviewholder", String.valueOf(viewHolder));
                            Movie price1 = movieList.get(viewHolder.getAdapterPosition());
                            price2 = price1.getPrice();
                            Log.i("price2", price2);
                            Log.i("pricesub", price2.substring(7));
                            int priceval = Integer.parseInt(price2.substring(7));
                            Log.i("price2int", String.valueOf(priceval));
                            totalprices = totalprices - priceval;
                            Log.i("price2total", String.valueOf(totalprices));
                            movieList.remove(viewHolder.getAdapterPosition());
                            mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());


                        }

                        @Override
                        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                            // view the background view
                        }
                    };

// attaching the touch helper to recycler view
                    new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
                    mAdapter = new MoviesAdapter(movieList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);

                    prepareMovieData();
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "NO PRODUCT FOUND", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void prepareMovieData() {
        totalprices=totalprices+Integer.valueOf(prices);
        Movie movie = new Movie(pid,name, prices );

        //auto_id++;
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
        Log.i("totalprices", String.valueOf(totalprices));
    }

    public void confirmbox(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Item purchased="+String.valueOf(mAdapter.getItemCount())+"and Total price is"+ String.valueOf(totalprices))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.i("totalItem",String.valueOf(mAdapter.getItemCount()));
                        Log.i("totalprice", String.valueOf(totalprices));
                        HashMap map1=new HashMap<String,String>();
                        map1.put("user_id",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        map1.put("total_price",totalprices);
                        map1.put("totalitem",mAdapter.getItemCount());
                        map1.put("product",movieList);

//                        ref_cart2 = database.getReference("detail_cat");
//                        HashMap map2=new HashMap<String,String>();
//                        map2.put("user_id",FirebaseAuth.getInstance().getCurrentUser().getEmail());
//                      map2.put("product",movieList);
//                        ref_cart2.child(date).push().setValue(map2);


                        ref_cart.child(date).push().setValue(map1);
                        Toast.makeText(RecycleView.this, "Thank you for shopping ", Toast.LENGTH_SHORT).show();
//                        Intent i1=new Intent(RecycleView.this,logincustomer.class);
//                        startActivity(i1);

                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("net.one97.paytm");
                        if (launchIntent != null) {
                            startActivity(launchIntent);//null pointer check in case package name was not found
                        }


                    }})
                .setNegativeButton("no", null).show();

    }
}
