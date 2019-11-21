package com.christian.fototag.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.christian.fototag.R;
import com.christian.fototag.fragment.FotosFragment;
import com.christian.fototag.fragment.TextoFragment;
import com.christian.fototag.model.TagTexto;
import java.util.*;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TagTexto tagTexto;
    List<TagTexto>  listaTagTexto = new ArrayList<TagTexto>();
    Integer atual = -1;
    private Button buttonText, buttonFoto;
    private TextoFragment textoFragment;
    private FotosFragment fotosFragment;
    public TextView textTag;
    private MenuItem itemCamera, itemNovaTag;
    private EditText editTag;
    private EditText editDescricao;
    private Boolean taNoTexto = true;
    List<String> nomeTags = new ArrayList<String>();
    ListView listViewTags;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TIRA SOMBRA DA TOOLBAR
        getSupportActionBar().setElevation(0);

        buttonFoto = findViewById(R.id.buttonFotos);
        buttonText = findViewById(R.id.buttonTexto);
        textTag = findViewById(R.id.textTag);

        textoFragment = new TextoFragment();
        fotosFragment = new FotosFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameConteudo, textoFragment );
        transaction.commit();
        buttonFoto.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        buttonText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameConteudo, textoFragment );
                transaction.commit();
                buttonFoto.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                buttonText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                itemCamera.setVisible(false);
                itemNovaTag.setVisible(true);
                taNoTexto = true;
                atualizaObjetos(atual);
            }
        });

        buttonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameConteudo, fotosFragment);
                transaction.commit();
                buttonText.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                buttonFoto.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                itemCamera.setVisible(true);
                itemNovaTag.setVisible(false);
                taNoTexto = false;
                atualizaObjetos(atual);
            }
        });









    }



    public TextView atualizaTag(String tag){
        textTag.setText(tag);
        return textTag;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        itemCamera = menu.findItem(R.id.itemCamera);
        itemNovaTag = menu.findItem(R.id.itemNovaTag);

        itemCamera.setVisible(false);
        itemNovaTag.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.itemCamera:
                Toast.makeText(this, "Camera Clicado", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(in.resolveActivity(getPackageManager()) !=null){
             
                    atualizaObjetos(atual);
                }



                return true;

            case R.id.itemCompartilhar:
                atualizaObjetos(atual);
                if (!nomeTags.isEmpty()) {
                    if (taNoTexto == true) {
                        Toast.makeText(this, "Compartilhar TEXTO", Toast.LENGTH_SHORT).show();

                        Intent enviarIntent = new Intent();
                        enviarIntent.setAction(Intent.ACTION_SEND);
                        String texto;
                        texto = editTag.getText().toString() + "\n" + editDescricao.getText().toString();
                        enviarIntent.putExtra(Intent.EXTRA_TEXT, texto);
                        enviarIntent.setType("text/plain");
                        startActivity(enviarIntent);
                    } else {
                        Toast.makeText(this, "Compartilhar FOTO", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Sem conteudo", Toast.LENGTH_SHORT).show();
                }
                return true;

            case  R.id.itemNovaTag:
                atualizaObjetos(atual);
                //ALERT COM EDIT TEXT
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Add Tag");
                final EditText input = new EditText(this);
                alertDialog.setView(input);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        String textoInput = input.getText().toString().trim();
                        Toast.makeText(getApplicationContext(), textoInput, Toast.LENGTH_SHORT).show();
                        TagTexto tag = new TagTexto();
                        tag.setNome(textoInput);
                        //tag.setDescricao("Exemplo de Texto do objeto tag"+textoInput);
                        listaTagTexto.add(tag);
                        nomeTags.add(tag.getNome());

                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //nada
                    }
                });
                alertDialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void atualizaObjetos(Integer i){
        if (i != -1){
            TagTexto t = listaTagTexto.get(i);
            String  txt = editTag.getText().toString();
            t.setNome(txt);
            t.setDescricao(editDescricao.getText().toString());
            nomeTags.set(i,txt);
            textTag.setText(txt);
        }
    }




    //ABRE ALERTA COM A LISTA DAS TAGS
    public void openDialog(View v){
        atualizaObjetos(atual);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View rowList = getLayoutInflater().inflate(R.layout.row, null);
        listViewTags = rowList.findViewById(R.id.listViewTag);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomeTags);
        listViewTags.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        alertDialog.setView(rowList);

        final AlertDialog dialog = alertDialog.create();

        String tituloAlerta;
        if (nomeTags.isEmpty()){
            tituloAlerta = "Nenhuma Tag definida";
        }
        else{
            tituloAlerta = "Selecionar Tag";
            listViewTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TagTexto t = listaTagTexto.get(i);
                    dialog.dismiss();
                    textTag.setText(t.getNome());
                    editTag = findViewById(R.id.editTextTagNome);
                    editDescricao = findViewById(R.id.editTextDescricao);
                    editTag.setText(t.getNome());
                    editDescricao.setText(t.getDescricao());
                    atual = i;
                    atualizaObjetos(atual);
                }
            });
        }
        dialog.setTitle(tituloAlerta);
        dialog.show();
    }



}


