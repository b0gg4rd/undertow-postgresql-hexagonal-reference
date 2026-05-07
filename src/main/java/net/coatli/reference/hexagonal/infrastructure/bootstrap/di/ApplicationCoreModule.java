package net.coatli.reference.hexagonal.infrastructure.bootstrap.di;

import net.coatli.reference.hexagonal.application.core.PayeeAliasDirectoryEntryCreator;
import net.coatli.reference.hexagonal.application.core.PayeeAliasDirectoryEntryDeleter;
import net.coatli.reference.hexagonal.application.core.PayeeAliasDirectoryEntryRetriever;
import net.coatli.reference.hexagonal.application.port.in.CreatePayeeAliasDirectoryEntryPortIn;
import net.coatli.reference.hexagonal.application.port.in.DeletePayeeAliasDirectoryEntryPortIn;
import net.coatli.reference.hexagonal.application.port.in.RetrievePayeeAliasDirectoryEntryPortIn;
import net.coatli.reference.hexagonal.application.port.in.model.mapper.CreatePayeeAliasDirectoryEntryPortInMapper;
import net.coatli.reference.hexagonal.application.port.in.model.mapper.DeletePayeeAliasDirectoryEntryPortInMapper;
import net.coatli.reference.hexagonal.application.port.in.model.mapper.RetrievePayeeAliasDirectoryEntryPortInMapper;
import net.coatli.reference.hexagonal.application.port.out.logging.LoggingPortOut;
import net.coatli.reference.hexagonal.application.port.out.persistence.PayeeAliasDirectoryEntryPersistencePortOut;
import net.coatli.reference.hexagonal.application.port.out.transformation.JsonTransformationPortOut;
import org.codejargon.feather.Provides;
import org.mapstruct.factory.Mappers;

import javax.inject.Singleton;

public class ApplicationCoreModule {

  @Provides
  @Singleton
  public CreatePayeeAliasDirectoryEntryPortInMapper createPayeeAliasDirectoryEntryMapper() {

    return Mappers.getMapper(CreatePayeeAliasDirectoryEntryPortInMapper.class);

  }

  @Provides
  @Singleton
  public RetrievePayeeAliasDirectoryEntryPortInMapper retrievePayeeAliasDirectoryEntryPortInMapper() {

    return Mappers.getMapper(RetrievePayeeAliasDirectoryEntryPortInMapper.class);

  }

  @Provides
  @Singleton
  public DeletePayeeAliasDirectoryEntryPortInMapper deletePayeeAliasDirectoryEntryPortInMapper() {

    return Mappers.getMapper(DeletePayeeAliasDirectoryEntryPortInMapper.class);

  }

  @Provides
  @Singleton
  public CreatePayeeAliasDirectoryEntryPortIn createPayeeAliasDirectoryEntryPortIn(
      LoggingPortOut                             loggingPortOut,
      JsonTransformationPortOut                  jsonTransformationPortOut,
      CreatePayeeAliasDirectoryEntryPortInMapper createPayeeAliasDirectoryEntryPortInMapper,
      PayeeAliasDirectoryEntryPersistencePortOut payeeAliasDirectoryEntryPersistencePortOut) {

    return new PayeeAliasDirectoryEntryCreator(
      loggingPortOut,
      jsonTransformationPortOut,
      createPayeeAliasDirectoryEntryPortInMapper,
      payeeAliasDirectoryEntryPersistencePortOut);

  }

  @Provides
  @Singleton
  public RetrievePayeeAliasDirectoryEntryPortIn retrievePayeeAliasDirectoryEntryPortIn(
      LoggingPortOut                               loggingPortOut,
      JsonTransformationPortOut                    jsonTransformationPortOut,
      RetrievePayeeAliasDirectoryEntryPortInMapper retrievePayeeAliasDirectoryEntryPortInMapper,
      PayeeAliasDirectoryEntryPersistencePortOut   payeeAliasDirectoryEntryPersistencePortOut) {

    return new PayeeAliasDirectoryEntryRetriever(
      loggingPortOut,
      jsonTransformationPortOut,
      retrievePayeeAliasDirectoryEntryPortInMapper,
      payeeAliasDirectoryEntryPersistencePortOut);

  }

  @Provides
  @Singleton
  public DeletePayeeAliasDirectoryEntryPortIn deletePayeeAliasDirectoryEntryPortIn(
      LoggingPortOut                             loggingPortOut,
      JsonTransformationPortOut                  jsonTransformationPortOut,
      DeletePayeeAliasDirectoryEntryPortInMapper deletePayeeAliasDirectoryEntryPortInMapper,
      PayeeAliasDirectoryEntryPersistencePortOut payeeAliasDirectoryEntryPersistencePortOut) {

    return new PayeeAliasDirectoryEntryDeleter(
      loggingPortOut,
      jsonTransformationPortOut,
      deletePayeeAliasDirectoryEntryPortInMapper,
      payeeAliasDirectoryEntryPersistencePortOut);
  }

}
