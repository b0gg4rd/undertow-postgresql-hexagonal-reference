package com.gfb.reference.hexagonal.infrastructure.adapter.in.rest.undertow.model.mapper;

import com.gfb.reference.hexagonal.application.port.in.model.RetrievePayeeAliasDirectoryEntryInput;
import com.gfb.reference.hexagonal.application.port.in.model.RetrievePayeeAliasDirectoryEntryOutput;
import com.gfb.reference.hexagonal.infrastructure.adapter.in.rest.undertow.model.RetrievePayeeAliasDirectoryEntryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RetrievePayeeAliasDirectoryEntryHandlerMapper {

  @Mapping(target = "payeeAliasDirectoryEntryReference", source = "payeeAliasDirectoryEntryReference")
  RetrievePayeeAliasDirectoryEntryInput mappingPayeeAliasDirectoryEntryReference2RetrievePayeeAliasDirectoryEntryInput(String payeeAliasDirectoryEntryReference);

  @Mapping(target = "a0", source = "payeeAliasDirectoryEntryReference")
  @Mapping(target = "a1", source = "customerReference")
  @Mapping(target = "a2", source = "payeeAlias")
  @Mapping(target = "a3", source = "status")
  @Mapping(target = "a4", source = "createdAt")
  RetrievePayeeAliasDirectoryEntryResponse mappingRetrievePayeeAliasDirectoryEntryOutput2RetrievePayeeAliasDirectoryEntryResponse(RetrievePayeeAliasDirectoryEntryOutput retrievePayeeAliasDirectoryEntryOutput);

}
