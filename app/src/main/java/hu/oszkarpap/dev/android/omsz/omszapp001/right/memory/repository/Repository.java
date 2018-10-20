package hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.repository;

import android.content.Context;

import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.Memory;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This interface implement SQLiteRepository method
 */

public interface Repository {
    void saveMemory(Memory memory);
    List<Memory> getAllMemory();
    void deleteMemory(Memory memory);
    void deleteAllMemory();
    void open(Context context);
    void close();
}
