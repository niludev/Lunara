import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecordRepository {

    private final Path path;

    public RecordRepository(Path path) {
        this.path = path;
    }

    public void ensureCsvHeader(String headers) {
        Path parentPath = this.path.getParent();

        try {
            if (parentPath != null) {
                Files.createDirectories(parentPath);
            }

            if (Files.notExists(this.path)) {
                Files.write(this.path, headers.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            } else {
                Long size = Files.size(this.path);
                if (size == 0) {
                    Files.write(this.path, headers.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                }
            }

        } catch (IOException e) {
            System.err.println("Error while trying to write file: " + e.getMessage());
        }
    }

    public String findById(String id) {

        try {
            List<String> lines = Files.readAllLines(this.path);
            List<String> dataLines = lines.subList(1, lines.size());

            for (String dataLine : dataLines) {
                String[] parts = dataLine.split(",");

                if (parts.length > 0) {
                    String lineId = parts[0].trim();

                    if (lineId.equals(id)) {
                        return dataLine;
                    }
                }

            }

        } catch (Exception e) {
            System.err.println("Error while trying to read file: " + e.getMessage());
        }
        return null;
    }

    public List<Record> readAllRecords() {
        List<Record> records = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(this.path);

            if (lines.size() <= 1) {
                return records;
            }

            List<String> dataLines = lines.subList(1, lines.size());

            for (String dataLine : dataLines) {
                try {
                    Record record = fromCsvLine(dataLine);
                    records.add(record);
                } catch (Exception e) {
                    System.err.println("Error while trying to read file: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Error while trying to read file: " + e.getMessage());
        }

        return records;
    }

    public void writeLine(String path, String line) {
        try {
            Files.write(Paths.get(path), (line + "\n").getBytes(), StandardOpenOption.APPEND);
//            Files.write(Paths.get(path), "\n".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Record fromCsvLine(String line) {
        String[] csvLine = line.split(",");

        String id = csvLine[0].trim();
        String username = csvLine[1].trim();
        LocalDate startDate = LocalDate.parse(csvLine[2].trim());
        LocalDate endDate = LocalDate.parse(csvLine[3].trim());
        Integer painIntensity = Integer.parseInt(csvLine[4].trim());
        Integer mood = Integer.parseInt(csvLine[5].trim());

        Record newRecord = new Record(id, username, startDate, endDate, painIntensity, mood);
        return newRecord;
    }

    public void createCSVRecord(Record record) {
        try {
            List<String> lines = Files.readAllLines(this.path);
//            List<String> dataLines = lines.subList(1, lines.size());
//
//            Integer sizeOfLines = dataLines.size();

            LocalDate currentDateTime = LocalDate.now();

            String newId = currentDateTime.toString();

            record.setId(newId);
            String csvLine = record.toString();
            writeLine(this.path.toString(), csvLine);

//            if (sizeOfLines == 0) {
//                System.out.println("File does not have any Records!");
//                record.setId(0);
//            } else {
//                Integer newId = sizeOfLines + 1;
//                record.setId(newId);
//
//                String csvLine = record.toString();
//                writeLine(this.path.toString(), csvLine);
//            }

        } catch (IOException e) {
            System.err.println("Error while trying to read file: " + e.getMessage());
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

    public void updateRecord(String id, Record record) {
        try {
            List<String> lines = Files.readAllLines(this.path);
            List<String> dataLines = lines.subList(1, lines.size());

            String foundedDataLine = "";
            String foundedId = "";

            for (String dataLine : dataLines) {
                String[] parts = dataLine.split(",");

                if (parts.length > 0) {
                    String lineId = parts[0].trim();

                    if (lineId.equals(id)) {
                        foundedId = lineId;
                        foundedDataLine = dataLine;
                        break;
                    }
                }
            }

            if (foundedDataLine.isEmpty()) {
                System.err.println("No record found with ID: " + id);
                return;
            }

            Record newRecord = fromCsvLine(foundedDataLine);
            newRecord.setId(foundedId);
            newRecord.setUsername(record.getUsername());
            newRecord.setStartDate(record.getStartDate());
            newRecord.setEndDate(record.getEndDate());
            newRecord.setPainIntensity(record.getPainIntensity());
            newRecord.setMood(record.getMood());

            String updatedDataLine = newRecord.toString();

            int index = lines.indexOf(foundedDataLine);
            lines.set(index, updatedDataLine);

            Files.write(this.path, lines, StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.err.println("Error while trying to read file: " + e.getMessage());
        }
    }

    public void deleteRecord(String id) {
        try {
            List<String> lines = Files.readAllLines(this.path);
            List<String> dataLines = lines.subList(1, lines.size());

            String lineToBeDeleted = "";

            for (String dataLine : dataLines) {
                String[] parts = dataLine.split(",");

                if (parts.length > 0) {
                    String lineId = parts[0].trim();

                    if (lineId.equals(id)) {
                        lineToBeDeleted = dataLine;
                        break;
                    }
                }
            }

            if (lineToBeDeleted.isEmpty()) {
                System.err.println("No record found with ID: " + id);
                return;
            }

//            lines.remove(lineToBeDeleted);

            List<String> updatedLines = new ArrayList<>();
            updatedLines.add(lines.getFirst());

            for (String line : lines.subList(1, lines.size())) {
                if (!line.trim().equals(lineToBeDeleted.trim())) {
                    updatedLines.add(line);
                }
            }

            Files.write(this.path, updatedLines, StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.err.println("Error while trying to read file: " + e.getMessage());
        }
    }
}
