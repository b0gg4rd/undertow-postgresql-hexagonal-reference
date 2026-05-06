package com.gfb.reference.hexagonal.infrastructure.adapter.in.rest.undertow;

import com.gfb.reference.hexagonal.application.port.in.RetrievePayeeAliasDirectoryEntryPortIn;
import com.gfb.reference.hexagonal.application.port.out.logging.LoggingPortOut;
import com.gfb.reference.hexagonal.application.port.out.transformation.JsonTransformationPortOut;
import com.gfb.reference.hexagonal.infrastructure.adapter.in.rest.undertow.model.mapper.RetrievePayeeAliasDirectoryEntryHandlerMapper;
import com.gfb.reference.hexagonal.infrastructure.adapter.in.rest.undertow.util.UndertowQueryParamUtils;
import com.gfb.reference.hexagonal.infrastructure.adapter.in.rest.undertow.util.UndertowResponseBodyUtils;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RetrievePayeeAliasDirectoryEntryHandler implements HttpHandler {

  private static final String PAYEE_ALIAS_DIRECTORY_ENTRY_REFERENCE = "a0";

  private final RetrievePayeeAliasDirectoryEntryPortIn retrievePayeeAliasDirectoryEntryPortIn;

  private final RetrievePayeeAliasDirectoryEntryHandlerMapper retrievePayeeAliasDirectoryEntryHandlerMapper;

  private final JsonTransformationPortOut jsonTransformationPortOut;

  private final LoggingPortOut loggingPortOut;

  @Inject
  public RetrievePayeeAliasDirectoryEntryHandler(final RetrievePayeeAliasDirectoryEntryPortIn        retrievePayeeAliasDirectoryEntryPortIn,
                                                 final RetrievePayeeAliasDirectoryEntryHandlerMapper retrievePayeeAliasDirectoryEntryHandlerMapper,
                                                 final JsonTransformationPortOut                     jsonTransformationPortOut,
                                                 final LoggingPortOut                                loggingPortOut) {

    this.retrievePayeeAliasDirectoryEntryPortIn = retrievePayeeAliasDirectoryEntryPortIn;

    this.retrievePayeeAliasDirectoryEntryHandlerMapper = retrievePayeeAliasDirectoryEntryHandlerMapper;

    this.jsonTransformationPortOut = jsonTransformationPortOut;

    this.loggingPortOut = loggingPortOut;

  }

  @Override
  public void handleRequest(final HttpServerExchange httpServerExchange) throws Exception {

    final var payeeAliasDirectoryEntryReference = UndertowQueryParamUtils.getQueryParam(
      httpServerExchange,
      PAYEE_ALIAS_DIRECTORY_ENTRY_REFERENCE);

    loggingPortOut.info(
      this.getClass(),
      "Infrastructure Adapter In - retrieve '{}'",
      payeeAliasDirectoryEntryReference);

    final var retrievePayeeAliasDirectoryEntryOutput = retrievePayeeAliasDirectoryEntryPortIn.execute(
        retrievePayeeAliasDirectoryEntryHandlerMapper.mappingPayeeAliasDirectoryEntryReference2RetrievePayeeAliasDirectoryEntryInput(payeeAliasDirectoryEntryReference));

    loggingPortOut.info(
      this.getClass(),
      "Infrastructure Adapter In - retrieve output '{}'",
      jsonTransformationPortOut.toJson(retrievePayeeAliasDirectoryEntryOutput));

    final var response = retrievePayeeAliasDirectoryEntryHandlerMapper.mappingRetrievePayeeAliasDirectoryEntryOutput2RetrievePayeeAliasDirectoryEntryResponse(retrievePayeeAliasDirectoryEntryOutput);

    UndertowResponseBodyUtils.createOkResponse(
      httpServerExchange,
      loggingPortOut,
      this.getClass(),
      jsonTransformationPortOut.toJson(response));

  }

}
