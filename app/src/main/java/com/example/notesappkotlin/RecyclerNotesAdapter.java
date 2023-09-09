package com.example.notesappkotlin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Delete;

import java.util.ArrayList;

public class RecyclerNotesAdapter extends RecyclerView.Adapter<RecyclerNotesAdapter.ViewHolder> {

    Context context;
    ArrayList<Note>arrNotes=new ArrayList<>();
    DatabaseHelper databaseHelper;

    public RecyclerNotesAdapter(Context context,ArrayList<Note> arrNotes,DatabaseHelper databaseHelper)
    {
                 this.arrNotes=arrNotes
                ;this.context=context;
                this.databaseHelper=databaseHelper;
    }

    @NonNull
    @Override
    public RecyclerNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.note_row,parent,false);

        ViewHolder viewHolder=new ViewHolder((view));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerNotesAdapter.ViewHolder holder, int position) {

        holder.txtTittle.setText(arrNotes.get(position).getTittle());
        holder.txtContext.setText(arrNotes.get(position).getContent());

        holder.llrow.setOnLongClickListener(new View.OnLongClickListener(){

           @Override
           public boolean onLongClick(View v){

               holder.deleteItem(position);
               return true;
        }
        });

    }

    @Override
    public int getItemCount() {
        return arrNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
       TextView txtTittle ,txtContext;
       LinearLayout llrow;


       public ViewHolder(@NonNull View itemView)
       {
           super(itemView);

           txtTittle=itemView.findViewById(R.id.txtTittle);
           txtContext=itemView.findViewById(R.id.txtDescription);
           llrow=itemView.findViewById(R.id.llrow);

       }

       public void deleteItem(int pos)
       {
           AlertDialog dialog=new AlertDialog.Builder(context)
                   .setTitle("Delete Note")
                   .setMessage("Are you sure! you want to delete the this Note ")
                   .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {

                           databaseHelper.noteDao().deleteNote(
                                   new Note(arrNotes.get(pos).getId(),arrNotes.get(pos).getTittle(),
                                           arrNotes.get(pos).getContent())
                           );

                           ((MainActivity)context).showNotes();
                       }
                   }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {

                       }
                   }).show();
       }
    }
}
