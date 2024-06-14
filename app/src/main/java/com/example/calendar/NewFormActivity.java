package com.example.calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calendar.API.APIService;
import com.example.calendar.Model.Form;
import com.example.calendar.Retrofit.RetrofitService;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFormActivity extends AppCompatActivity {
    private EditText edtLyDo;
    private TextView tvTuNgay, tvDenNgay;
    private Button btnCreateForm;
    private ProgressBar progressBar;

    private Calendar calendarTuNgay, calendarDenNgay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_form);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Tạo đơn mới");
        }

        edtLyDo = findViewById(R.id.edtLyDo);
        tvTuNgay = findViewById(R.id.tvTuNgay);
        tvDenNgay = findViewById(R.id.tvDenNgay);
        btnCreateForm = findViewById(R.id.btnCreateForm);
        progressBar = findViewById(R.id.progress_bar);

        calendarTuNgay = Calendar.getInstance();
        calendarDenNgay = Calendar.getInstance();

        tvTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker(tvTuNgay, calendarTuNgay);
            }
        });

        tvDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker(tvDenNgay, calendarDenNgay);
            }
        });

        btnCreateForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewForm();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDateTimePicker(final TextView textView, final Calendar calendar) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    calendar.set(year, monthOfYear, dayOfMonth);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                            (view1, hourOfDay, minute) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                textView.setText(String.format("%d-%02d-%02d %02d:%02d:%02d",
                                        year, monthOfYear + 1, dayOfMonth, hourOfDay, minute, 0));
                            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                    timePickerDialog.show();
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void createNewForm() {
        String lyDo = edtLyDo.getText().toString().trim();
        String tuNgay = tvTuNgay.getText().toString().trim();
        String denNgay = tvDenNgay.getText().toString().trim();

        if (lyDo.isEmpty() || tuNgay.isEmpty() || denNgay.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "");

        if (userEmail.isEmpty()) {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        Form newForm = new Form(lyDo, tuNgay, denNgay, "Đang xử lý");

        RetrofitService retrofitService = new RetrofitService();
        APIService apiService = retrofitService.getRetrofit().create(APIService.class);

        Call<Form> call = apiService.createForm(newForm);
        call.enqueue(new Callback<Form>() {
            @Override
            public void onResponse(Call<Form> call, Response<Form> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(NewFormActivity.this, "Form created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e("NewFormActivity", "Response code: " + response.code());
                    Log.e("NewFormActivity", "Response message: " + response.message());
                    try {
                        Log.e("NewFormActivity", "Error body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(NewFormActivity.this, "Failed to create form", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Form> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("NewFormActivity", "Error: " + t.getMessage());
                Toast.makeText(NewFormActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
