package api.constants;

import config.ConfigReader;

public class ApiEndpoints {
    public static final String GOODS_LIST = ConfigReader.load().getGoodsListPath();
    public static final String GOODS_ADD = ConfigReader.load().getGoodsAddPath();
    public static final String GOODS_ID = ConfigReader.load().getGoodsByIdPath();
    private ApiEndpoints() { }
}
