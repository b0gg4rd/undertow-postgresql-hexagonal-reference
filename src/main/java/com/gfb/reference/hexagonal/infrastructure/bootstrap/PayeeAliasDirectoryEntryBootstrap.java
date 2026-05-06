package com.gfb.reference.hexagonal.infrastructure.bootstrap;

import com.gfb.reference.hexagonal.application.port.out.logging.LoggingPortOut;
import com.gfb.reference.hexagonal.infrastructure.adapter.in.rest.undertow.config.ExceptionConfig;
import com.gfb.reference.hexagonal.infrastructure.adapter.in.rest.undertow.config.RoutesConfig;
import com.gfb.reference.hexagonal.infrastructure.adapter.in.rest.undertow.util.UndertowAppUtils;
import com.gfb.reference.hexagonal.infrastructure.bootstrap.di.ApplicationCoreModule;
import com.gfb.reference.hexagonal.infrastructure.bootstrap.di.InfrastructureAdapterInModule;
import com.gfb.reference.hexagonal.infrastructure.bootstrap.di.InfrastructureAdapterOutModule;
import io.undertow.server.handlers.BlockingHandler;
import lombok.experimental.UtilityClass;
import org.codejargon.feather.Feather;

@UtilityClass
public class PayeeAliasDirectoryEntryBootstrap {

  private static final Feather FEATHER = Feather.with(
    new InfrastructureAdapterInModule(),
    new InfrastructureAdapterOutModule(),
    new ApplicationCoreModule());

  static void main(final String[] args) throws Exception {

    final var loggingPortOut = FEATHER.instance(LoggingPortOut.class);

    UndertowAppUtils.buildAndStart(
      loggingPortOut,
      PayeeAliasDirectoryEntryBootstrap.class,
      new BlockingHandler(
        ExceptionConfig.setup(
          RoutesConfig.routes(FEATHER),
          loggingPortOut,
          PayeeAliasDirectoryEntryBootstrap.class
          )));

  }

}
