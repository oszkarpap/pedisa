/**package hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.repository.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.right.medication.Medication;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.repository.Repository;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This is class the repo to SQLite DB



public class SQLiteRepository implements Repository {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    /**
     * save new medication memory in own device DB


    @Override
    public void saveMemory(Medication memory) {
        ContentValues values = new ContentValues();
        values.put(DBConstants.MEMORY_MODEL.NAME, memory.getName());
        values.put(DBConstants.MEMORY_MODEL.AGENT, memory.getAgent());
        values.put(DBConstants.MEMORY_MODEL.PACK, memory.getPack());
        values.put(DBConstants.MEMORY_MODEL.IND, memory.getInd());
        values.put(DBConstants.MEMORY_MODEL.CONTRA, memory.getCont());
        values.put(DBConstants.MEMORY_MODEL.ADULT, memory.getAdult());
        values.put(DBConstants.MEMORY_MODEL.CHILD, memory.getChild());

        db.insert(DBConstants.TABLE_MEMORY, null, values);
    }


    /**
     * load all medication memory from own deivce DB

    @Override
    public List<Medication> getAllMemory() {

        Cursor cursor = db.query(DBConstants.TABLE_MEMORY,
                new String[]{
                        DBConstants.MEMORY_MODEL.ID,
                        DBConstants.MEMORY_MODEL.NAME,
                        DBConstants.MEMORY_MODEL.AGENT,
                        DBConstants.MEMORY_MODEL.PACK,
                        DBConstants.MEMORY_MODEL.IND,
                        DBConstants.MEMORY_MODEL.CONTRA,
                        DBConstants.MEMORY_MODEL.ADULT,
                        DBConstants.MEMORY_MODEL.CHILD
                }, null, null, null, null, DBConstants.MEMORY_MODEL.NAME);

        List<Medication> memoriesLoaded = new ArrayList<>(cursor.getCount());

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(DBConstants.MEMORY_MODEL.ID));
            String name = cursor.getString(cursor.getColumnIndex(DBConstants.MEMORY_MODEL.NAME));
            String agent = cursor.getString(cursor.getColumnIndex(DBConstants.MEMORY_MODEL.AGENT));
            String pack = cursor.getString(cursor.getColumnIndex(DBConstants.MEMORY_MODEL.PACK));
            String ind = cursor.getString(cursor.getColumnIndex(DBConstants.MEMORY_MODEL.IND));
            String contra = cursor.getString(cursor.getColumnIndex(DBConstants.MEMORY_MODEL.CONTRA));
            String adult = cursor.getString(cursor.getColumnIndex(DBConstants.MEMORY_MODEL.ADULT));
            String child = cursor.getString(cursor.getColumnIndex(DBConstants.MEMORY_MODEL.CHILD));

            Medication memory = new Medication(id, name, agent, pack, ind, contra, adult, child,mater, store);
            memoriesLoaded.add(memory);


        }

        cursor.close();
        return memoriesLoaded;
    }

    /**
     * delete selected medication memory in DB


    @Override
    public void deleteMemory(Medication memory) {
        Long id = memory.getId();
        if (id != null) {
            db.delete(DBConstants.TABLE_MEMORY, DBConstants.MEMORY_MODEL.ID + "=" + memory.getId(), null);
        }
    }


    /**
     * delete DB table

    @Override
    public void deleteAllMemory() {
        db.execSQL(DBConstants.DROP_TABLE);
    }

    /**
     * create new SQLiteOpenHelper

    @Override
    public void open(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        dbHelper.onCreate(db);
    }

    /**
     * close current SQLiteOpenHelper

    @Override
    public void close() {
        dbHelper.close();
    }
}
*/