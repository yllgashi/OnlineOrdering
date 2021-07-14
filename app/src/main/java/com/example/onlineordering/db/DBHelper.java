package com.example.onlineordering.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.onlineordering.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "userdb.sqlite";
    public static final String CONTACTS_TABLE_NAME = "mycontacts";
    public static final String CONTACTS_COLUMN_ID  = "id";
    public static final String CONTACTS_COLUMN_STUNAME = "name";
    public static final String CONTACTS_COLUMN_STUUSERNAME = "username";
    public static final String CONTACTS_COLUMN_STUPASSWORD = "password";
    public static final String CONTACTS_COLUMN_STUEMAIL = "email";

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table users " +
                        "(id integer primary key autoincrement, name text,username text,email text, password text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public boolean addUser(User user){
        /*,*/
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put(CONTACTS_COLUMN_STUNAME,user.getFullname());
        contantValues.put(CONTACTS_COLUMN_STUEMAIL, user.getEmail());
        contantValues.put(CONTACTS_COLUMN_STUUSERNAME, user.getUsername());
        contantValues.put(CONTACTS_COLUMN_STUPASSWORD, user.getPassword());
        db.insert("users", null, contantValues);
        db.close();
        return true;
    }
    public boolean updateUser(User user)
    {
        /*,String contactname,*/
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put(CONTACTS_COLUMN_STUNAME,user.getFullname());
        contantValues.put(CONTACTS_COLUMN_STUEMAIL, user.getEmail());
        contantValues.put(CONTACTS_COLUMN_STUUSERNAME, user.getUsername());
        contantValues.put(CONTACTS_COLUMN_STUPASSWORD, user.getPassword());
        db.update("users", contantValues, "id = ?", new String[]{Integer.toString(user.getId())});
        db.close();
        return true;
    }
    public Integer deleteUser(Integer id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("users","id = ?",new String[]{Integer.toString(id)});
    }
    public User getUserByID(int userId){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("Select * from users where id = " + userId + "", null);

        if (cursor.moveToFirst()) {
            do {

                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(CONTACTS_COLUMN_ID)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUEMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUPASSWORD)));
                user.setFullname(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUNAME)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUUSERNAME)));
                return user;
            } while (cursor.moveToNext());
        }
        return null;
    }
    public int numberOfRows(){
        SQLiteDatabase db=this.getWritableDatabase();
        int numRows=(int) DatabaseUtils.queryNumEntries(db,CONTACTS_TABLE_NAME);
        return numRows;
    }
    public ArrayList<User> getAllUsers(){
        ArrayList<User> arraylist= new ArrayList<User>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from users",null);

        if (cursor.moveToFirst()) {
            do {

                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(CONTACTS_COLUMN_ID)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUEMAIL)));
                user.setFullname(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUNAME)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUUSERNAME)));
                arraylist.add(user);
            } while (cursor.moveToNext());
        }
        return arraylist;
    }


    public ArrayList<User> getUsersByQuery(String query){
        ArrayList<User> arraylist= new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from users where name Like '%" + query + "%'",null);

        if (cursor.moveToFirst()) {
            do {

                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(CONTACTS_COLUMN_ID)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUEMAIL)));
                user.setFullname(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUNAME)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUUSERNAME)));
                arraylist.add(user);
            } while (cursor.moveToNext());
        }
        return arraylist;
    }

    public User loginUser(String username, String password){
        ArrayList<User> arraylist= new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from users where TRIM(username) = '" + username.trim() + "' and TRIM(password) = '" + password.trim() + "'"  ,null);

        if (cursor.moveToFirst()) {
            do {

                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(CONTACTS_COLUMN_ID)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUEMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUPASSWORD)));
                user.setFullname(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUNAME)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUUSERNAME)));
                return user;
            } while (cursor.moveToNext());
        }
        return null;

    }
}
