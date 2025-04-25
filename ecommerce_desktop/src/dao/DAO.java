package dao;

import exception.DBException;
import java.util.List;

public interface DAO<T> {
    void add(T entity) throws DBException;
    T getById(int id) throws DBException;
    List<T> getAll() throws DBException;
    void update(T entity) throws DBException;
    void delete(int id) throws DBException;
}
