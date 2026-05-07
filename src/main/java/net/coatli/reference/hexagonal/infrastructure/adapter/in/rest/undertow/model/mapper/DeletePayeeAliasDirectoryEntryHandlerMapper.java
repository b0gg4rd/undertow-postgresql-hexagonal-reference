package net.coatli.reference.hexagonal.infrastructure.adapter.in.rest.undertow.model.mapper;

import net.coatli.reference.hexagonal.application.port.in.model.DeletePayeeAliasDirectoryEntryInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DeletePayeeAliasDirectoryEntryHandlerMapper {

  @Mapping(target = "payeeAliasDirectoryEntryReference", source = "payeeAliasDirectoryEntryReference")
  DeletePayeeAliasDirectoryEntryInput mappingPayeeAliasDirectoryEntryReference2DeletePayeeAliasDirectoryEntryInput(String payeeAliasDirectoryEntryReference);

}
