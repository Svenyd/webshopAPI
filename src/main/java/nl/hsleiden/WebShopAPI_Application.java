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
import nl.hsleiden.model.Product;
import nl.hsleiden.model.User;
import nl.hsleiden.persistence.ProductDAO;
import nl.hsleiden.persistence.UserDAO;
import nl.hsleiden.resource.ProductResource;
import nl.hsleiden.resource.UserResource;
import nl.hsleiden.service.ProductService;
import nl.hsleiden.service.UserService;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

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

    private void configureCors(Environment environment) {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }

    @Override
    public void run(WebShopApi_Configuration webShopApi_configuration, Environment environment) throws Exception {
        name = webShopApi_configuration.getApiName();

        MariaDB.getInstance().connect("84.105.145.191", "WebShop", "Svenyd", "Kickbox123!");

        logger.info(String.format("Set API name to %s", name));

        setupAuthentication(environment);
        configureCors(environment);

        environment.jersey().register(MultiPartFeature.class);

        //Register Resources
        environment.jersey().register(ProductResource.class);
        environment.jersey().register(UserResource.class);

        //Register Services
        UserService userService = guiceBundle.getInjector().getInstance(UserService.class);
        ProductService productService = guiceBundle.getInjector().getInstance(ProductService.class);

        //Register Dao
        ProductDAO productDAO = guiceBundle.getInjector().getInstance(ProductDAO.class);
        UserDAO userDAO = guiceBundle.getInjector().getInstance(UserDAO.class);

    }

    public static void main(final String[] args) throws Exception {
        new WebShopAPI_Application().run(args);
    }
}
