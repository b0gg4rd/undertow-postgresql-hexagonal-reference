package com.gfb.reference.hexagonal.infrastructure.adapter.out.persistence.postgresql;

import com.gfb.reference.hexagonal.application.port.out.logging.LoggingPortOut;
import com.gfb.reference.hexagonal.application.port.out.persistence.PayeeAliasDirectoryEntryPersistencePortOut;
import com.gfb.reference.hexagonal.application.port.out.persistence.exception.PayeeAliasDirectoryEntryPersistenceException;
import com.gfb.reference.hexagonal.application.port.out.transformation.JsonTransformationPortOut;
import com.gfb.reference.hexagonal.domain.model.PayeeAliasDirectoryEntry;
import com.gfb.reference.hexagonal.infrastructure.adapter.out.persistence.postgresql.mybatis.MyBatisPayeeAliasDirectoryEntryMapper;
import com.gfb.reference.hexagonal.infrastructure.adapter.out.persistence.postgresql.mybatis.model.mapper.PostgresqlPayeeAliasDirectoryEntryPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Optional;

@RequiredArgsConstructor
public class PostgresqlPayeeAliasDirectoryEntryPersistenceAdapter implements PayeeAliasDirectoryEntryPersistencePortOut {

  private static final int SUCCESS_INSERT = 1;

  private static final int SUCCESS_DELETE = 1;

  private static final int NOT_FOUND_DELETE = 0;

  private final SqlSessionFactory sqlSessionFactory;

  private final PostgresqlPayeeAliasDirectoryEntryPersistenceMapper postgresqlPayeeAliasDirectoryEntryPersistenceMapper;

  private final JsonTransformationPortOut jsonTransformationPortOut;

  private final LoggingPortOut loggingPortOut;

  @Override
  public PayeeAliasDirectoryEntry create(PayeeAliasDirectoryEntry payeeAliasDirectoryEntry) {

    loggingPortOut.info(
      this.getClass(),
      "Infrastructure Adapter Out - persistence create '{}'",
      jsonTransformationPortOut.toJson(payeeAliasDirectoryEntry));

    try (final var sqlSession = sqlSessionFactory.openSession()) {

      final var rows = sqlSession
        .getMapper(MyBatisPayeeAliasDirectoryEntryMapper.class)
        .insert(postgresqlPayeeAliasDirectoryEntryPersistenceMapper.mappingPayeeAliasDirectoryEntry2PayeeAliasDirectoryEntryRow(payeeAliasDirectoryEntry));

      if (SUCCESS_INSERT == rows) {

        sqlSession.commit();

        loggingPortOut.info(
          this.getClass(),
          "Infrastructure Adapter Out - persistence create commited");

      } else {

        sqlSession.rollback();

        loggingPortOut.error(
          this.getClass(),
          "Infrastructure Adapter Out - persistence create rollback by '" + rows + "'");

        throw new PayeeAliasDirectoryEntryPersistenceException(String.format("Error creating payment directory entry, insert result '%s'", rows));

      }

    }

    return payeeAliasDirectoryEntry;

  }

  @Override
  public Optional<PayeeAliasDirectoryEntry> retrieve(String payeeAliasDirectoryEntryReference) {

    loggingPortOut.info(
      this.getClass(),
      "Infrastructure Adapter Out - persistence retrieve '{}'",
      payeeAliasDirectoryEntryReference);

    try (final var sqlSession = sqlSessionFactory.openSession(true)) {

      return Optional
        .ofNullable(sqlSession
          .getMapper(MyBatisPayeeAliasDirectoryEntryMapper.class)
          .select(payeeAliasDirectoryEntryReference))
        .map(postgresqlPayeeAliasDirectoryEntryPersistenceMapper::mappingPayeeAliasDirectoryEntryRow2PayeeAliasDirectoryEntry);

    }

  }

  @Override
  public Optional<PayeeAliasDirectoryEntry> delete(String payeeAliasDirectoryEntryReference) {

    loggingPortOut.info(
      this.getClass(),
      "Infrastructure Adapter Out - persistence delete '{}'",
      payeeAliasDirectoryEntryReference);

    try (final var sqlSession = sqlSessionFactory.openSession(true)) {

      final var rows = sqlSession
        .getMapper(MyBatisPayeeAliasDirectoryEntryMapper.class)
        .delete(payeeAliasDirectoryEntryReference);

      if (NOT_FOUND_DELETE == rows) {

        loggingPortOut.info(
          this.getClass(),
          "Infrastructure Adapter Out - persistence delete not found");

        return Optional.empty();

      } else if (SUCCESS_DELETE == rows) {

        sqlSession.commit();

        loggingPortOut.info(
          this.getClass(),
          "Infrastructure Adapter Out - persistence delete commited");

        return Optional.of(new PayeeAliasDirectoryEntry()
          .setPayeeAliasDirectoryEntryReference(payeeAliasDirectoryEntryReference));

      } else {

        sqlSession.rollback();

        loggingPortOut.error(
          this.getClass(),
          "Infrastructure Adapter Out - persistence delete rollback by '" + rows + "'");

        throw new PayeeAliasDirectoryEntryPersistenceException(String.format("Error deleting payment directory entry, delete result '%s'", rows));

      }

    }

  }

}
