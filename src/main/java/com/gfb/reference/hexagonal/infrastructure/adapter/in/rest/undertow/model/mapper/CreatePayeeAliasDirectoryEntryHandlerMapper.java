package com.gfb.reference.hexagonal.infrastructure.adapter.in.rest.undertow.model.mapper;

import com.gfb.reference.hexagonal.application.port.in.model.CreatePayeeAliasDirectoryEntryInput;
import com.gfb.reference.hexagonal.infrastructure.adapter.in.rest.undertow.model.CreatePayeeAliasDirectoryEntryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CreatePayeeAliasDirectoryEntryHandlerMapper {

  @Mapping(target = "customerReference", source = "a1")
  @Mapping(target = "alias",             source = "a2")
  CreatePayeeAliasDirectoryEntryInput mappingCreatePayeeAliasDirectoryEntryRequest2CreatePayeeAliasDirectoryEntryInput(CreatePayeeAliasDirectoryEntryRequest createPayeeAliasDirectoryEntryRequest);

}
