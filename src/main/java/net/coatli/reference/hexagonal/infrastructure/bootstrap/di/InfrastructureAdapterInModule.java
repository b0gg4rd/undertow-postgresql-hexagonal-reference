package net.coatli.reference.hexagonal.infrastructure.bootstrap.di;

import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.model.mapper.CreatePayeeAliasDirectoryEntryHandlerMapper;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.model.mapper.DeletePayeeAliasDirectoryEntryHandlerMapper;
import net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.model.mapper.RetrievePayeeAliasDirectoryEntryHandlerMapper;
import org.codejargon.feather.Provides;
import org.mapstruct.factory.Mappers;

import javax.inject.Singleton;

public class InfrastructureAdapterInModule {

  @Provides
  @Singleton
  public CreatePayeeAliasDirectoryEntryHandlerMapper createPayeeAliasDirectoryEntryHandlerMapper() {

    return Mappers.getMapper(CreatePayeeAliasDirectoryEntryHandlerMapper.class);

  }

  @Provides
  @Singleton
  public RetrievePayeeAliasDirectoryEntryHandlerMapper retrievePayeeAliasDirectoryEntryHandlerMapper() {

    return Mappers.getMapper(RetrievePayeeAliasDirectoryEntryHandlerMapper.class);

  }

  @Provides
  @Singleton
  public DeletePayeeAliasDirectoryEntryHandlerMapper deletePayeeAliasDirectoryEntryHandlerMapper() {

    return Mappers.getMapper(DeletePayeeAliasDirectoryEntryHandlerMapper.class);

  }

}
