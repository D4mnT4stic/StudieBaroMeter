package imtpmd.studiebarometer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Joost van Eeden on 23-5-2016.
 */
    public class DatabaseHelper extends SQLiteOpenHelper {
        public static SQLiteDatabase mSQLDB;
        private static DatabaseHelper mInstance;			// SINGLETON TRUC
        public static final String dbName = "barometer.db";	// Naam van je DB
        public static final int dbVersion = 1;				// Versie nr van je db.

        public DatabaseHelper(Context ctx) {				// De constructor doet niet veel meer dan ...
            super(ctx, dbName, null, dbVersion);			// … de super constructor aan te roepen.
        }

        public static synchronized DatabaseHelper getHelper (Context ctx){  // SYNCRONIZED TRUC
            if (mInstance == null){
                mInstance = new DatabaseHelper(ctx);
                mSQLDB = mInstance.getWritableDatabase();
            }
            return mInstance;
        }

        @Override // CREATE TABLE course (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, ects TEXT, code TEXT grade TEXT);
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DatabaseInfo.CourseTables.COURSE + " (" +
                            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            DatabaseInfo.CourseColumn.NAME + " TEXT," + DatabaseInfo.CourseColumn.ECTS + " TEXT," +
                            DatabaseInfo.CourseColumn.GRADE + " TEXT," + DatabaseInfo.CourseColumn.PERIOD + " TEXT," +
                            DatabaseInfo.CourseColumn.KERNVAK + " TEXT," + DatabaseInfo.CourseColumn.OMSCHRIJVING + " TEXT," +
                            DatabaseInfo.CourseColumn.SPECIALISATIE + " TEXT);"
            );
        }    // . . . . continued on next slide
// Continued from previous slide

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	 // BIJ EEN UPDATE VAN DE DB (ID verhoogd)
            db.execSQL("DROP TABLE IF EXISTS "+ DatabaseInfo.CourseTables.COURSE);	 // GOOI ALLES WEG
            onCreate(db);									 // EN CREER HET OPNIEUW
        }

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version ){
            super(context, name, factory, version);
        }

        public void insert(String table, String nullColumnHack, ContentValues values){
            mSQLDB.insert(table, nullColumnHack, values);
        }

        public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy){
            return mSQLDB.query(table, columns, selection, selectArgs, groupBy, having, orderBy);
        }

        public void updateCijfer(String id, String cijfer){
            SQLiteDatabase sqlDB = this.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put("grade",cijfer);
            String where = "_id = " + id;
            sqlDB.update("course",values,where,null);

        }


        public ArrayList<Cursor> getData(String Query){
            //get writable database
            SQLiteDatabase sqlDB = this.getWritableDatabase();
            String[] columns = new String[] { "mesage" };
            //an array list of cursor to save two cursors one has results from the query
            //other cursor stores error message if any errors are triggered
            ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
            MatrixCursor Cursor2= new MatrixCursor(columns);
            alc.add(null);
            alc.add(null);


            try{
                String maxQuery = Query ;
                //execute the query results will be save in Cursor c
                Cursor c = sqlDB.rawQuery(maxQuery, null);


                //add value to cursor2
                Cursor2.addRow(new Object[] { "Success" });

                alc.set(1,Cursor2);
                if (null != c && c.getCount() > 0) {


                    alc.set(0,c);
                    c.moveToFirst();

                    return alc ;
                }
                return alc;
            } catch(SQLException sqlEx){
                Log.d("printing exception", sqlEx.getMessage());
                //if any exceptions are triggered save the error message to cursor an return the arraylist
                Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
                alc.set(1,Cursor2);
                return alc;
            } catch(Exception ex){

                Log.d("printing exception", ex.getMessage());

                //if any exceptions are triggered save the error message to cursor an return the arraylist
                Cursor2.addRow(new Object[] { ""+ex.getMessage() });
                alc.set(1,Cursor2);
                return alc;
            }


        }
    }//end class

