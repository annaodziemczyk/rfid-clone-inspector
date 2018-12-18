package com.cit.core.csv;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by odziea on 12/18/2018.
 */
@Service
public class CSVReader implements ICSVReader{

    Logger logger = LoggerFactory.getLogger(CSVReader.class);

    @Override
    public <T> List<T> loadObjectList(Class<T> type, File file) {
        try {
            CsvMapper mapper = new CsvMapper();
//            CsvSchema schema = mapper.schemaFor(type);
//            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            CsvSchema schema = CsvSchema.builder()
                    .addColumn("airportId", CsvSchema.ColumnType.NUMBER)
                    .addColumn("airportName")
                    .addColumn("airportCity")
                    .addColumn("airportCountry")
                    .addColumn("iataCode")
                    .addColumn("icaoCode")
                    .addColumn("latitude", CsvSchema.ColumnType.NUMBER)
                    .addColumn("longitude", CsvSchema.ColumnType.NUMBER)
                    .addColumn("altitude", CsvSchema.ColumnType.NUMBER)
                    .addColumn("timezone", CsvSchema.ColumnType.NUMBER)
                    .addColumn("dst")
                    .addColumn("tzDatabaseTimeZone")
                    .addColumn("type")
                    .addColumn("source")
                    .build();

            MappingIterator<T> readValues =
                    mapper.reader(type).with(schema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            logger.error("Error occurred while loading object list from file " + file.getName(), e);
            return Collections.emptyList();
        }
    }
}
