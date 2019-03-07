package com.gera;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryExecutor {

	String fromTimestampStr = null;

	public QueryExecutor(String[] args) throws ParseException {
		initializeMembersByCommandlineArguments(args);
	}

	public void initializeMembersByCommandlineArguments(String[] args) throws ParseException {
		Options options = new Options();
		Option propertyOption = Option.builder().longOpt("D").argName("property=value").hasArgs().valueSeparator().numberOfArgs(2).desc("use value for given properties").build();
		options.addOption(propertyOption);
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		if (cmd.hasOption("D")) {
			Properties properties = cmd.getOptionProperties("D");
			fromTimestampStr = properties.getProperty("from");
			System.out.println("fromTimestamp: " + fromTimestampStr);
		}
	}
	
	public void execute() {
		try {
			new CsvOutputBuilder(System.getenv("EXERCISE_HOME") + "/outputs/query_result.csv")
				.setDataSource( new DBDataSource("jdbc:postgresql://hh-pgsql-public.ebi.ac.uk:5432/pfmegrnargs", "reader", "NWDMCE5xdipIjRrp") {
									public void setPreparedStatementParameters(PreparedStatement preparedStatement) throws SQLException, java.text.ParseException {
											Timestamp fromTimestamp = new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(fromTimestampStr).getTime());
											preparedStatement.setTimestamp(1, fromTimestamp);
									}
								}.setStatement("select dbid, created, last, upi, version_i, deleted, timestamp, userstamp, ac, version, taxid, id from xref where timestamp > ?") )
			.build();
		
		} catch (Throwable t) {
			System.out.println(ExceptionUtils.getStackTrace(t));
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, ParseException, java.text.ParseException, IOException, SQLException {
		new QueryExecutor(args).execute();
	}
}