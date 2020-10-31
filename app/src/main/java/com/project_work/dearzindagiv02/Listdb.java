package com.project_work.dearzindagiv02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

public class Listdb {
    public static final String KEY_ROWID="_id";
    public static final String KEY_TIME="_time";
    public static final String KEY_NAME="person_name";
    public static final String KEY_NUMOFTIMES="num_of_times";
    public static final String KEY_EXPIRY="expiry_date";
    public static final String KEY_DESCRIPTION="_description";

    private final String DATABASE_NAME="Listdb";
    private final String DATABASE_TABLE="TimeTable";
    private final int DATABASE_VERSION=1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public Listdb(Context context){
        ourContext = context;
    }

    public class DBHelper extends SQLiteOpenHelper
    {
        public DBHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String sqlcode="CREATE TABLE "+DATABASE_TABLE+" ("+
                    KEY_ROWID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    KEY_TIME+" TIME(0), "+
                    KEY_NAME+" TEXT NOT NULL, "+
                    KEY_NUMOFTIMES+" INT NOT NULL, "+
                    KEY_EXPIRY+" DATE NOT NULL, "+
                    KEY_DESCRIPTION+" VARCHAR(250) );";
            db.execSQL(sqlcode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }
    public Listdb open() throws SQLException
    {
        ourHelper=new DBHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        ourHelper.close();
    }

    public long createEntry(String time,String name,String num_of_times,String expiry,String description)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_TIME,time);
        cv.put(KEY_NAME,name);
        cv.put(KEY_NUMOFTIMES,num_of_times);
        cv.put(KEY_EXPIRY,expiry);
        cv.put(KEY_DESCRIPTION,description);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

  /*  public String getTime() {
        Cursor c = ourDatabase.query(DATABASE_TABLE, new String[]{KEY_TIME}, null, null, KEY_TIME, null, KEY_TIME);
        String time="";
        int Time=c.getColumnIndex(KEY_TIME);

    }*/


    public String getData()
    {
        String []columns=new String[] {KEY_ROWID,KEY_TIME,KEY_NAME,KEY_NUMOFTIMES,KEY_EXPIRY,KEY_DESCRIPTION};
        Cursor c= ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,KEY_TIME);
        String result="";
        int RowID=c.getColumnIndex(KEY_ROWID);
        int Time=c.getColumnIndex(KEY_TIME);
        int Name=c.getColumnIndex(KEY_NAME);
        int num_of_times=c.getColumnIndex(KEY_NUMOFTIMES);
        int expiry=c.getColumnIndex(KEY_EXPIRY);
        int description=c.getColumnIndex(KEY_DESCRIPTION);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            result+=c.getString(Time)+" "+c.getString(RowID)+" : "+c.getString(Name)+" * "+c.getString(num_of_times)+" "+c.getString(expiry)+"\n";
                if(!c.getString(description).isEmpty())
                {
                    result+=c.getString(description)+"\n";
                }
        }
        c.close();
        return result;
    }
    public String getData(String time)
    {
        String []columns=new String[] {KEY_ROWID,KEY_TIME,KEY_NAME,KEY_NUMOFTIMES,KEY_EXPIRY,KEY_DESCRIPTION};
        Cursor c= ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,KEY_TIME);
        String result="";
        int RowID=c.getColumnIndex(KEY_ROWID);
        int Time=c.getColumnIndex(KEY_TIME);
        int Name=c.getColumnIndex(KEY_NAME);
        int num_of_times=c.getColumnIndex(KEY_NUMOFTIMES);
        int expiry=c.getColumnIndex(KEY_EXPIRY);
        int description=c.getColumnIndex(KEY_DESCRIPTION);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(Time)==time){
                result+=c.getString(Time)+" "+c.getString(RowID)+" : "+c.getString(Name)+" * "+c.getString(num_of_times)+" "+c.getString(expiry)+"\n";
                if(!c.getString(description).isEmpty())
                {
                    result+=c.getString(description)+"\n";
                }
            }
            else
                break;
        }
        c.close();
        return result;
    }
    public long deleteEntry(String rowID)
    {
        return ourDatabase.delete(DATABASE_TABLE,KEY_ROWID+"=?",new String[]{rowID});
    }
}
