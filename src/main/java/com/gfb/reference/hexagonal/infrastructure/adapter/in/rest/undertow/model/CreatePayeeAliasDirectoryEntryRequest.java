package com.gfb.reference.hexagonal.infrastructure.adapter.in.rest.undertow.model;

import com.jsoniter.annotation.JsonCreator;

public record CreatePayeeAliasDirectoryEntryRequest(

  String a1,

  String a2) {

  /**
   * Required for Jsoniter compatibility.
   */
  @JsonCreator
  public CreatePayeeAliasDirectoryEntryRequest {
  }

}
