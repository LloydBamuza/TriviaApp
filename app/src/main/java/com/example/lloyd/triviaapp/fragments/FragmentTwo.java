package com.example.lloyd.triviaapp.fragments;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lloyd.triviaapp.R;
import com.example.lloyd.triviaapp.TriviaMain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Anu on 22/04/17.
 */



public class FragmentTwo extends Fragment {
    final int INTERNET_REQ = 773;
    EditText edtTrivNum;
    TextView txtTrivia;
    Button btnTrivEnter;

    public FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        //get internet permission
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.INTERNET},INTERNET_REQ);

        //get UI references
        edtTrivNum = (EditText) view.findViewById(R.id.edtMonth);
        txtTrivia = (TextView) view.findViewById(R.id.txtTrivia);
        btnTrivEnter = (Button) view.findViewById(R.id.btnTrivEnter);

        btnTrivEnter.setOnClickListener(e->
        {
            String input = "http://numbersapi.com/" + edtTrivNum.getText().toString() + "/math";
            StringBuilder message = new StringBuilder();

            txtTrivia.setText("");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedReader bis = null;
                        URL trivia = new URL(input);
                        bis = new BufferedReader(new InputStreamReader(trivia.openStream()));
                        String userInput = edtTrivNum.getText().toString();

                        //store output
                        message.append("TRIVIA for number: " + userInput + " ::::::::\n\n");
                        String buffer = "No Trivia available";

                        while ((buffer = bis.readLine()) != null)
                        {
                            message.append(buffer + "\n");
                        }

                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                //Display output
                                txtTrivia.setText(message.toString());
                            }
                        });


                    }


                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();


        });


        return view;
    }

}