package com.smilesifat.eselfredeemer.drawerMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.smilesifat.eselfredeemer.firebaseViewHolder.FirebaseAmbulancesViewHolder;
import com.smilesifat.eselfredeemer.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import com.smilesifat.eselfredeemer.constractor.addAmbulancesConstructor;

public class AmbulancesActivity extends AppCompatActivity {

    private FirebaseRecyclerOptions<addAmbulancesConstructor> options;
    private FirebaseRecyclerAdapter<addAmbulancesConstructor, FirebaseAmbulancesViewHolder> adapter;

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulances);

        Toolbar toolbar = findViewById(R.id.add_ambulances_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        RecyclerView recyclerView = findViewById(R.id.ambulances_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference databaseAmbulancesReference = FirebaseDatabase.getInstance().getReference().child("Ambulance List");
        databaseAmbulancesReference.keepSynced(true);
        options=new FirebaseRecyclerOptions.Builder<addAmbulancesConstructor>().setQuery(databaseAmbulancesReference,addAmbulancesConstructor.class).build();

        adapter=new FirebaseRecyclerAdapter<addAmbulancesConstructor, FirebaseAmbulancesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseAmbulancesViewHolder firebaseAmbulancesViewHolder, int i, @NonNull final addAmbulancesConstructor addAmbulancesConstructor) {
                firebaseAmbulancesViewHolder.AmbulancesListName.setText(addAmbulancesConstructor.getAmbulanceName());
                firebaseAmbulancesViewHolder.AmbulancesListPhoneNumber.setText(addAmbulancesConstructor.getAmbulancePhoneNumber());
                firebaseAmbulancesViewHolder.AmbulancesListEmail.setText(addAmbulancesConstructor.getAmbulanceEmail());
                firebaseAmbulancesViewHolder.AmbulancesListType.setText(addAmbulancesConstructor.getAmbulanceType());
                firebaseAmbulancesViewHolder.AmbulancesListLocation.setText(addAmbulancesConstructor.getAmbulanceLocation());

                firebaseAmbulancesViewHolder.AmbulancesListCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String visitorPhoneNumber= addAmbulancesConstructor.getAmbulancePhoneNumber();
                        String call="tel:"+visitorPhoneNumber.trim();
                        Intent callIntent=new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse(call));
                        startActivity(callIntent);
                    }
                });
            }
            @NonNull
            @Override
            public FirebaseAmbulancesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseAmbulancesViewHolder(LayoutInflater.from(AmbulancesActivity.this).inflate(R.layout.list_cardview_ambulances_recyclerview,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
