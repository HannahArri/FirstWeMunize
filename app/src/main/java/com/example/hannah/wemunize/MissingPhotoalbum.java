package com.example.hannah.wemunize;


import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class MissingPhotoalbum extends Fragment {

    android.widget.SearchView phonenumber;
    Resources res;
    TextView rfid;
    ArrayList<ParseObject> list;
    String numberqueried;
    ListView listView;
    ProgressBar bar;
    int itemPosition;
    public MissingPhotoalbum() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_missing_photoalbum, container, false);
        bar =(ProgressBar) view.findViewById(R.id.missingprogressbar);
        res = getResources();
        bar.setVisibility(View.INVISIBLE);
        listView = (ListView) view.findViewById(R.id.missinglist);
        //set onclicklistener on listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                // create input dialog to retrieve the new rfid tag
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("New RFID");
                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                input.setLayoutParams(lp);
                builder.setView(input);
                //positive button to save new tag
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newtext = input.getText().toString().trim();
                        rfid = (TextView) view.findViewById(R.id.newrfid);
                        ParseObject object = list.get(position);
                        //call method to update the rfid tag in the parse object
                        updaterfid(object.getString("FirstName"), object.getString("PhoneNum2"), newtext, position);
                        rfid.setText(newtext);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        phonenumber = (android.widget.SearchView) view.findViewById(R.id.missingphonenumber);
        setSearch();

        return view;
    }
    //called whenever search text is submitted
    public void setSearch(){
        android.widget.SearchView.OnQueryTextListener queryTextListener = new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                numberqueried = query;
                list = search(query);
                missingadapter adapter = new missingadapter(getActivity(), R.layout.list_view_layout_single, list);
                listView.setAdapter(adapter);
                bar.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                numberqueried = newText;
                list = search(newText);
                bar.setVisibility(View.INVISIBLE);
                return false;
            }
        };
        phonenumber.setOnQueryTextListener(queryTextListener);
    }
    // called when query text is submitted
    public ArrayList<ParseObject> search(final String number){
        bar.setVisibility(View.VISIBLE);
        final ArrayList<ParseObject> list = new ArrayList<ParseObject>(1);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ChildBioData");
        query.whereEqualTo("PhoneNum1", number);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects != null){
                    for(ParseObject object: objects){
                        list.add(object);
                    }
                }
                else{
                    String error = e.getMessage().substring(0, 1) + e.getMessage().substring(1);
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage(error)
                            .setCancelable(true)
                            .show();
                }
            }
        });

        return list;
    }
    public void updaterfid(String id, String num, String tag, final int itemPosition){
        final String newtag = tag;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ChildBioData");
        query.whereEqualTo("FirstName", id);
        query.whereEqualTo("PhoneNum2", num);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list != null){
                    if(!list.isEmpty()){
                    list.get(itemPosition).put("RFIDTAG", newtag);
                    list.get(itemPosition).saveInBackground();
                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "No data fetched", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    String error = e.getMessage().substring(0, 1) + e.getMessage().substring(1);
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage(error)
                            .setCancelable(true)
                            .show();
                }
            }

        });


    }
}
