package net.coatli.reference.hexagonal.infrastructure.bootstrap;

import net.coatli.reference.hexagonal.application.port.out.logging.LoggingPortOut;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.config.ExceptionConfig;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.config.RoutesConfig;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.util.UndertowAppUtils;
import net.coatli.reference.hexagonal.infrastructure.bootstrap.di.ApplicationCoreModule;
import net.coatli.reference.hexagonal.infrastructure.bootstrap.di.InfrastructureAdapterInModule;
import net.coatli.reference.hexagonal.infrastructure.bootstrap.di.InfrastructureAdapterOutModule;
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
