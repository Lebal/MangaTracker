package com.tracker.filip.mangatracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Filip on 2017-05-31.
 */

public class MangaEntryAdapter extends ArrayAdapter<MangaEntry>{
    private final int mangaLayoutResource;

    public MangaEntryAdapter(@NonNull Context context, int mangaLayoutResource) {
        super(context, 0);
        this.mangaLayoutResource=mangaLayoutResource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final View view = getWorkingView(convertView);
        final ViewHolder viewHolder = getViewHolder(view);
        final MangaEntry entry = getItem(position);

        viewHolder.titleView.setText(entry.getName()+"");

        viewHolder.chapterView.setText(entry.getChapter()+"");


        File imgFile = new  File(entry.getPicture());
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            viewHolder.imageView.setImageBitmap(myBitmap);

        }
        else {
            viewHolder.imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }
        return view;
    }



    private View getWorkingView(View convertView){

        View workingView;
        if(null==convertView){

            Context context = getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            workingView = inflater.inflate(mangaLayoutResource,null);
        }

        else {
            workingView= convertView;
        }
        return workingView;

    }



    private ViewHolder getViewHolder(View workingView){

        Object tag = workingView.getTag();

        ViewHolder viewHolder;

        if(null == tag || !(tag instanceof ViewHolder)){

            viewHolder = new ViewHolder();

            viewHolder.titleView = (TextView) workingView.findViewById(R.id.title);
            viewHolder.chapterView = (TextView) workingView.findViewById(R.id.chapters);
            viewHolder.imageView = (ImageView) workingView.findViewById(R.id.listPicture);

            workingView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder)tag;
        }
        return viewHolder;

    }


    private static class ViewHolder{

        TextView titleView;
        TextView chapterView;
        ImageView imageView;
    }
}
