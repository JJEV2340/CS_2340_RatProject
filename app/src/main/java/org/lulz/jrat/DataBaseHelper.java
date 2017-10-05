package org.lulz.jrat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper
{
    private static String TAG = "DataBaseHelper"; // Tag just for the LogCat window
    //destination path (location) of our database on device
    private static String DB_PATH = "/data/data/org.lulz.jrat/databases/";
    private static String DB_NAME ="ratDB";// Database name
    private SQLiteDatabase mDataBase;
    private final Context mContext;

    public DataBaseHelper(Context context)
    {
        super(context, DB_NAME, null, 1);// 1? Its database Version
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    public void createDataBase() throws IOException
    {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            this.getReadableDatabase();
            this.close();
            try
            {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            }
            catch (IOException mIOException)
            {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    //Copy the database from assets
    private void copyDataBase() throws IOException
    {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    // Query users
    public String[] getUniqueIDs() {
        List<String> uniqueIDList = new LinkedList<String>();
        Cursor cur = mDataBase.rawQuery("select unique_key from ratData;",null);
        cur.moveToFirst();
        while(cur.isAfterLast() == false) {
            uniqueIDList.add("Sighting ID: " + cur.getString(0));
            cur.moveToNext();
        }
        cur.close();
        return uniqueIDList.toArray(new String[uniqueIDList.size()]);
    }

    public RatSighting generateSighting(String keyVal) {
        Long uniqueKey = null;
        String createdDate = null;
        String locationType = null;
        Long incidentZip = null;
        String incidentAddress = null;
        String city = null;
        String borough = null;
        Double latitude = null;
        Double longitude = null;
        Cursor cur = mDataBase.rawQuery("select * from ratData where unique_key=" + keyVal, null);
        cur.moveToFirst();
        while(cur.isAfterLast() == false) {
            uniqueKey = Long.parseLong(cur.getString(0));
            createdDate = cur.getString(1);
            locationType =  cur.getString(2);
            incidentZip = Long.parseLong(cur.getString(3));
            incidentAddress = cur.getString(4);
            city = cur.getString(5);
            borough = cur.getString(6);
            latitude = Double.parseDouble(cur.getString(7));
            longitude = Double.parseDouble(cur.getString(8));
            cur.moveToNext();
        }
        return new RatSighting(uniqueKey, createdDate, locationType, incidentZip, incidentAddress,
                               city, borough, latitude, longitude);
    }

    @Override
    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}