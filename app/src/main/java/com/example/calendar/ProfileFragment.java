package com.example.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.calendar.Model.User;

public class ProfileFragment extends Fragment {
    private User loggedInUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        if (getArguments() != null) {
            loggedInUser = (User) getArguments().getSerializable("user");
        }

        if (loggedInUser != null) {
            TextView nameTextView = view.findViewById(R.id.profile_name);
            TextView emailTextView = view.findViewById(R.id.profile_email);
            TextView positionTextView = view.findViewById(R.id.profile_position);
            TextView accountTypeTextView = view.findViewById(R.id.profile_account_type);

            nameTextView.setText(loggedInUser.getName());
            emailTextView.setText(loggedInUser.getEmail());
            positionTextView.setText(loggedInUser.getChucVu());
            accountTypeTextView.setText(loggedInUser.getMaNV());
        }

        return view;
    }
}
