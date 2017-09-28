package com.alex.places.server.config;

import com.alex.places.server.PlaceFinderAppServiceImpl;
import com.alex.places.server.service.PlaceService;
import com.alex.places.server.service.PlaceServiceImpl;
import com.alex.places.server.thirdparty.annotations.GeocodingApiContext;
import com.alex.places.server.thirdparty.service.GooglePlacesService;
import com.alex.places.server.thirdparty.service.GooglePlacesServiceImpl;
import com.alex.places.server.thirdparty.annotations.PlaceApiContext;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.maps.GeoApiContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class PlaceFinderAppServletContextListener extends GuiceServletContextListener {
  private final Logger log = LoggerFactory.getLogger(PlaceFinderAppServletContextListener.class);

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new ServletModule() {
      private final String CONFIG_FILE = "application.properties";

      @Override
      protected void configureServlets() {
        install(new JpaPersistModule("placeFinderPersistence"));
        filter("/*").through(PersistFilter.class);

        serve("/placefinder/find").with(PlaceFinderAppServiceImpl.class);

        bindProperties();

        bind(PlaceService.class).to(PlaceServiceImpl.class);
        bind(GooglePlacesService.class).to(GooglePlacesServiceImpl.class);
      }

      @Provides @PlaceApiContext
      GeoApiContext placeApiContext(@Named("google.places.api.key") String apiKey) {
        return new GeoApiContext.Builder().apiKey(apiKey).build();
      }

      @Provides @GeocodingApiContext
      GeoApiContext geocodingApiContext(@Named("google.geocoding.api.key") String apiKey) {
        return new GeoApiContext.Builder().apiKey(apiKey).build();
      }

      private void bindProperties() {
        try {
          Properties properties = new Properties();
          properties.load(getClass().getClassLoader().getResourceAsStream(CONFIG_FILE));
          Names.bindProperties(binder(), properties);
        } catch (IOException e) {
          log.error("Could not load configuration values: ", e);
        }
      }
    });
  }
}
