package com.example.hannah.wemunize;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForgottenPhotoalbum extends Fragment {
    EditText pnum1, pnum2, pnum3;
    Button search;
    ListView listView;
    ProgressBar bar;
    LinearLayout layout;
    Button button;
    public ForgottenPhotoalbum() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgotten_photoalbum, container, false);
        pnum1 = (EditText) view.findViewById(R.id.forgottennum);
        pnum1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    Search();
                }
                return false;
            }
        });
        pnum2 = (EditText) view.findViewById(R.id.forgottensecondnum);
        pnum2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    Search();
                }
                return false;
            }
        });
        pnum3 = (EditText) view.findViewById(R.id.forgottenthirdnum);
        pnum3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    Search();
                }
                return false;
            }
        });
        search = (Button) view.findViewById(R.id.forgottenbutton);
        listView = (ListView) view.findViewById(R.id.forgottenphotoalbum);
        bar = (ProgressBar) view.findViewById(R.id.forgottenbar);
        bar.setVisibility(View.INVISIBLE);
        layout = (LinearLayout) view.findViewById(R.id.forgottenLinear);
        button = (Button) view.findViewById(R.id.forgottenbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });
        return view;
    }
    public void Search(){
        pnum1.setTextColor(getResources().getColor(R.color.textsubmitted));
        pnum2.setTextColor(getResources().getColor(R.color.textsubmitted));
        pnum3.setTextColor(getResources().getColor(R.color.textsubmitted));
        bar.setVisibility(View.VISIBLE);
        final ArrayList<ParseObject> list = new ArrayList<ParseObject>();
        if(pnum1.getText().toString().isEmpty() || pnum2.getText().toString().isEmpty() || pnum3.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            bar.setVisibility(View.INVISIBLE);
        }
        else if(pnum1.getText().toString().length() <11 ||pnum2.getText().toString().length() <11 || pnum3.getText().toString().length() <11 ){
            Toast.makeText(getActivity(), "Please fill in valid numbers", Toast.LENGTH_SHORT).show();
            if(pnum1.getText().toString().length() < 11){
                pnum1.setTextColor(getResources().getColor(R.color.texterror));
            }
            if(pnum2.getText().toString().length() < 11){
                pnum2.setTextColor(getResources().getColor(R.color.texterror));
            }
            if(pnum3.getText().toString().length() < 11){
                pnum3.setTextColor(getResources().getColor(R.color.texterror));
            }
            bar.setVisibility(View.INVISIBLE);
        }
        else{
            String firstnum = pnum1.getText().toString().trim();
            String secnum = pnum2.getText().toString().trim();
            String thirdnum = pnum3.getText().toString().trim();
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ChildBioData");
            query.whereEqualTo("PhoneNum1", firstnum);
            query.whereEqualTo("PhoneNum2", secnum);
            query.whereEqualTo("PhoneNum3", thirdnum);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(objects != null){
                        if(objects.isEmpty()){
                            new AlertDialog.Builder(getActivity())
                                    .setMessage("No result for this set of numbers")
                                    .setTitle("Invalid Search")
                                    .setCancelable(true)
                                    .show();
                            pnum1.setText("");
                            pnum2.setText("");
                            pnum3.setText("");
                        }
                        else{
                        for (ParseObject obj : objects){
                            list.add(obj);
                        }
                        missingadapter adapter = new missingadapter(getActivity(), R.layout.list_view_layout_single, list);
                        layout.setVisibility(View.GONE);
                        listView.setAdapter(adapter);
                        }
                    }
                    else{
                        String error = e.getMessage().substring(0, 1) + e.getMessage().substring(1);
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setMessage(error)
                                .setCancelable(true)
                                .show();
                        pnum1.setText("");
                        pnum2.setText("");
                        pnum3.setText("");
                    }
                }
            });
        }
    }
}
