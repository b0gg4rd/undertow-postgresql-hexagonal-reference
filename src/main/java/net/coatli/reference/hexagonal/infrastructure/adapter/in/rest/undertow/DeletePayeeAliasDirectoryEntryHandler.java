package net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow;

import net.coatli.reference.hexagonal.application.port.in.DeletePayeeAliasDirectoryEntryPortIn;
import net.coatli.reference.hexagonal.application.port.out.logging.LoggingPortOut;
import net.coatli.reference.hexagonal.application.port.out.transformation.JsonTransformationPortOut;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.model.mapper.DeletePayeeAliasDirectoryEntryHandlerMapper;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.util.UndertowQueryParamUtils;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.util.UndertowResponseBodyUtils;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DeletePayeeAliasDirectoryEntryHandler implements HttpHandler {

  private static final String PAYEE_ALIAS_DIRECTORY_ENTRY_REFERENCE = "a0";

  private final DeletePayeeAliasDirectoryEntryPortIn deletePayeeAliasDirectoryEntryPortIn;

  private final DeletePayeeAliasDirectoryEntryHandlerMapper deletePayeeAliasDirectoryEntryHandlerMapper;

  private final JsonTransformationPortOut jsonTransformationPortOut;

  private final LoggingPortOut loggingPortOut;

  @Inject
  public DeletePayeeAliasDirectoryEntryHandler(final DeletePayeeAliasDirectoryEntryPortIn        deletePayeeAliasDirectoryEntryPortIn,
                                               final DeletePayeeAliasDirectoryEntryHandlerMapper deletePayeeAliasDirectoryEntryHandlerMapper,
                                               final JsonTransformationPortOut                   jsonTransformationPortOut,
                                               final LoggingPortOut                              loggingPortOut) {

    this.deletePayeeAliasDirectoryEntryPortIn = deletePayeeAliasDirectoryEntryPortIn;

    this.deletePayeeAliasDirectoryEntryHandlerMapper = deletePayeeAliasDirectoryEntryHandlerMapper;

    this.jsonTransformationPortOut = jsonTransformationPortOut;

    this.loggingPortOut = loggingPortOut;

  }

  @Override
  public void handleRequest(final HttpServerExchange httpServerExchange) throws Exception {

    final var payeeAliasDirectoryEntryReference = UndertowQueryParamUtils.getQueryParam(httpServerExchange, PAYEE_ALIAS_DIRECTORY_ENTRY_REFERENCE);

    loggingPortOut.info(
      this.getClass(),
      "Infrastructure Adapter In - delete '{}'",
      payeeAliasDirectoryEntryReference);

    final var deletePayeeAliasDirectoryEntryOutput = deletePayeeAliasDirectoryEntryPortIn.execute(
      deletePayeeAliasDirectoryEntryHandlerMapper.mappingPayeeAliasDirectoryEntryReference2DeletePayeeAliasDirectoryEntryInput(payeeAliasDirectoryEntryReference));

    loggingPortOut.info(
      this.getClass(),
      "Infrastructure Adapter In - delete output '{}'",
      jsonTransformationPortOut.toJson(deletePayeeAliasDirectoryEntryOutput));

    UndertowResponseBodyUtils.createNoContentResponse(
      httpServerExchange,
      loggingPortOut,
      this.getClass());

  }

}
