package com.gera;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Iterator;

public abstract class DBDataSource implements DataSource {
	private String url;
	private String user;
	private String password;
	private String statement;
	
	public DBDataSource(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	public DBDataSource setStatement(String statement) {
		this.statement = statement;
		return this;
	}

	public DBDataSource setOutputBuilder(OutputBuilder outputBuilder) {
		return this;
	}
	
	public abstract void setPreparedStatementParameters(PreparedStatement preparedStatement) throws SQLException, ParseException;
	
	@SuppressWarnings("rawtypes")
	@Override
	public Iterator iterator() {
		try (Connection conn = DriverManager.getConnection(url, user, password);) 
		{
			PreparedStatement preparedStatement = conn.prepareStatement( statement );
			setPreparedStatementParameters(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();
			return new ResultSetStringIterator(resultSet);
		} catch (Throwable e) {
			throw new GenericException("Failed to create DBDataSource.iterator ", e);
		}
	}
}