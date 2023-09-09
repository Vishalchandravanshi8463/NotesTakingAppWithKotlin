package com.example.notesappkotlin;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "tittle")
    private String tittle;

    @ColumnInfo(name="content")
    private String content;

    @Ignore
    public Note(String tittle,String content)
    {
        this.tittle=tittle;
        this.content=content;
    }

    public Note(int id, String tittle, String content) {
        this.tittle=tittle;
        this.id=id;
        this.content=content;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
