package DB;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Utility class to check internet connectivity.
 * Provides methods to test network connectivity with configurable timeouts.
 */
public class InternetConnectionChecker {

    private static final String[] TEST_URLS = {
            "https://www.google.com",
            "https://www.cloudflare.com",
            "https://www.github.com"
    };

    private static final int DEFAULT_CONNECT_TIMEOUT = 3000; // 3 seconds
    private static final int DEFAULT_READ_TIMEOUT = 3000; // 3 seconds

    /**
     * Check if internet is available by testing multiple URLs
     * 
     * @return true if any of the test URLs are reachable
     */
    public boolean isInternetAvailable() {
        return isInternetAvailable(DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * Check if internet is available with custom timeouts
     * 
     * @param connectTimeout connection timeout in milliseconds
     * @param readTimeout    read timeout in milliseconds
     * @return true if any of the test URLs are reachable
     */
    public boolean isInternetAvailable(int connectTimeout, int readTimeout) {
        for (String urlString : TEST_URLS) {
            if (testUrl(urlString, connectTimeout, readTimeout)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Test a specific URL for connectivity
     * 
     * @param urlString      URL to test
     * @param connectTimeout connection timeout in milliseconds
     * @param readTimeout    read timeout in milliseconds
     * @return true if URL is reachable
     */
    private boolean testUrl(String urlString, int connectTimeout, int readTimeout) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setRequestProperty("User-Agent", "POS-System/1.0");

            int responseCode = connection.getResponseCode();
            return (responseCode >= 200 && responseCode < 300);

        } catch (IOException e) {
            // Log the specific error for debugging
            System.err.println("Failed to connect to " + urlString + ": " + e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Asynchronously check internet connectivity
     * 
     * @return CompletableFuture that completes with connectivity status
     */
    public CompletableFuture<Boolean> isInternetAvailableAsync() {
        return CompletableFuture.supplyAsync(() -> isInternetAvailable());
    }

    /**
     * Check internet connectivity with a specific timeout
     * 
     * @param timeout timeout in milliseconds
     * @return true if internet is available within the timeout
     */
    public boolean isInternetAvailableWithTimeout(long timeout) {
        try {
            return isInternetAvailableAsync()
                    .get(timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            System.err.println("Internet connectivity check timed out: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get detailed connectivity information
     * 
     * @return ConnectivityInfo object with detailed status
     */
    public ConnectivityInfo getConnectivityInfo() {
        ConnectivityInfo info = new ConnectivityInfo();

        for (String url : TEST_URLS) {
            boolean reachable = testUrl(url, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
            info.addTestResult(url, reachable);
        }

        return info;
    }

    /**
     * Inner class to hold detailed connectivity information
     */
    public static class ConnectivityInfo {
        private final java.util.Map<String, Boolean> testResults = new java.util.HashMap<>();

        public void addTestResult(String url, boolean reachable) {
            testResults.put(url, reachable);
        }

        public boolean isAnyReachable() {
            return testResults.values().stream().anyMatch(reachable -> reachable);
        }

        public java.util.Map<String, Boolean> getTestResults() {
            return new java.util.HashMap<>(testResults);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Connectivity Info:\n");
            testResults.forEach((url, reachable) -> sb.append(url).append(": ")
                    .append(reachable ? "REACHABLE" : "UNREACHABLE").append("\n"));
            return sb.toString();
        }
    }
}
