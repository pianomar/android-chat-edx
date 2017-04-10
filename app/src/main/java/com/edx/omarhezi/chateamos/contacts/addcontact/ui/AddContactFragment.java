package com.edx.omarhezi.chateamos.contacts.addcontact.ui;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.edx.omarhezi.chateamos.R;
import com.edx.omarhezi.chateamos.contacts.addcontact.AddContactPresenter;
import com.edx.omarhezi.chateamos.contacts.addcontact.AddContactPresenterImpl;
import com.edx.omarhezi.chateamos.contacts.addcontact.events.AddContactEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddContactFragment extends DialogFragment implements AddContactView, DialogInterface.OnShowListener {


    @BindView(R.id.editTxtEmail)
    EditText editTxtEmail;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;

    AddContactPresenter presenter;

    public AddContactFragment() {
        presenter = new AddContactPresenterImpl(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("Add contact").setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_contact, null);
        unbinder = ButterKnife.bind(this, view);

        builder.setView(view);
        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(this);
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
        View view = getActivity().getCurrentFocus();
        Snackbar.make(view, "User has been added", Snackbar.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void contactNotAdded() {
        editTxtEmail.setText("");
        Toast.makeText(getActivity(), "Error, user was not added", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onShow(DialogInterface dialog) {
        final AlertDialog alertDialog = (AlertDialog) getDialog();
        if(dialog!=null){
            Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
            Button negativeButton = alertDialog.getButton(Dialog.BUTTON_NEGATIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.addContact(editTxtEmail.getText().toString());
                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        presenter.onShow();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
