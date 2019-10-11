package connect.shopping.akshay.kmnorth;

/**
 * Created by Akshay on 01-07-2017.
 */
public enum BuildType {
      DEV("https://www.kmnorth.xyz/", "akshay7272", true,true);
//    DEV("http://192.168.43.75/", "akshay7272", true, true);

    String API_VERSION = "";
    private final String OAUTH_KEY = "Authorization";
    private final boolean loggingEnabled;
    private final boolean preFilledForm;
    private final String serverUrl;
    private final String OAuthValue;

    BuildType(String serverUrl, String OAuthValue, boolean preFilledForm, boolean loggingEnabled) {
        this.serverUrl = serverUrl;
        this.preFilledForm = preFilledForm;
        this.loggingEnabled = loggingEnabled;
        this.OAuthValue = OAuthValue;
    }

    public boolean isPreFilledForm() {
        return preFilledForm;
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public String getServerUrl(WebServiceType webServiceType) {
        switch (webServiceType) {
            case REFRESH_TOKEN:
            case GENERATE_ACCESS_TOKEN:
                return serverUrl;
            default:
                return serverUrl + API_VERSION;
        }
    }

    public String getOAuthValue() {
        return OAuthValue;
    }

    public String getOAUTH_KEY() {
        return OAUTH_KEY;
    }
}
