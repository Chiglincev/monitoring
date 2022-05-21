import java.util.HashMap;
import java.util.Map;

public class APKF {
    private String number;
    private String ip;
    private String certificate;
    private String fromDateCert;
    private String toDateCert;
    private String speed;
    private String address;
    private String coordinate;

    private static Map<String, APKF> allAPKF = new HashMap<>();


    public APKF(String number, String ip, String certificate, String fromDateCert,
                String toDateCert, String speed, String address, String coordinate) {
        this.number = number;
        this.ip = ip;
        this.certificate = certificate;
        this.fromDateCert = fromDateCert;
        this.toDateCert = toDateCert;
        this.speed = speed;
        this.address = address;
        this.coordinate = coordinate;
    }

    public static APKF getAPKF(String number) {
        return allAPKF.get(number);
    }

    public void putAPKF() {
        allAPKF.put(this.number, this);
    }

    @Override
    public String toString() {
        return number + ";" + ip;
    }
}
