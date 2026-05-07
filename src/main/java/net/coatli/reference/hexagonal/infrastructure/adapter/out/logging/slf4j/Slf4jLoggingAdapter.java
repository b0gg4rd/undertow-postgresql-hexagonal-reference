package net.coatli.reference.hexagonal.infrastructure.adapter.out.logging.slf4j;

import net.coatli.reference.hexagonal.application.port.out.logging.LoggingPortOut;
import org.slf4j.LoggerFactory;

public class Slf4jLoggingAdapter implements LoggingPortOut {

  @Override
  public void info(Class<?> caller, String template, Object... values) {

    LoggerFactory.getLogger(caller).info(template, values);

  }

  @Override
  public void debug(Class<?> caller, String template, Object... values) {

    LoggerFactory.getLogger(caller).debug(template, values);

  }

  @Override
  public void error(Class<?> caller, String message, Object... values) {

    LoggerFactory.getLogger(caller).error(message, values);

  }

}