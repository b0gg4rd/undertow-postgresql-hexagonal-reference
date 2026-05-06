package com.gfb.reference.hexagonal.infrastructure.adapter.in.rest.undertow.util;

import com.gfb.reference.hexagonal.application.port.out.logging.LoggingPortOut;
import com.gfb.reference.hexagonal.infrastructure.bootstrap.ApplicationProperties;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import lombok.experimental.UtilityClass;
import org.xnio.Options;

@UtilityClass
public class UndertowAppUtils {

  private static final String HOST = "host";

  private static final String PORT = "port";

  private static final int BUFFER_SIZE = 1024 * 64;

  private static final int IO_THREADS = Runtime.getRuntime().availableProcessors() * 4;

  private static final int WORKER_THREADS = IO_THREADS * 2;

  private static final int BACKLOG_SIZE = 10000;

  private static final int KEEP_ALIVE_TIME = 200;

  public static void buildAndStart(final LoggingPortOut loggingPortOut,
                                   final Class<?>       clazz,
                                   final HttpHandler    routes) {

    loggingPortOut.info(
      clazz,
      "Starting {} ...",
      clazz.getSimpleName());

    UndertowAppUtils
      .buildUndertow(routes)
      .start();

    loggingPortOut.info(
      clazz,
      "{} started for interface {} and port {}",
      clazz.getSimpleName(),
      ApplicationProperties.APPLICATION_PROPERTIES.get(HOST),
      ApplicationProperties.APPLICATION_PROPERTIES.get(PORT));

  }

  private static Undertow buildUndertow(final HttpHandler routes) {

    return
      Undertow
        .builder()
        .addHttpListener(
          Integer.parseInt(ApplicationProperties.APPLICATION_PROPERTIES.get(PORT)),
          ApplicationProperties.APPLICATION_PROPERTIES.get(HOST))
        .setBufferSize(BUFFER_SIZE)
        .setIoThreads(IO_THREADS)
        .setWorkerThreads(WORKER_THREADS)
        .setSocketOption(Options.BACKLOG, BACKLOG_SIZE)
        .setServerOption(UndertowOptions.ALWAYS_SET_KEEP_ALIVE, false)
        .setServerOption(UndertowOptions.ALWAYS_SET_DATE, true)
        .setServerOption(UndertowOptions.RECORD_REQUEST_START_TIME, false)
        .setHandler(routes)
        .build();

  }

}
