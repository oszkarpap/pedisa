package hu.oszkarpap.dev.android.omsz.omszapp001.memory.repository.sqlite;



import static hu.oszkarpap.dev.android.omsz.omszapp001.memory.repository.sqlite.DBConstants.MEMORY_MODEL.ADULT;
import static hu.oszkarpap.dev.android.omsz.omszapp001.memory.repository.sqlite.DBConstants.MEMORY_MODEL.AGENT;
import static hu.oszkarpap.dev.android.omsz.omszapp001.memory.repository.sqlite.DBConstants.MEMORY_MODEL.CHILD;
import static hu.oszkarpap.dev.android.omsz.omszapp001.memory.repository.sqlite.DBConstants.MEMORY_MODEL.CONTRA;
import static hu.oszkarpap.dev.android.omsz.omszapp001.memory.repository.sqlite.DBConstants.MEMORY_MODEL.ID;
import static hu.oszkarpap.dev.android.omsz.omszapp001.memory.repository.sqlite.DBConstants.MEMORY_MODEL.IND;
import static hu.oszkarpap.dev.android.omsz.omszapp001.memory.repository.sqlite.DBConstants.MEMORY_MODEL.NAME;
import static hu.oszkarpap.dev.android.omsz.omszapp001.memory.repository.sqlite.DBConstants.MEMORY_MODEL.PACK;

public class DBConstants {

    public static final String DATABASE_NAME= "memory.db";
    public static final int DATABASE_VERSION=1;

    public static final String TABLE_MEMORY = "memory";

    public static class MEMORY_MODEL{
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String AGENT = "agent";
        public static final String PACK = "pack";
        public static final String IND = "ind";
        public static final String CONTRA = "contra";
        public static final String ADULT = "adult";
        public static final String CHILD = "child";
    }

    public static final String CREATE_TABLE=
            "CREATE TABLE IF NOT EXISTS " + TABLE_MEMORY + " " +
                    "(" +
                    ID + " integer primary key autoincrement," +
                    NAME + " text not null," +
                    AGENT + " text," +
                    PACK + " text," +
                    IND + " text," +
                    CONTRA + " text," +
                    ADULT + " text," +
                    CHILD + " text" +
                    ");";

    public static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_MEMORY+" ;";

}
