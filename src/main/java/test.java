import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class test {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDateTime = now.format(formatter);
//        String formattedDateTime = "20:00";
        System.out.println("Current Date and Time: " + formattedDateTime);
        String[] timeSplit = formattedDateTime.split(":");
        int timeMinute = Integer.parseInt(timeSplit[0]) * 60 + Integer.parseInt(timeSplit[1]);
        System.out.println("Current Time Second: " + timeMinute);
        int timeMc = 18000+(timeMinute * 50 / 3);
        System.out.println("Current Time Mc: " + timeMc);
    }

}
