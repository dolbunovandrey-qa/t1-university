package config;

import java.math.BigDecimal;

public class TestConfig {
    private String webUrl;
    private String apiUrl;
    private long timeout;
    private String logging;
    private String adminUsername;
    private String adminPassword;
    private String startProductName;
    private BigDecimal startProductPrice;
    private String browser;
    private String browserSize;
    private boolean headless;
    private String invalidUsername;
    private String catalogPath;
    private String adminPath;
    private String goodsListPath;
    private String goodsAddPath;
    private String goodsByIdPath;
    private int listPage;
    private int listSize;
    private int smallListSize;
    private int defaultQuantity;
    private int orderQuantity;
    private int overLimitQuantity;
    private BigDecimal orderLimit;
    private BigDecimal firstCartPrice;
    private BigDecimal secondCartPrice;
    private int firstCartQuantity;
    private int secondCartQuantity;
    private BigDecimal newProductPrice;
    private BigDecimal updatedProductPrice;
    private String updatedProductNamePrefix;
    private String shopTitle;
    private String orderTitle;
    private String orderNotification;
    private String productAddedNotification;
    private String productUpdatedMessageTemplate;
    private String addedToCartMessageTemplate;
    private String invalidCredentialsMessage;
    private String orderLimitMessageFragment;
    private String emptyCartText;
    private String apiSuccessMessage;
    private String apiDuplicateMessageTemplate;
    private String apiNotFoundMessageTemplate;
    private String apiNotFoundBodyTemplate;
    private String apiDeletedBodyTemplate;
    private BigDecimal moneyTolerance;

    public String getWebUrl() { return webUrl; }
    public void setWebUrl(String value) { webUrl = value; }

    public String getApiUrl() { return apiUrl; }
    public void setApiUrl(String value) { apiUrl = value; }

    public long getTimeout() { return timeout; }
    public void setTimeout(long value) { timeout = value; }

    public String getLogging() { return logging; }
    public void setLogging(String value) { logging = value; }

    public String getAdminUsername() { return adminUsername; }
    public void setAdminUsername(String value) { adminUsername = value; }

    public String getAdminPassword() { return adminPassword; }
    public void setAdminPassword(String value) { adminPassword = value; }

    public String getStartProductName() { return startProductName; }
    public void setStartProductName(String value) { startProductName = value; }

    public BigDecimal getStartProductPrice() { return startProductPrice; }
    public void setStartProductPrice(BigDecimal value) { startProductPrice = value; }

    public String getBrowser() { return browser; }
    public void setBrowser(String value) { browser = value; }

    public String getBrowserSize() { return browserSize; }
    public void setBrowserSize(String value) { browserSize = value; }

    public boolean getHeadless() { return headless; }
    public void setHeadless(boolean value) { headless = value; }

    public String getInvalidUsername() { return invalidUsername; }
    public void setInvalidUsername(String value) { invalidUsername = value; }

    public String getCatalogPath() { return catalogPath; }
    public void setCatalogPath(String value) { catalogPath = value; }

    public String getAdminPath() { return adminPath; }
    public void setAdminPath(String value) { adminPath = value; }

    public String getGoodsListPath() { return goodsListPath; }
    public void setGoodsListPath(String value) { goodsListPath = value; }

    public String getGoodsAddPath() { return goodsAddPath; }
    public void setGoodsAddPath(String value) { goodsAddPath = value; }

    public String getGoodsByIdPath() { return goodsByIdPath; }
    public void setGoodsByIdPath(String value) { goodsByIdPath = value; }

    public int getListPage() { return listPage; }
    public void setListPage(int value) { listPage = value; }

    public int getListSize() { return listSize; }
    public void setListSize(int value) { listSize = value; }

    public int getSmallListSize() { return smallListSize; }
    public void setSmallListSize(int value) { smallListSize = value; }

    public int getDefaultQuantity() { return defaultQuantity; }
    public void setDefaultQuantity(int value) { defaultQuantity = value; }

