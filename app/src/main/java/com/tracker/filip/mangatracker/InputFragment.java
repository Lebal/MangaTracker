package com.tracker.filip.mangatracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Filip on 2017-06-01.
 */

public class InputFragment extends DialogFragment{

    public interface InputListener{
        void onClick(MangaEntry entry);
    }


    private InputListener inputListener;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            Activity activity = (Activity) context;

            inputListener = (InputListener) activity;
        }
        catch (ClassCastException e){

            throw new ClassCastException(context.toString() + "  must implement InputListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceBundle){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = (LayoutInflater.from(getActivity())).inflate(R.layout.input_layout,null);

        final EditText titleInput = (EditText) view.findViewById(R.id.titleInput);
        final EditText chapterInput = (EditText) view.findViewById(R.id.chapterInput);



        builder.setTitle("Input title and chapter").setView(view).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = titleInput.getText().toString();
                int chapter = Integer.parseInt(chapterInput.getText().toString());
                inputListener.onClick(new MangaEntry(title,chapter,0));
            }
        });

        return builder.create();
    }

}
