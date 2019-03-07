package com.gera;

import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;

public class CsvOutputBuilder implements OutputBuilder {
	private String fileName;
	private DataSource dataSource;
	
	public CsvOutputBuilder(String fileName) {
		this.fileName = fileName;
	}	

	@SuppressWarnings("resource")
	@Override
	public void build() {
		@SuppressWarnings("resource")
		CSVWriter csvWriter;
		try {
			csvWriter = new CSVWriter(new FileWriter(fileName));
		} catch (IOException e) {
			throw new GenericException("Failed to create CSVWriter", e);
		}
		csvWriter.writeAll(dataSource, true);
	} 

	@Override
	public OutputBuilder setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		return this;
	}
}