    public int getOrderQuantity() { return orderQuantity; }
    public void setOrderQuantity(int value) { orderQuantity = value; }

    public int getOverLimitQuantity() { return overLimitQuantity; }
    public void setOverLimitQuantity(int value) { overLimitQuantity = value; }

    public BigDecimal getOrderLimit() { return orderLimit; }
    public void setOrderLimit(BigDecimal value) { orderLimit = value; }

    public BigDecimal getFirstCartPrice() { return firstCartPrice; }
    public void setFirstCartPrice(BigDecimal value) { firstCartPrice = value; }

    public BigDecimal getSecondCartPrice() { return secondCartPrice; }
    public void setSecondCartPrice(BigDecimal value) { secondCartPrice = value; }

    public int getFirstCartQuantity() { return firstCartQuantity; }
    public void setFirstCartQuantity(int value) { firstCartQuantity = value; }

    public int getSecondCartQuantity() { return secondCartQuantity; }
    public void setSecondCartQuantity(int value) { secondCartQuantity = value; }

    public BigDecimal getNewProductPrice() { return newProductPrice; }
    public void setNewProductPrice(BigDecimal value) { newProductPrice = value; }

    public BigDecimal getUpdatedProductPrice() { return updatedProductPrice; }
    public void setUpdatedProductPrice(BigDecimal value) { updatedProductPrice = value; }

    public String getUpdatedProductNamePrefix() { return updatedProductNamePrefix; }
    public void setUpdatedProductNamePrefix(String value) { updatedProductNamePrefix = value; }

    public String getShopTitle() { return shopTitle; }
    public void setShopTitle(String value) { shopTitle = value; }

    public String getOrderTitle() { return orderTitle; }
    public void setOrderTitle(String value) { orderTitle = value; }

    public String getOrderNotification() { return orderNotification; }
    public void setOrderNotification(String value) { orderNotification = value; }

    public String getProductAddedNotification() { return productAddedNotification; }
    public void setProductAddedNotification(String value) { productAddedNotification = value; }

    public String getProductUpdatedMessageTemplate() { return productUpdatedMessageTemplate; }
    public void setProductUpdatedMessageTemplate(String value) { productUpdatedMessageTemplate = value; }

    public String getAddedToCartMessageTemplate() { return addedToCartMessageTemplate; }
    public void setAddedToCartMessageTemplate(String value) { addedToCartMessageTemplate = value; }

    public String getInvalidCredentialsMessage() { return invalidCredentialsMessage; }
    public void setInvalidCredentialsMessage(String value) { invalidCredentialsMessage = value; }

    public String getOrderLimitMessageFragment() { return orderLimitMessageFragment; }
    public void setOrderLimitMessageFragment(String value) { orderLimitMessageFragment = value; }

    public String getEmptyCartText() { return emptyCartText; }
    public void setEmptyCartText(String value) { emptyCartText = value; }

    public String getApiSuccessMessage() { return apiSuccessMessage; }
    public void setApiSuccessMessage(String value) { apiSuccessMessage = value; }

    public String getApiDuplicateMessageTemplate() { return apiDuplicateMessageTemplate; }
    public void setApiDuplicateMessageTemplate(String value) { apiDuplicateMessageTemplate = value; }

    public String getApiNotFoundMessageTemplate() { return apiNotFoundMessageTemplate; }
    public void setApiNotFoundMessageTemplate(String value) { apiNotFoundMessageTemplate = value; }

    public String getApiNotFoundBodyTemplate() { return apiNotFoundBodyTemplate; }
    public void setApiNotFoundBodyTemplate(String value) { apiNotFoundBodyTemplate = value; }

    public String getApiDeletedBodyTemplate() { return apiDeletedBodyTemplate; }
    public void setApiDeletedBodyTemplate(String value) { apiDeletedBodyTemplate = value; }

    public BigDecimal getMoneyTolerance() { return moneyTolerance; }
    public void setMoneyTolerance(BigDecimal value) { moneyTolerance = value; }
}
