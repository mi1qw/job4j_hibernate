package com.example.hiber.persistence;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CrudPersist<T> implements CRUDStore<T>, SessionStore {
    private final Class<T> aClass;
    private final String className;
    private Method setId;

    public CrudPersist(final Class<T> aClass) {
        this.aClass = aClass;
        this.className = aClass.getSimpleName();
        setMethodSetId();
    }

    private void setMethodSetId() {
        try {
            setId = aClass.getDeclaredMethod("setId", Long.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void setIdmethod(final Long id, final T obj) {
        try {
            setId.invoke(obj, id);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T add(final T item) {
        return tx(session -> {
            session.persist(item);
            return item;
        });
    }

    /**
     * replace.
     * session.get with catch ObjectNotFoundException
     * change by get()
     *
     * @param id   id
     * @param item item
     * @return return
     */
    @Override
    public boolean replace(final Long id, final T item) {
        return tx(session -> {
            try {
                T oldItem = session.get(aClass, id);
                int i = oldItem.hashCode();
            } catch (Exception e) {
                return false;
            }
            setIdmethod(id, item);
            session.merge(item);
            return true;
        });
    }


    @Override
    public boolean delete(final Long id) {
        try {
            int del = tx(session -> session
                    .createQuery("delete from " + className + " where id=:id", Integer.class)
                    .setParameter("id", id)
                    .executeUpdate());
            return del != 0;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public T findById(final Long id) {
        try {
            return tx(
                    session -> session
                            .createQuery("from " + className + " where id=:id", aClass)
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public List<T> findAll() {
        try {
            return tx(session -> session
                    .createQuery("from " + className, aClass)
                    .list());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    @Override
    public List<T> findByName(final String name) {
        try {
            return tx(session -> session
                    .createQuery("from " + className + " a where a.name=:name", aClass)
                    .setParameter("name", name)
                    .list());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    private String getNameEntity() {
        return tx(session -> session.getMetamodel().entity(aClass).getName());
    }
}
