package com.example.registration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersProfileRecyclerAdapter extends RecyclerView.Adapter<UsersProfileRecyclerAdapter.UserProfileViewHolder> {

    private List<User> listUsersProfile;

    public UsersProfileRecyclerAdapter(List<User> listUsersProfile) {
        this.listUsersProfile = listUsersProfile;
    }

    @Override
    public UserProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_userprofil_recycler, parent, false);

        return new UserProfileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserProfileViewHolder holder, int position) {
        holder.txtUser.setText(listUsersProfile.get(position).getName()+" "+ listUsersProfile.get(position).getSurname());
        holder.textViewName.setText(listUsersProfile.get(position).getName());
        holder.textViewSurname.setText(listUsersProfile.get(position).getSurname());

        holder.textViewEmail.setText(listUsersProfile.get(position).getEmail());
        holder.textViewPassword.setText(listUsersProfile.get(position).getPassword());
        holder.textViewAdress.setText(listUsersProfile.get(position).getAdress());
        holder.textViewCel.setText(listUsersProfile.get(position).getCel());



    }

    @Override
    public int getItemCount() {
        Log.v(UsersProfileRecyclerAdapter.class.getSimpleName(),""+listUsersProfile.size());
        return listUsersProfile.size();
    }


    /**
     * ViewHolder class
     */
    public static class  UserProfileViewHolder extends RecyclerView.ViewHolder {

        public static EditText textViewName;
        public static EditText textViewSurname;
        public static TextView txtUser;
        public  static EditText textViewEmail;
        public static EditText textViewPassword;
        public static EditText textViewAdress;
        public static EditText textViewCel;
        public static Button btnCancel;
        public static Button btnSave;



        public UserProfileViewHolder(View view) {
            super(view);
            btnCancel=(Button)view.findViewById(R.id.btnCancel);
            btnSave=(Button)view.findViewById(R.id.btnSave);
            textViewName = (EditText)view.findViewById(R.id.txtNameProfile);
            textViewSurname= (EditText)view.findViewById(R.id.txtSurnameProfile);
            txtUser= (TextView) view.findViewById(R.id.txtUser);
            textViewEmail = (EditText)view.findViewById(R.id.txtEmailProfile);
            textViewPassword =(EditText)view.findViewById(R.id.txtPasswordProfile);
            textViewAdress=(EditText)view.findViewById(R.id.txtAdressProfile);
            textViewCel=(EditText)view.findViewById(R.id.txtCelProfile);
        }
    }


}
