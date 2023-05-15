package at.ac.fhcampuswien.fhmdb.interfaces;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;

@FunctionalInterface
public interface ClickEventHandler<T> {
    void onClick(T t) throws DatabaseException;
}
