package hu.oszkarpap.dev.android.omsz.omszapp001.memory.repository;

import android.content.Context;

import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.memory.model.Memory;


public interface Repository {
    void saveMemory(Memory memory);
    List<Memory> getAllMemory();
    void deleteMemory(Memory memory);
    void deleteAllMemory();
    void open(Context context);
    void close();
}
