package com.tracker.filip.mangatracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BlockedNumberContract;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Filip on 2017-06-01.
 */

@SuppressLint("ValidFragment")
public class EditFragment extends DialogFragment{

    private static int RESULT_LOAD_IMAGE = 1;
    ImageView mangaImage;
    String picturePath ="";
    private MangaEntry entryLocal;

    public interface UpdateListener {
        void onClickUpdate(MangaEntry entry1, MangaEntry entry2);
        void removeEntry(MangaEntry entry);
    }

    private UpdateListener updateListener;

    @SuppressLint("ValidFragment")
    public EditFragment(MangaEntry entry) {
        super();
        entryLocal = entry;

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            Activity activity = (Activity) context;

            updateListener = (UpdateListener) activity;
        }
        catch (ClassCastException e){

            throw new ClassCastException(context.toString() + "  must implement UpdateListener");
        }
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstance){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = (LayoutInflater.from(getActivity())).inflate(R.layout.input_layout,null);


        final EditText titleInput = (EditText) view.findViewById(R.id.titleInput);
        titleInput.setText(entryLocal.getName()+"");
        final EditText chapterInput = (EditText) view.findViewById(R.id.chapterInput);
        chapterInput.setText(entryLocal.getChapter()+"");

        mangaImage = (ImageView) view.findViewById(R.id.mangaImage);
        mangaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,RESULT_LOAD_IMAGE);
            }
        });

        File imgFile = new  File(entryLocal.getPicture());
        if(imgFile.exists()){
            picturePath = imgFile.getPath();
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            mangaImage.setImageBitmap(myBitmap);

        }
        else {
            mangaImage.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        builder.setTitle("Input title and chapter").setView(view).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = titleInput.getText().toString();
                int chapter = Integer.parseInt(chapterInput.getText().toString());
                updateListener.onClickUpdate(new MangaEntry(title,chapter,picturePath), entryLocal);
            }
        }).setCancelable(true).setNegativeButton("Remove", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateListener.removeEntry(entryLocal);
            }
        }).setNeutralButton("Cancel",null);


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

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            BitmapFactory.decodeFile(picturePath,options);
            mangaImage.setImageBitmap(bitmap);

        }
    }


}
