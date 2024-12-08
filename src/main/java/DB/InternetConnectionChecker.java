package DB;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class InternetConnectionChecker {
    public InternetConnectionChecker(){}
    public boolean isInternetAvailable() {
        try {
            // Try to connect to a reliable website
            URL url = new URL("https://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);


            // Check the response code
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 299);
        } catch (IOException e) {
            return false;
        }
    }


}
