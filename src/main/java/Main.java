import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException  {
        String inputPath = "C:\\Users\\Admin\\IdeaProjects\\monitoring\\src\\main\\java\\Таблицы\\Мониторинг";
        String tablePath = "C:/Users/Admin/IdeaProjects/monitoring/src/main/java/Таблицы/allAPKF.csv";

        try (CSVReader csvReader = new CSVReader(new FileReader(tablePath))) {
            List<String[]> allRows = csvReader.readAll();
            allRows.remove(0);       //удаляет заголовок
            for (String[] row : allRows) {
                String number = row[2];
                String ip = row[5];
                String certificate = row[6];
                String fromDateCert = row[7];
                String toDateCert = row[8];
                String speed = row[9];
                String address = row[12];
                String coordinate = row[17];
                //добавляет созданный комплекс в список
                new APKF(number, ip, certificate, fromDateCert, toDateCert, speed, address, coordinate).putAPKF();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Monitoring monitoring = new Monitoring(inputPath);
        HashMap<APKF, Integer> map = monitoring.getEmpty();
        map.entrySet().stream()
                    .sorted(Map.Entry.<APKF, Integer>comparingByValue().reversed())
                    .forEach(System.out::println);
        monitoring.output();
    }

}
