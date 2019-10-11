package connect.shopping.akshay.kmnorth;

import android.util.Log;

/**
 * Created by Akshay on 01-07-2017.
 */
public class DevConfig {
    /**
     * sets which server build it is
     */
    public static final BuildType buildType = BuildType.DEV;
    /**
     * Set it to true to enable forms in application pre-filled with data
     */
    public static final boolean PRE_FILLED_FORM = buildType.isPreFilledForm();

    /**
     * Set it to true to enable logging. If set to false logging will be decided
     * using {@link Log#isLoggable(String, int)}
     */
    public static final boolean LOGGING_ENABLED = buildType.isLoggingEnabled();

//  TODO:  change query log to false in manifest for signed build

}
