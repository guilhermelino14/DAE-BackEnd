package pt.ipleiria.estg.dei.ei.dae.daebackend.providers;

import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
public class CSVExporter<T1> {
    public String generateCSV(List<T1> aList, T1 aRecord) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        String header = buildHeader((Class<T1>) aRecord.getClass());
        StringWriter writer = new StringWriter();
        writer.append(header);
        ColumnPositionMappingStrategy<T1> mappingStrategy = new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType((Class<T1>) aRecord.getClass());
        mappingStrategy.setColumnMapping(header.split(","));
        StatefulBeanToCsv<T1> beanToCsv = new StatefulBeanToCsvBuilder<T1>(writer).withMappingStrategy(mappingStrategy)
                .withSeparator(',')
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();
        beanToCsv.write(aList);
        return writer.toString();
    }


    private String buildHeader(java.lang.Class<T1> aClass) {
        return Arrays.stream(aClass.getDeclaredFields())
                .filter(f -> f.getAnnotation(CsvBindByPosition.class) != null
                        && f.getAnnotation(CsvBindByName.class) != null)
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(",")) + "\n";
    }
}
