package com.example.mis_internee.atendence_app_android.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kamran Ijaz on 22/04/2019.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public  static final String Table_Name="REIMBURSEMENT_FORM";
    public  static final String COL1="place";
    public  static final String COL2="nature";
    public  static final String COL3="medicalbills";
    public  static final String COL4="othersbills";
    public  static final String COL5="allbills";
    public  static final String COL6="attacheddocs";
    public DatabaseHelper(Context context) {
        super(context,Table_Name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable="CREATE TABLE "+Table_Name+"(ID INTEGER PRIMARY KEY AUTOINCREMENT ,"+ COL1+" TEXT ,"+COL2+" INTEGER , "+COL3+" TEXT ,"+
                COL4+" TEXT , "+COL5+" INTEGER ,"+COL6+" INTEGER  )";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop="DROP IF TABLE EXIST";
        db.execSQL( drop+Table_Name);
        onCreate(db);
    }
    public boolean addData(String item,String item2,String item3,String item4,String item5,String item6,String item7){
        SQLiteDatabase sq= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(COL1,item);
        cv.put(COL2,item2);
        cv.put(COL3,item3);
        cv.put(COL4,item4);
        cv.put(COL5,item4);
        cv.put(COL6,item4);

        long result=sq.insert(Table_Name,null,cv);
        if(result==-1){
            return false;
        }
        else {
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase sq= this.getReadableDatabase();
        String query="SELECT * FROM "+Table_Name;
        Cursor data= sq.rawQuery(query,null);
        return data;
    }
    public void deleteData(String item){
        SQLiteDatabase sq= this.getReadableDatabase();
        String query="DELETE FROM "+Table_Name+ " WHERE ID="+item;
        sq.execSQL(query);

    }
    public Cursor getSelectData(String item){
        SQLiteDatabase sq= this.getReadableDatabase();
        String query="SELECT * FROM "+Table_Name+ " WHERE ID="+item;
        Cursor data= sq.rawQuery(query,null);
        return data;
    }
}
