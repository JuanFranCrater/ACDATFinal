package com.example.juanf.tareas.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juanf.tareas.R;
import com.example.juanf.tareas.model.Tarea;
import com.example.juanf.tareas.network.ApiAdapter;

import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener, Callback<Tarea> {
    public static final int OK = 1;

    @BindView(R.id.nameTarea)
    EditText nameTarea;
    @BindView(R.id.descriptionTarea)
    EditText descriptionTarea;
    @BindView(R.id.spnImportancia)
    Spinner spinImportancia;
    @BindView(R.id.dateDisplay)
    TextView dateDisplay;
    @BindView(R.id.linkTarea)
    EditText linkTarea;
    @BindView(R.id.imagenTarea)
    EditText imagenTarea;
    @BindView(R.id.pickDate)
    Button pickDate;
    @BindView(R.id.accept)
    Button accept;
    @BindView(R.id.cancel)
    Button cancel;
    ProgressDialog progreso;
    Tarea tarea;
    Calendar c=Calendar.getInstance();
    int  mYear = c.get(Calendar.YEAR);
    int  mMonth = c.get(Calendar.MONTH);
    int  mDay = c.get(Calendar.DAY_OF_MONTH);
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
    private void updateDisplay() {
        dateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mDay).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mYear).append(" "));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ButterKnife.bind(this);
        accept.setOnClickListener(this);
        cancel.setOnClickListener(this);

        Intent i = getIntent();
        tarea = (Tarea) i.getSerializableExtra("tarea");
        nameTarea.setText(tarea.getName());
        linkTarea.setText(tarea.getLink());
        descriptionTarea.setText(tarea.getDescription());
        spinImportancia.setSelection(tarea.getImportancia()-1);
        dateDisplay.setText(tarea.getDeadline());
        imagenTarea.setText(tarea.getImage());
    }

    @Override
    public void onClick(View v) {
        String name, description, importancia, dadLine, link, imagen;

        if (v == accept) {
            name = nameTarea.getText().toString();
            description=descriptionTarea.getText().toString();
            importancia= String.valueOf(spinImportancia.getSelectedItemPosition());
            dadLine=dateDisplay.getText().toString();
            link = linkTarea.getText().toString();
            imagen=imagenTarea.getText().toString();

            if (name.isEmpty() || description.isEmpty())
                Toast.makeText(this, "Nombre y Descripcion no pueden estar vacios", Toast.LENGTH_SHORT).show();
            else {
                tarea.setName(name);
                tarea.setLink(link);
                tarea.setImage(imagen);
                tarea.setDescription(description);
                tarea.setImportancia(Integer.parseInt(importancia)+1);
                tarea.setDeadline(dadLine);
                connection(tarea);
            }
        }
        if (v == cancel)
            finish();
    }

    private void connection(Tarea tarea) {
        progreso = new ProgressDialog(this);
        progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progreso.setMessage("Conectando . . .");
        progreso.setCancelable(false);
        progreso.show();

        Call<Tarea> call = ApiAdapter.getInstance().updateTarea(tarea, tarea.getId());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Tarea> call, Response<Tarea> response) {
        progreso.dismiss();
        if (response.isSuccessful()) {
            Tarea tarea = response.body();
            Intent i = new Intent();
            Bundle mBundle = new Bundle();
            mBundle.putInt("id", tarea.getId());
            mBundle.putString("name", tarea.getName());
            mBundle.putString("description",tarea.getDescription());
            mBundle.putInt("importancia",tarea.getImportancia());
            mBundle.putString("deadline",tarea.getDeadline());
            mBundle.putString("link", tarea.getLink());
            mBundle.putString("image", tarea.getImage());
            i.putExtras(mBundle);
            setResult(OK, i);
            finish();
            showMessage("Tarea editada correctamente");
        } else {
            StringBuilder message = new StringBuilder();
            message.append("Error en la descarga" + response.code());
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
    public void onFailure(Call<Tarea> call, Throwable t) {
        progreso.dismiss();
        if (t != null)
            showMessage("Fallo en la comunicacion\n" + t.getMessage());
    }

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}