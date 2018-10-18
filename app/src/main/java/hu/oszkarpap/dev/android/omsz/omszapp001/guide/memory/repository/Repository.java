package hu.oszkarpap.dev.android.omsz.omszapp001.guide.memory.repository;

import android.content.Context;

import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.guide.memory.Memory;


public interface Repository {
    void saveMemory(Memory memory);
    List<Memory> getAllMemory();
    void deleteMemory(Memory memory);
    void deleteAllMemory();
    void open(Context context);
    void close();
}
