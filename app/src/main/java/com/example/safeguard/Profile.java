package  com.example.safeguard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Profile extends Fragment {

    private TextView ProfileName,profilePhoneNumber,profileEmail,profileLocation;
    private FirebaseAuth firebaseAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        ProfileName=view.findViewById(R.id.profile_name);
        profilePhoneNumber = view.findViewById(R.id.profile_phone);
        profileEmail = view.findViewById(R.id.profile_email);
        profileLocation = view.findViewById(R.id.profile_location);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final userDataConstructor u =dataSnapshot.getValue(userDataConstructor.class);
                assert u != null;
                final String userName= u.getUserName();
                final String userPhoneNumber= u.getPhoneNumber();
                final String userEmail= u.getEmail();
                final String userLocation= u.getLocation();

                ProfileName.setText(userName);
                profilePhoneNumber.setText(userPhoneNumber);
                profileEmail.setText(userEmail);
                profileLocation.setText(userLocation);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

}
