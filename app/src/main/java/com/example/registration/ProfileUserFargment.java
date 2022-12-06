package com.example.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProfileUserFargment extends Fragment {

    private RecyclerView recyclerViewuserProfil;
    private List<User> listUsers;
    private UsersProfileRecyclerAdapter usersProfileRecyclerAdapter;
    private Toolbar mToolbarBottom;
    private DatabaseHelper databaseHelper;
    FragmentManager fragmentManager;
    String adressemail;
    String txtEmail;
    String email;
    SharedPreferences sharedPreferences;
    ProgressDialog mProgressDialog;

    public ProfileUserFargment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_user_fargment, container, false);
        sharedPreferences=getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        //Intent intent = getIntent();
        email=sharedPreferences.getString("Email","test@gmail.com");
        // email=getArguments().getString("email");
        recyclerViewuserProfil = (RecyclerView)view.findViewById(R.id.recyclerViewUsers);
        mProgressDialog = new ProgressDialog(getActivity());
        //  mProgressDialog.setCancelable(false);
        mProgressDialog.setIcon(R.drawable.greenhotel);
        mProgressDialog.setTitle("Green Star Hotel");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("Please wait ...");
        mProgressDialog.show();

        //   UsersProfileRecyclerAdapter.UserProfileViewHolder.txtUser.setText(email.toString());

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        initObjects();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UsersProfileRecyclerAdapter.UserProfileViewHolder.textViewName.setEnabled(true);
                UsersProfileRecyclerAdapter.UserProfileViewHolder.textViewSurname.setEnabled(true);

                UsersProfileRecyclerAdapter.UserProfileViewHolder.textViewEmail.setEnabled(true);
                UsersProfileRecyclerAdapter.UserProfileViewHolder.textViewPassword.setEnabled(true);
                UsersProfileRecyclerAdapter.UserProfileViewHolder.textViewCel.setEnabled(true);
                UsersProfileRecyclerAdapter.UserProfileViewHolder.textViewAdress.setEnabled(true);
                UsersProfileRecyclerAdapter.UserProfileViewHolder.btnSave.setEnabled(true);
                UsersProfileRecyclerAdapter.UserProfileViewHolder.btnSave.setVisibility(View.VISIBLE);
                UsersProfileRecyclerAdapter.UserProfileViewHolder.btnCancel.setVisibility(View.VISIBLE);
                UsersProfileRecyclerAdapter.UserProfileViewHolder.btnCancel.setEnabled(true);
                UsersProfileRecyclerAdapter.UserProfileViewHolder.btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDataFromSQLiteUpdate();
                    }
                });


            }
        });


        return view;
    }

    private void getDataFromSQLiteUpdate() {

        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(databaseHelper.getUserProfil(txtEmail));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                usersProfileRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    private void initObjects() {


        listUsers = new ArrayList<>();
        usersProfileRecyclerAdapter = new UsersProfileRecyclerAdapter(listUsers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewuserProfil.setLayoutManager(mLayoutManager);
        recyclerViewuserProfil.setItemAnimator(new DefaultItemAnimator());
        recyclerViewuserProfil.setHasFixedSize(true);
        recyclerViewuserProfil.setAdapter(usersProfileRecyclerAdapter);
        databaseHelper = new DatabaseHelper(getActivity());

        //   txtEmail= String.valueOf(UsersProfileRecyclerAdapter.UserProfileViewHolder.textViewEmail);
        txtEmail=String.valueOf(email);

        getDataFromSQLite();


    }

    private void getDataFromSQLite() {
        mProgressDialog.dismiss();
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(databaseHelper.getUserProfil(txtEmail));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                usersProfileRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }


}
