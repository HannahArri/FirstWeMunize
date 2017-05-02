package com.example.hannah.wemunize;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddressRfid extends Fragment {

    Bundle args;
    EditText address, rfid, photoal;
    Button next;
    public AddressRfid() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address_rfid, container, false);
        args = getArguments();
        address = (EditText) view.findViewById(R.id.registerAddress);
        rfid = (EditText) view.findViewById(R.id.registerRFID);
        photoal = (EditText) view.findViewById(R.id.registerphotonum2);
        next = (Button) view.findViewById(R.id.registerbutton);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        return view;
    }

    //function called when button is clicked
    public void register(){
        String newaddress, newrfid, photoalbum;

        if (address.getText().toString().isEmpty()) {
            newaddress = "Address";
        }else{
            newaddress = address.getText().toString();
        }

        if(rfid.getText().toString().isEmpty()){
            newrfid = "RFID";
        }else{
            newrfid = rfid.getText().toString();
        }
        photoalbum = photoal.getText().toString();
        if(!photoalbum.equalsIgnoreCase(args.getString("PhotoAlbumNum1"))){
            new AlertDialog.Builder(getActivity())
                    .setTitle("ERROR")
                    .setMessage("This photo album number doesn't match the previously entered number")
                    .show();
        }
        else{
        args.putString("Address", newaddress);
        args.putString("RFID", newrfid);

        Summary summary = new Summary();
        summary.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack("Address")
                .add(android.R.id.content, summary)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .hide(AddressRfid.this)
                .commit();
        }
    }

}
