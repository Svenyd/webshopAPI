package nl.hsleiden;

import com.google.inject.Module;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.hubspot.dropwizard.guice.GuiceBundle.Builder;
import nl.hsleiden.model.User;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class WebShopAPI_Application extends Application<WebShopApi_Configuration> {

    private final Logger logger = LoggerFactory.getLogger(WebShopAPI_Application.class);

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

    private void setupAuthentication(Environment environment) {
        AuthenticationService authenticationService = guiceBundle.getInjector().getInstance(AuthenticationService.class);
        ApiUnauthorizedHandler unauthorizedHandler = guiceBundle.getInjector().getInstance(ApiUnauthorizedHandler.class);

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(authenticationService)
                .setAuthorizer(authenticationService)
                .setRealm("SUPER SECRET STUFF")
                .setUnauthorizedHandler(unauthorizedHandler)
                .buildAuthFilter()
        ));

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    @Override
    public void run(WebShopApi_Configuration webShopApi_configuration, Environment environment) throws Exception {
        name = webShopApi_configuration.getApiName();

        MariaDB.getInstance().connect("84.105.145.191", "WebShop", "Svenyd", "Kickbox123!");

        logger.info(String.format("Set API name to %s", name));

        setupAuthentication(environment);

//        SslConfigurator sslConfigurator = SslConfigurator.newInstance();
//        sslConfigurator.trustStoreFile("WebShop.keystore")
//                .trustStorePassword("crimson");
//        SSLContext sslContext = sslConfigurator.createSSLContext();
//        Client client = ClientBuilder
//                .newBuilder()
//                .sslContext(sslContext)
//                .build();
    }

    public static void main(final String[] args) throws Exception {
        new WebShopAPI_Application().run(args);
    }
}
