package com.gera;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResultSetStringIterator implements Iterator<String[]> {

    private ResultSet result;
    private ResultSetMetaData meta;
    private boolean hasNext;
    private boolean isTitlesLine = true;

    public ResultSetStringIterator( ResultSet result ) throws SQLException {
        this.result = result;
        meta = result.getMetaData();
        hasNext = result.next();
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public String[] next() {
        if (! hasNext) {
            throw new NoSuchElementException();
        }

        try {
            if (isTitlesLine) {
            	String[] next = new String[meta.getColumnCount()];
                for (int i = 0; i < meta.getColumnCount(); i++) {
                	String column = meta.getColumnName(i+1);
                    next[i] = column;
                }
                isTitlesLine = false;
                return next;
            }
            
        	String[] next = new String[meta.getColumnCount()];
            for (int i = 0; i < meta.getColumnCount(); i++) {
                String value = result.getObject(i+1).toString();
                next[i] = value;
            }
            hasNext = result.next();
            return next;
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}