package net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.config;

import net.coatli.reference.hexagonal.application.port.in.exception.PayeeAliasDirectoryEntryInputException;
import net.coatli.reference.hexagonal.application.port.in.exception.PayeeAliasDirectoryEntryNotFoundException;
import net.coatli.reference.hexagonal.application.port.out.logging.LoggingPortOut;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.util.UndertowResponseBodyUtils;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.ExceptionHandler;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionConfig {

  public static HttpHandler setup(final HttpHandler    routes,
                                  final LoggingPortOut loggingPortOut,
                                  final Class<?>       caller) {

    return Handlers
      .exceptionHandler(routes)
      .addExceptionHandler(
        PayeeAliasDirectoryEntryInputException.class,
        httpServerExchange -> handlePayeeAliasDirectoryEntryInputException(httpServerExchange, loggingPortOut, caller))
      .addExceptionHandler(
        PayeeAliasDirectoryEntryNotFoundException.class,
        httpServerExchange -> handlePayeeAliasDirectoryNotFoundException(httpServerExchange, loggingPortOut, caller))
      .addExceptionHandler(
        IllegalArgumentException.class,
        httpServerExchange -> handleIllegalArgumentException(httpServerExchange, loggingPortOut, caller))
      .addExceptionHandler(
        Exception.class,
        httpServerExchange -> handleGeneralException(httpServerExchange, loggingPortOut, caller));

  }

  private static void handlePayeeAliasDirectoryEntryInputException(final HttpServerExchange httpServerExchange,
                                                                   final LoggingPortOut     loggingPortOut,
                                                                   final Class<?>           caller) {

    UndertowResponseBodyUtils.createBadRequestResponse(
      httpServerExchange,
      loggingPortOut,
      caller,
      (Throwable) httpServerExchange.getAttachment(ExceptionHandler.THROWABLE));

  }

  private static void handlePayeeAliasDirectoryNotFoundException(final HttpServerExchange httpServerExchange,
                                                                 final LoggingPortOut     loggingPortOut,
                                                                 final Class<?>           caller) {

    UndertowResponseBodyUtils.createNotFoundResponse(
      httpServerExchange,
      loggingPortOut,
      caller,
      (Throwable) httpServerExchange.getAttachment(ExceptionHandler.THROWABLE));
  }

  private static void handleIllegalArgumentException(final HttpServerExchange httpServerExchange,
                                                     final LoggingPortOut     loggingPortOut,
                                                     final Class<?>           caller) {

    UndertowResponseBodyUtils.createBadRequestResponse(
      httpServerExchange,
      loggingPortOut,
      caller,
      (Throwable) httpServerExchange.getAttachment(ExceptionHandler.THROWABLE));

  }

  private static void handleGeneralException(final HttpServerExchange httpServerExchange,
                                             final LoggingPortOut     loggingPortOut,
                                             final Class<?>           caller) {

    UndertowResponseBodyUtils.createInternalServerErrorResponse(
      httpServerExchange,
      loggingPortOut,
      caller,
      (Throwable) httpServerExchange.getAttachment(ExceptionHandler.THROWABLE));

  }

}
