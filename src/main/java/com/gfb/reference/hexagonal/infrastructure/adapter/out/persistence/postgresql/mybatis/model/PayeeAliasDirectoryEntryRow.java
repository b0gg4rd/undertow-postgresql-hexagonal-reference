package com.gfb.reference.hexagonal.infrastructure.adapter.out.persistence.postgresql.mybatis.model;

import java.time.LocalDateTime;

public record PayeeAliasDirectoryEntryRow(

  String id,

  String customerId,

  String alias,

  String status,

  LocalDateTime createdAt) {

}
