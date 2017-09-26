package com.alex.places.server.config;

import com.alex.places.server.PlaceFinderAppServiceImpl;
import com.alex.places.server.service.PlaceService;
import com.alex.places.server.service.PlaceServiceImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public class PlaceFinderAppServletContextListener extends GuiceServletContextListener {
  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new ServletModule() {
      @Override
      protected void configureServlets() {
        serve("/placefinder/find").with(PlaceFinderAppServiceImpl.class);
        bind(PlaceService.class).to(PlaceServiceImpl.class);
      }
    });
  }
}
