package com.tracker.filip.mangatracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Filip on 2017-06-01.
 */

public class InputFragment extends DialogFragment{

    private static int RESULT_LOAD_IMAGE = 1;
    ImageView mangaImage;
    String picturePath ="";
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
        mangaImage = (ImageView) view.findViewById(R.id.mangaImage);
        mangaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,RESULT_LOAD_IMAGE);
            }
        });
        mangaImage.setImageResource(R.drawable.aot);



        builder.setTitle("Input title and chapter").setView(view).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = titleInput.getText().toString();
                int chapter = Integer.parseInt(chapterInput.getText().toString());
                inputListener.onClick(new MangaEntry(title,chapter,picturePath));
            }
        });

        return builder.create();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Context context = getContext();
            Cursor cursor = context.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            mangaImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            // String picturePath contains the path of selected Image
        }



    }




}
