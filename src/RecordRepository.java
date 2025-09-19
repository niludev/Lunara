import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecordRepository {

    private final Path path;

    public RecordRepository(Path path) {
        this.path = path;
    }

    public void ensureCsvHeader(String[] headers) {
        Path parentPath = this.path.getParent();
        String HeadersString = String.join(",", headers);
        try {
            if (parentPath != null) {
                Files.createDirectories(parentPath);
            }

            if (Files.notExists(this.path)) {
                Files.write(this.path, (HeadersString + "\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            } else {
                Long size = Files.size(this.path);
                if (size == 0) {
                    Files.write(this.path, (HeadersString + "\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                }
            }

        } catch (IOException e) {
            System.err.println("Error while trying to write file: " + e.getMessage());
        }
    }

    public String findById(Integer id) {

        try {
            List<String> lines = Files.readAllLines(this.path);
            List<String> dataLines = lines.subList(1, lines.size());

            for (String dataLine : dataLines) {
                String[] parts = dataLine.split(",");

                if (parts.length > 0) {
                    String lineIdString = parts[0].trim();
                    int lineId = Integer.parseInt(lineIdString);

                    if (lineId == id) {
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
                    
//                    if (record == null) {
//                        System.out.println("there is no record");
//                        return null;
//                    }

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

    public void printAllRecords() {
        try {
            List<String> lines = Files.readAllLines(this.path);
            List<String> dataLines = lines.subList(1, lines.size());

            for (String dataLine : dataLines) {
                System.out.println(dataLine);
            }

        } catch (Exception e) {
            System.err.println("Error while trying to read file: " + e.getMessage());
        }

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

        if (line.isEmpty()) {
            return null;
        }

        String[] csvLine = line.split(",");

        if (line.isEmpty()) {
            return null;
        } else  {
            String idString = csvLine[0].trim();
            Integer id = Integer.parseInt(idString);
            LocalDate date = LocalDate.parse(csvLine[1].trim());
            Integer painIntensity = Integer.parseInt(csvLine[2].trim());
            Integer mood = Integer.parseInt(csvLine[3].trim());

            Record newRecord = new Record(id, date, painIntensity, mood);
            return newRecord;
        }
    }

    public void createCSVRecord(Record record) {
        try {
            List<String> lines = Files.readAllLines(this.path);
            List<String> dataLines = lines.subList(1, lines.size());

            Integer sizeOfLines = dataLines.size();

            Integer newId = null;

            if (sizeOfLines == 0) {
                System.out.println("File does not have any Records!");
                record.setId(1);

                String csvLine = record.toString();
                writeLine(this.path.toString(), csvLine);

            } else {
                newId = Integer.parseInt(dataLines.getLast().split(",")[0]) +1;
                record.setId(newId);

                String csvLine = record.toString();
                writeLine(this.path.toString(), csvLine);
            }

        } catch (IOException e) {
            System.err.println("Error while trying to read file: " + e.getMessage());
        }
    }

    private List<String> getAllLines() throws IOException {
        List<String> lines = Files.readAllLines(this.path);
        return lines;
    }

    private String findLineById(String id) throws IOException {
        List<String> lines = this.getAllLines();
        List<String> dataLines = lines.subList(1, lines.size());

        String foundedDataLine = "";

        for (String dataLine : dataLines) {
            String[] parts = dataLine.split(",");

            if (parts.length > 0) {
                String lineId = parts[0].trim();

                if (lineId.equals(id)) {
                    foundedDataLine = dataLine;
                    break;
                }
            }
        }

        if (foundedDataLine.isEmpty()) {
            System.err.println("No record found with ID: " + id);
            return null;
        }

        return foundedDataLine;
    }

    public void updateRecord(String id, Record record) {
        try {
            List<String> lines = this.getAllLines();
            String foundedDataLine = this.findLineById(id);
            Record newRecord = fromCsvLine(foundedDataLine);
            newRecord.setDate(record.getDate());
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
            List<String> lines = this.getAllLines();
            String lineToBeDeleted = this.findLineById(id);

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
