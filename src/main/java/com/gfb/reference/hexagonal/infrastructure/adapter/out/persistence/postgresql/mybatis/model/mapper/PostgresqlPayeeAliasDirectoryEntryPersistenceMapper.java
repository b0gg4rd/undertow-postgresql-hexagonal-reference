package com.gfb.reference.hexagonal.infrastructure.adapter.out.persistence.postgresql.mybatis.model.mapper;

import com.gfb.reference.hexagonal.domain.model.PayeeAliasDirectoryEntry;
import com.gfb.reference.hexagonal.infrastructure.adapter.out.persistence.postgresql.mybatis.model.PayeeAliasDirectoryEntryRow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PostgresqlPayeeAliasDirectoryEntryPersistenceMapper {

  @Mapping(target = "id",         source = "payeeAliasDirectoryEntry.payeeAliasDirectoryEntryReference")
  @Mapping(target = "customerId", source = "payeeAliasDirectoryEntry.customerReference")
  @Mapping(target = "alias",      source = "payeeAliasDirectoryEntry.payeeAlias")
  @Mapping(target = "status",     source = "payeeAliasDirectoryEntry.status")
  @Mapping(target = "createdAt",  source = "payeeAliasDirectoryEntry.createdAt")
  PayeeAliasDirectoryEntryRow mappingPayeeAliasDirectoryEntry2PayeeAliasDirectoryEntryRow(PayeeAliasDirectoryEntry payeeAliasDirectoryEntry);

  @Mapping(target = "payeeAliasDirectoryEntryReference", source = "payeeAliasDirectoryEntryRow.id")
  @Mapping(target = "customerReference",                 source = "payeeAliasDirectoryEntryRow.customerId")
  @Mapping(target = "payeeAlias",                        source = "payeeAliasDirectoryEntryRow.alias")
  @Mapping(target = "status",                            source = "payeeAliasDirectoryEntryRow.status")
  @Mapping(target = "createdAt",                         source = "payeeAliasDirectoryEntryRow.createdAt")
  PayeeAliasDirectoryEntry mappingPayeeAliasDirectoryEntryRow2PayeeAliasDirectoryEntry(PayeeAliasDirectoryEntryRow payeeAliasDirectoryEntryRow);

}
