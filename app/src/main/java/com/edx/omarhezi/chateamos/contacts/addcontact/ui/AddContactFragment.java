package com.edx.omarhezi.chateamos.contacts.addcontact.ui;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.edx.omarhezi.chateamos.R;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddContactFragment extends DialogFragment implements AddContactView {


    @BindView(R.id.editTxtEmail)
    EditText editTxtEmail;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;

    public AddContactFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_contact, null);
        unbinder = ButterKnife.bind(this, view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    @Override
    public void showInput() {
        editTxtEmail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInput() {
        editTxtEmail.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void contactAdded() {
        Toast.makeText(getActivity(), "User has been added", Toast.LENGTH_LONG).show();
    }

    @Override
    public void contactNotAdded() {
        editTxtEmail.setText("");
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
