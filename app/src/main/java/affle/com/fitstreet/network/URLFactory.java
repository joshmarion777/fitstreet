package affle.com.fitstreet.network;

/**
 * Created by root on 6/14/16.
 */
public enum URLFactory {
    BASE_URL(ServiceConstants.BASE_URL);

    private String url;

    URLFactory(String name) {
        this.url = name;
    }

    public String getURL() {
        return this.url;
    }
}
