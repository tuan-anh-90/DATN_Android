package com.example.calendar.API;

import com.example.calendar.DTO.ChangePasswordDTO;
import com.example.calendar.DTO.LoginDTO;
import com.example.calendar.Exception.LoginResponse;
import com.example.calendar.Model.Event;
import com.example.calendar.Model.EventCreateParams;
import com.example.calendar.Model.Form;
import com.example.calendar.Model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @POST("/api/users/signin")
    Call<LoginResponse> loginUser(@Body LoginDTO loginDTO);
    @PUT("/api/users/chagePassword/{id}")
    Call<ChangePasswordDTO> ChangePassword(@Path("id") Long userId, @Body ChangePasswordDTO changePasswordDTO);
    @POST("/api/forms/")
    Call<Form> createForm(@Body Form form);
    @GET("/api/dons/android/allByEmail")
    Call<List<Form>> getAllForms(@Query("email") String email);
    @GET("/api/events")
    Call<List<Event>> getEvents(
            @Query("start") String start,
            @Query("end") String end
    );

    @POST("/api/events/create")
    Call<Event> createEvent(@Body EventCreateParams params);

    @POST("/api/events/delete")
    Call<Void> deleteEvent(@Body EventCreateParams params);

}