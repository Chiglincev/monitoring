import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Monitoring {
    private static final int CELL = 25;

    private List<String[]> monitoring = new ArrayList<>();
    private HashMap<APKF, Integer> empty = new HashMap<>();

    Date dateNow = new Date();
    SimpleDateFormat HH = new SimpleDateFormat("HH");
    SimpleDateFormat DH = new SimpleDateFormat("yyyy MM.dd HH 'ч'");
    String time = HH.format(dateNow);
    String dayTime = DH.format(dateNow);
    String outputPath = "C:\\Users\\Admin\\IdeaProjects\\monitoring\\src\\main\\java\\Таблицы\\Мониторинг\\Невыгрузки";


    public Monitoring(String filePath) {
        File dir = new File(filePath);
        int hour = Integer.parseInt(time);

        for (File item : dir.listFiles()) {
            int count = 0;

            try (CSVReader csvReader = new CSVReader(new FileReader(item.getPath()), ',', '"', 1)) {
                List<String[]> allRows = csvReader.readAll();

                for (String[] row : allRows) {
                    count = 0;
                    String[] cutRow = new String[CELL];
                    cutRow[0] = row[3];
                    APKF apkf = APKF.getAPKF(row[3]);
                    //проходит по строке и находит пустые ячейки
                    for (int i = 1; i < 25; i++) {
                        if (!row[i + 4].isEmpty()) {
                            count = 0;
                            cutRow[i] = row[i + 4];
                        } else if (i <= hour) {
                            count++;
                        }
                    }
                    monitoring.add(cutRow);
                    //заполняет список проблемных комплексов
                    if (count > 2) {
                        empty.put(apkf, count);
                    } else if (empty.containsKey(apkf)) {
                        empty.remove(apkf);
                    }
                }
            } catch (IOException e) {
            }
        }
    }

    public void output() throws IOException{
        File csv = new File (outputPath, "Невыгрузки " + dayTime + ".csv");
        if (csv.exists()) {
            csv.delete();
        } else {
            csv.createNewFile();
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(csv, true), ';')) {
            for (Map.Entry<APKF, Integer> entry : empty.entrySet().stream().
                    sorted(Map.Entry.<APKF, Integer>comparingByValue().reversed()).
                    toList()) {
                APKF key = entry.getKey();
                int value = entry.getValue();
                StringBuilder out = new StringBuilder();
                out.append(key.toString()).append(";").append(value);
                String[] output = out.toString().split(";");
                writer.writeNext(output);
                Desktop.getDesktop().open(csv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<APKF, Integer> getEmpty() {
        return empty;
    }
}
