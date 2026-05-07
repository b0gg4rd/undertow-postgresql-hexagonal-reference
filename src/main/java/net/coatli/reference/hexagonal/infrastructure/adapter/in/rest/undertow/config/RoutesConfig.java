package net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.config;

import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.CreatePayeeAliasDirectoryEntryHandler;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.DeletePayeeAliasDirectoryEntryHandler;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.RetrievePayeeAliasDirectoryEntryHandler;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.ResponseCodeHandler;
import io.undertow.util.Methods;
import lombok.experimental.UtilityClass;
import org.codejargon.feather.Feather;

@UtilityClass
public class RoutesConfig {

  public static HttpHandler routes(final Feather feather) {

    return
      Handlers
        .routing()
        .add(
          Methods.GET,
          "/api/v1/ping",
          ResponseCodeHandler.HANDLE_200)
        .add(
          Methods.POST,
          "/api/v1/payee-alias-directory-entries",
          feather.instance(CreatePayeeAliasDirectoryEntryHandler.class))
        .add(
          Methods.GET,
          "/api/v1/payee-alias-directory-entries/{a0}",
          feather.instance(RetrievePayeeAliasDirectoryEntryHandler.class))
        .add(
          Methods.DELETE,
          "/api/v1/payee-alias-directory-entries/{a0}",
          feather.instance(DeletePayeeAliasDirectoryEntryHandler.class));

  }

}
