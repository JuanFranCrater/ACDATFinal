package com.example.juanf.tareas.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juanf.tareas.MainActivity;
import com.example.juanf.tareas.R;
import com.example.juanf.tareas.adapter.TareasAdapter;
import com.example.juanf.tareas.model.Email;
import com.example.juanf.tareas.model.Tarea;
import com.example.juanf.tareas.network.ApiAdapter;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailActivity extends AppCompatActivity implements View.OnClickListener, Callback<ResponseBody> {
    public static final int OK = 1;

    @BindView(R.id.to)
    EditText to;
    @BindView(R.id.accept)
    Button accept;
    @BindView(R.id.cancel)
    Button cancel;
    ProgressDialog progreso;
    private TareasAdapter adapter;
    ArrayList<Tarea> tareas;
    String m="Copia de Seguridad:\n";
    String t ;
    String s = "Copia de Seguridad";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        ButterKnife.bind(this);
        accept.setOnClickListener(this);
        cancel.setOnClickListener(this);
        adapter = new TareasAdapter();
        Intent i = getIntent();
        progreso = new ProgressDialog(this);
        progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progreso.setMessage("Descargando las Tareas para el envio . . .");
        progreso.setCancelable(false);
        progreso.show();
        Call<ArrayList<Tarea>> call = ApiAdapter.getInstance().getTareas();
        call.enqueue(new Callback<ArrayList<Tarea>>() {
            @Override
            public void onResponse(Call<ArrayList<Tarea>> call, Response<ArrayList<Tarea>> response) {
                progreso.dismiss();
                if (response.isSuccessful()) {
                    tareas = response.body();
                    adapter.setTareas(response.body());
                    showMessage("Tareas descargadas para el envio");
                } else {
                    StringBuilder message = new StringBuilder();
                    message.append("Error en la descarga de las tareas para el envio: " + response.code());
                    accept.setEnabled(false);
                    if (response.body() != null)
                        message.append("\n" + response.body());
                    if (response.errorBody() != null)
                        try {
                            message.append("\n" + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    showMessage(message.toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Tarea>> call, Throwable t) {
                progreso.dismiss();
                if (t != null)
                    showMessage("Fallo en la comunicacion\n" + t.getMessage());
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == accept) {
        send();
        }

        if (v == cancel) {
            finish();
        }
    }

    private void send() {
        t = to.getText().toString();
        if(tareas!=null) {
            for (int i = 0; i < tareas.size(); i++) {
                m+=tareas.get(i).toJson();
            }
            Email email = new Email(t, s, m);
            connection(email);
        }
    }

    private void connection(Email e) {
        progreso = new ProgressDialog(this);
        progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progreso.setMessage("Enviando . . .");
        progreso.setCancelable(false);
        progreso.show();

        Call<ResponseBody> call = ApiAdapter.getInstance().sendEmail(e);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        progreso.dismiss();
        if (response.isSuccessful()) {
            Intent i = new Intent();
            setResult(OK, i);
            finish();
            showMessage("Email enviado");
        } else {
            StringBuilder message = new StringBuilder();
            message.append("Error em el envio del email: " + response.code());
            if (response.body() != null)
                message.append("\n" + response.body());
            if (response.errorBody() != null)
                try {
                    message.append("\n" + response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            showMessage(message.toString());
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        progreso.dismiss();
        if (t != null)
            showMessage("Fallo en la comunicacion\n" + t.getMessage());
    }

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}