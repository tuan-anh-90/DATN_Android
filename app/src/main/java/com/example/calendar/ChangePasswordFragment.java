package com.example.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.calendar.API.APIService;
import com.example.calendar.DTO.ChangePasswordDTO;
import com.example.calendar.R;
import com.example.calendar.Retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePasswordFragment extends Fragment {

    private Long userId; // Đối tượng lưu trữ userId
    private EditText inputNewPassword;
    private EditText inputNewPasswordAgain;
    private Button changePasswordButton;
    private Retrofit retrofit;
    private APIService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        // Khởi tạo RetrofitService và lấy đối tượng Retrofit
        RetrofitService retrofitService = new RetrofitService();
        retrofit = retrofitService.getRetrofit();

        // Khởi tạo APIService
        apiService = retrofit.create(APIService.class);

        inputNewPassword = view.findViewById(R.id.inputNewPassword);
        inputNewPasswordAgain = view.findViewById(R.id.inputNewPasswordAgain);
        changePasswordButton = view.findViewById(R.id.changePasswordButton);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đọc dữ liệu từ EditText
                String newPassword = inputNewPassword.getText().toString();
                String newPasswordAgain = inputNewPasswordAgain.getText().toString();

                // Kiểm tra và thực hiện thay đổi mật khẩu
                if (newPassword.isEmpty() || newPasswordAgain.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(newPasswordAgain)) {
                    Toast.makeText(getContext(), "New passwords do not match.", Toast.LENGTH_SHORT).show();
                } else {
                    // Gọi hàm thay đổi mật khẩu ở đây
                    Log.d("ChangePasswordFragment", "UserID: " + userId); // Kiểm tra giá trị userId
                    changeUserPassword(userId, newPassword);
                }
            }
        });

        return view;
    }

    // Phương thức để truyền userId
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Phương thức để thay đổi mật khẩu người dùng
    private void changeUserPassword(Long userId, String newPassword) {
        // Sử dụng RetrofitService đã tạo và khởi tạo ChangePasswordDTO
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(userId, newPassword);

        Call<ChangePasswordDTO> call = apiService.ChangePassword(userId, changePasswordDTO);
        Log.d("LoginActivity", "ID: " + userId);


        call.enqueue(new Callback<ChangePasswordDTO>() {
            @Override
            public void onResponse(Call<ChangePasswordDTO> call, Response<ChangePasswordDTO> response) {
                if (response.isSuccessful()) {
                    // Xử lý kết quả thành công
                    Toast.makeText(getContext(), "Password changed successfully.", Toast.LENGTH_SHORT).show();
                    // Tùy chọn: Đóng LoginActivity sau khi chuyển màn hình
                    HomeFragment homeFragment = new HomeFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, homeFragment)
                            .commit();
                } else {
                    // Xử lý kết quả không thành công
                    Toast.makeText(getContext(), "Failed to change password.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordDTO> call, Throwable t) {
                // Xử lý khi gọi API không thành công
                Toast.makeText(getContext(), "Failed to change password.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
