package ru.itmo.lab4.mental;

import ru.itmo.lab4.mental.exception.NoMemoriesLeftException;

public interface Remembering {
    Memory nextMemory() throws NoMemoriesLeftException;

    int getMemoriesLeftCount();
}
