package com.christian.fototag.fragment;


import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.christian.fototag.R;
import com.christian.fototag.model.TagTexto;

import java.util.ArrayList;
import java.util.List;


public class TextoFragment extends Fragment {
    private TagTexto tagTexto;
    List<TagTexto> listaTagTexto = new ArrayList<TagTexto>();
    private Button buttonText, buttonFoto;
    private TextoFragment textoFragment;
    private FotosFragment fotosFragment;
    private MenuItem itemCamera, itemNovaTag;


    private Boolean taNoTexto = true;
    List<String> nomeTags = new ArrayList<String>();
    ListView listViewTags;

    private EditText editTag;
    private EditText editDescricao;



    public TextoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_texto, container, false);
        listaTagTexto.isEmpty();


        editTag = view.findViewById(R.id.editTextTagNome);
        editDescricao = view.findViewById(R.id.editTextDescricao);
        //editTextTagNome.setText("");


        //LISTENER
        editTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence txt, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    return view;
    }






}
