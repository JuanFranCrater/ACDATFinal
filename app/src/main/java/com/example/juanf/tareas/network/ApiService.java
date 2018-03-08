package com.example.juanf.tareas.network;


import com.example.juanf.tareas.model.Email;
import com.example.juanf.tareas.model.Tarea;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ApiService {
    @GET("api/tareas")
    Call<ArrayList<Tarea>> getTareas();

    @GET("acceso/tareas.json")
    Call<ArrayList<Tarea>> getLocalTareas();

    @POST("api/tareas")
    Call<Tarea> createTarea(@Body Tarea tarea);

    @PUT("api/tareas/{id}")
    Call<Tarea> updateTarea(@Body Tarea tarea, @Path("id") int id);

    @DELETE("api/tareas/{id}")
    Call<ResponseBody> deleteTarea(@Path("id") int id);

    @POST("api/email")
    Call<ResponseBody> sendEmail(@Body Email email);
}

