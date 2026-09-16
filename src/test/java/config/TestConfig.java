package config;

public class TestConfig {

    private String webUrl;
    private String apiUrl;
    private long timeout;
    private String logging;
    private String adminUsername;
    private String adminPassword;
    private String startProductName;
    private double startProductPrice;

    public String getWebUrl() {
        return webUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public long getTimeout() {
        return timeout;
    }

    public String getLogging() {
        return logging;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public String getStartProductName() {
        return startProductName;
    }

    public double getStartProductPrice() {
        return startProductPrice;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setLogging(String logging) {
        this.logging = logging;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void setStartProductName(String startProductName) {
        this.startProductName = startProductName;
    }

    public void setStartProductPrice(double startProductPrice) {
        this.startProductPrice = startProductPrice;
    }
}