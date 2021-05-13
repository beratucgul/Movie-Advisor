package com.beratemir.movieadvisor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<String> userEmailFromFB;
    ArrayList<String> movieCommentFromFB;
    ArrayList<String> movieNameFromFB;
    ArrayList<String> movieTopicFromFB;
    ArrayList<String> movieTypeFromFB;
    ArrayList<String> userImageFromFB;

    FeedRecyclerAdapter feedRecyclerAdapter;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.upload) {
            Intent intent = new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.signout) {

            firebaseAuth.signOut();

            Intent intent = new Intent(FeedActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed2);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userEmailFromFB = new ArrayList<>();
        userImageFromFB = new ArrayList<>();
        movieCommentFromFB = new ArrayList<>();
        movieNameFromFB = new ArrayList<>();
        movieTopicFromFB = new ArrayList<>();
        movieTypeFromFB = new ArrayList<>();

        getDataFromFirestore();


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        feedRecyclerAdapter = new FeedRecyclerAdapter(userEmailFromFB,movieCommentFromFB,movieNameFromFB,
                movieTypeFromFB,movieTopicFromFB,userImageFromFB);

        recyclerView.setAdapter(feedRecyclerAdapter);
    }

    public void getDataFromFirestore() {

        CollectionReference collectionReference = firebaseFirestore.collection("Posts");

        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error != null) {
                    Toast.makeText(FeedActivity.this, error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

                if(value != null) {

                    for(DocumentSnapshot snapshot : value.getDocuments()) {

                        Map<String, Object> data = snapshot.getData();

                        String mName = "Name: " +(String) data.get("moviename");
                        String mTopic = (String) data.get("movietopic");
                        String mType = "Genre/" +(String) data.get("movietype");
                        String mComment = "\" "+(String) data.get("moviecomment") +" \"";
                        String mEmail = (String) data.get("useremail");
                        String mDownloadUrl = (String) data.get("downloadurl");

                        userEmailFromFB.add(mEmail);
                        userImageFromFB.add(mDownloadUrl);
                        movieCommentFromFB.add(mComment);
                        movieNameFromFB.add(mName);
                        movieTopicFromFB.add(mTopic);
                        movieTypeFromFB.add(mType);

                        feedRecyclerAdapter.notifyDataSetChanged();

                    }

                }

            }
        });



    }
}