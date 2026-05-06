package com.gfb.reference.hexagonal.infrastructure.adapter.out.persistence.postgresql.mybatis;

import com.gfb.reference.hexagonal.infrastructure.adapter.out.persistence.postgresql.mybatis.model.PayeeAliasDirectoryEntryRow;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MyBatisPayeeAliasDirectoryEntryMapper {

  @Insert("""
    INSERT INTO payee_alias_directory_entry
    (id, customer_id, alias, status, created_at)
    VALUES
    (#{id}::uuid, #{customerId}, #{alias}, #{status}, #{createdAt})
  """)
  int insert(PayeeAliasDirectoryEntryRow payeeAliasDirectoryEntryRow);

  @Select("""
    SELECT id, customer_id, alias, status, created_at
    FROM payee_alias_directory_entry
    WHERE id = #{id}::uuid
  """)
  PayeeAliasDirectoryEntryRow select(@Param("id") String id);

  @Delete("""
    DELETE FROM payee_alias_directory_entry
    WHERE id = #{id}::uuid
  """)
  int delete(@Param("id") String id);

}
