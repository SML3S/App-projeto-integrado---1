package com.sml3s.trabalho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE ="com.example.android.twoactivities.extra.MESSAGE";
    private TextView mMessageviewUsuario;
    private Spinner campo;
    List<User> listaDeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        campo = findViewById(R.id.spinnerDeUsuario);
        mMessageviewUsuario = findViewById(R.id.viewUsuario);
        buscaDados();
    }

    private void buscaDados() {
        RetrofitService.getServico().obterUsuarios().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                listaDeUser = response.body();
                List<String> listanome= new ArrayList<String>();
                for (User user : listaDeUser) {
                    listanome.add(user.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>( MainActivity.this,
                        android.R.layout.simple_spinner_item, listanome);
                campo.setAdapter(adapter);
                campo.setOnItemSelectedListener(MainActivity.this);

            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        TextView saida = findViewById(R.id.viewUsuario);
        User user = listaDeUser.get(position);
        mMessageviewUsuario.setText("Nome:"+user.getName()+"\nemail:"+user.getEmail());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void launchSecondActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, SecondActivity.class);
        String message = mMessageviewUsuario.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
