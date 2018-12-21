import com.google.inject.Module;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.hubspot.dropwizard.guice.GuiceBundle.Builder;

public class WebShopAPI_Application extends Application<WebShopApi_Configuration> {

    private ConfiguredBundle assetsBundle;
    private GuiceBundle guiceBundle;

    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void initialize(Bootstrap<WebShopApi_Configuration> bootstrap) {
        assetsBundle = (ConfiguredBundle) new ConfiguredAssetsBundle("/assets/", "/client", "index.html");
        guiceBundle = createGuiceBundle(WebShopApi_Configuration.class, new ApiGuiceModule());

        bootstrap.addBundle(assetsBundle);
        bootstrap.addBundle(guiceBundle);
    }

    private GuiceBundle createGuiceBundle(Class<WebShopApi_Configuration> configurationClass, Module module) {
        Builder guiceBuilder = GuiceBundle.<WebShopApi_Configuration>newBuilder()
                .addModule(module)
                .enableAutoConfig(new String[] {"nl.hsleiden"})
                .setConfigClass(configurationClass);

        return guiceBuilder.build();
    }

    @Override
    public void run(WebShopApi_Configuration webShopApi_configuration, Environment environment) throws Exception {
        name = webShopApi_configuration.getApiName();
    }

    public static void main(final String[] args) throws Exception {
        new WebShopAPI_Application().run(args);
    }
}
