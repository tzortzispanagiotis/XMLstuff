package csvApacheCommonsExercise;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.Reader;

//simple application that uses Apache Commons csv to read a csv, parse it and create a second file
//excluding one column
public class csvExercise {
	private final static String FILE_PATH = "input.csv";
	static String[] HEADERS = {"name", "email", "phone"};

	public static void main(String[] args) throws IOException {
		try (
			Reader read = new FileReader(FILE_PATH);
			CSVParser csvParser = new CSVParser(read, CSVFormat.DEFAULT
//					.withHeader(HEADERS)
					.withFirstRecordAsHeader()
					.withIgnoreHeaderCase()
					.withTrim());
			CSVPrinter print = new CSVPrinter(new FileWriter("new.csv"), CSVFormat.DEFAULT.withHeader(HEADERS))
			
		) { 
			 for (CSVRecord csvRecord : csvParser) {
				  print.printRecord(csvRecord.get("name"),csvRecord.get("email"),csvRecord.get("phone"));
			 }
			
		}
		
	}
}	
