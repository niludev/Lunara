import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class RecordRepository {
    public static void writeLine(String path, String line) {
        try {
            Files.write(Paths.get(path), line.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(path), "\n".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public void createRecord(Record record) {
//        Record[] rs = readRecords();
//        Record lastRecord = rs[rs.length - 1];
//
//        Integer newId = lastRecord.getId() + 1;
//        record.setId(newId);
//
//        writeLine("...", record.toString());
//    }

    //public Record[] readRecords() {};

    //public Record readRecord(int id) {};

    public void updateRecord(int id, Record record) {}

    public void deleteRecord(int id) {}
}
