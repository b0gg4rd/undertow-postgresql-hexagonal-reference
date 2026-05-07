package net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow;

import net.coatli.reference.hexagonal.application.port.in.CreatePayeeAliasDirectoryEntryPortIn;
import net.coatli.reference.hexagonal.application.port.out.logging.LoggingPortOut;
import net.coatli.reference.hexagonal.application.port.out.transformation.JsonTransformationPortOut;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.model.mapper.CreatePayeeAliasDirectoryEntryHandlerMapper;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.model.CreatePayeeAliasDirectoryEntryRequest;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.util.UndertowRequestBodyUtils;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.util.UndertowResponseBodyUtils;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CreatePayeeAliasDirectoryEntryHandler implements HttpHandler {

  private static final String PAYEE_ALIAS_DIRECTORY_ENTRY_REFERENCE_HEADER = "a0";

  private final CreatePayeeAliasDirectoryEntryPortIn createPayeeAliasDirectoryEntryPortIn;

  private final CreatePayeeAliasDirectoryEntryHandlerMapper createPayeeAliasDirectoryEntryHandlerMapper;

  private final JsonTransformationPortOut jsonTransformationPortOut;

  private final LoggingPortOut loggingPortOut;

  @Inject
  public CreatePayeeAliasDirectoryEntryHandler(final CreatePayeeAliasDirectoryEntryPortIn        createPayeeAliasDirectoryEntryPortIn,
                                               final CreatePayeeAliasDirectoryEntryHandlerMapper createPayeeAliasDirectoryEntryHandlerMapper,
                                               final JsonTransformationPortOut                   jsonTransformationPortOut,
                                               final LoggingPortOut                              loggingPortOut) {

    this.createPayeeAliasDirectoryEntryPortIn = createPayeeAliasDirectoryEntryPortIn;

    this.createPayeeAliasDirectoryEntryHandlerMapper = createPayeeAliasDirectoryEntryHandlerMapper;

    this.jsonTransformationPortOut = jsonTransformationPortOut;

    this.loggingPortOut = loggingPortOut;

  }

  @Override
  public void handleRequest(final HttpServerExchange httpServerExchange) throws Exception {

    loggingPortOut.info(
      this.getClass(),
      "Infrastructure Adapter In - create");

    final var createPayeeAliasDirectoryEntryRequest = UndertowRequestBodyUtils
      .deserializeBody(
        httpServerExchange,
        jsonTransformationPortOut,
        CreatePayeeAliasDirectoryEntryRequest.class);

    loggingPortOut.info(
      this.getClass(),
      "Infrastructure Adapter In - request '{}'",
      jsonTransformationPortOut.toJson(createPayeeAliasDirectoryEntryRequest));

    final var createPayeeAliasDirectoryEntryOutput = createPayeeAliasDirectoryEntryPortIn.execute(
      createPayeeAliasDirectoryEntryHandlerMapper.mappingCreatePayeeAliasDirectoryEntryRequest2CreatePayeeAliasDirectoryEntryInput(createPayeeAliasDirectoryEntryRequest));

    loggingPortOut.info(
      this.getClass(),
      "Infrastructure Adapter In - create output '{}'",
      jsonTransformationPortOut.toJson(createPayeeAliasDirectoryEntryOutput));

    UndertowResponseBodyUtils.createCreatedResponse(
      httpServerExchange,
      loggingPortOut,
      this.getClass(),
      PAYEE_ALIAS_DIRECTORY_ENTRY_REFERENCE_HEADER,
      createPayeeAliasDirectoryEntryOutput.payeeAliasDirectoryEntryReference());

  }

}
