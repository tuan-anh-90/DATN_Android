package com.example.calendar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.API.APIService;
import com.example.calendar.Adapter.FormAdapter;
import com.example.calendar.Model.Form;
import com.example.calendar.Retrofit.RetrofitService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {
    private RecyclerView recyclerView;
    private FormAdapter formAdapter;
    private ProgressBar progressBar;
    private Button btnNewForm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        btnNewForm = view.findViewById(R.id.btn_new_form);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadForms();

        btnNewForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewFormActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadForms() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", getContext().MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "");

        if (userEmail.isEmpty()) {
            Toast.makeText(getContext(), "No user logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        RetrofitService retrofitService = new RetrofitService();
        APIService apiService = retrofitService.getRetrofit().create(APIService.class);

        Call<List<Form>> call = apiService.getAllForms(userEmail);
        call.enqueue(new Callback<List<Form>>() {
            @Override
            public void onResponse(Call<List<Form>> call, Response<List<Form>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    formAdapter = new FormAdapter(response.body());
                    recyclerView.setAdapter(formAdapter);
                } else {
                    Log.e("FormFragment", "Response code: " + response.code());
                    Log.e("FormFragment", "Response message: " + response.message());
                    try {
                        Log.e("FormFragment", "Error body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), "Failed to load forms", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Form>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("FormFragment", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
