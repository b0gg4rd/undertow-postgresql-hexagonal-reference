package net.coatli.reference.hexagonal.infrastructure.bootstrap.di;

import net.coatli.reference.hexagonal.application.port.out.logging.LoggingPortOut;
import net.coatli.reference.hexagonal.application.port.out.persistence.PayeeAliasDirectoryEntryPersistencePortOut;
import net.coatli.reference.hexagonal.application.port.out.transformation.JsonTransformationPortOut;
import net.coatli.reference.hexagonal.infrastructure.adapter.out.logging.slf4j.Slf4jLoggingAdapter;
import net.coatli.reference.hexagonal.infrastructure.adapter.out.persistence.postgresql.PostgresqlPayeeAliasDirectoryEntryPersistenceAdapter;
import net.coatli.reference.hexagonal.infrastructure.adapter.out.persistence.postgresql.mybatis.MyBatisPayeeAliasDirectoryEntryMapper;
import net.coatli.reference.hexagonal.infrastructure.adapter.out.persistence.postgresql.mybatis.model.PayeeAliasDirectoryEntryRow;
import net.coatli.reference.hexagonal.infrastructure.adapter.out.persistence.postgresql.mybatis.model.mapper.PostgresqlPayeeAliasDirectoryEntryPersistenceMapper;
import net.coatli.reference.hexagonal.infrastructure.adapter.out.transformation.jsoniter.JsoniterJsonTransformationAdapter;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.codejargon.feather.Provides;
import org.mapstruct.factory.Mappers;

import javax.inject.Singleton;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class InfrastructureAdapterOutModule {

  @Provides
  @Singleton
  public JsonTransformationPortOut jsonTransformationPortOut() {

    return new JsoniterJsonTransformationAdapter();

  }

  @Provides
  @Singleton
  public LoggingPortOut loggingPortOut() {

    return new Slf4jLoggingAdapter();

  }

  @Provides
  @Singleton
  public DataSource dataSource() {

    try (final var inputStream = InfrastructureAdapterOutModule.class.getResourceAsStream("/conf/hikari.properties")) {

      final var hikariConfig = new Properties();

      hikariConfig.load(inputStream);

      return new HikariDataSource(new HikariConfig(hikariConfig));

    } catch (final IOException ioException) {

      throw new RuntimeException("Error creating the data source", ioException);

    }

  }

  @Provides
  @Singleton
  public Configuration configuration(final DataSource dataSource) {

    final var configuration = new Configuration(
      new Environment(
        "production",
        new JdbcTransactionFactory(),
        dataSource));

    configuration
      .getTypeAliasRegistry()
      .registerAlias(
        PayeeAliasDirectoryEntryRow.class.getSimpleName(),
        PayeeAliasDirectoryEntryRow.class);

    configuration.addMapper(MyBatisPayeeAliasDirectoryEntryMapper.class);

    return configuration;

  }

  @Provides
  @Singleton
  public SqlSessionFactory sqlSessionFactory(final Configuration configuration) {

    return new SqlSessionFactoryBuilder().build(configuration);

  }

  @Provides
  @Singleton
  public PostgresqlPayeeAliasDirectoryEntryPersistenceMapper postgresqlPayeeAliasDirectoryEntryPersistenceMapper() {

    return Mappers.getMapper(PostgresqlPayeeAliasDirectoryEntryPersistenceMapper.class);

  }

  @Provides
  @Singleton
  public PayeeAliasDirectoryEntryPersistencePortOut PayeeAliasDirectoryEntryPersistencePortOut(
      SqlSessionFactory                                   sqlSessionFactory,
      PostgresqlPayeeAliasDirectoryEntryPersistenceMapper postgresqlPayeeAliasDirectoryEntryPersistenceMapper,
      JsonTransformationPortOut                           jsonTransformationPortOut,
      LoggingPortOut                                      loggingPortOut) {

    return new PostgresqlPayeeAliasDirectoryEntryPersistenceAdapter(
      sqlSessionFactory,
      postgresqlPayeeAliasDirectoryEntryPersistenceMapper,
      jsonTransformationPortOut,
      loggingPortOut);

  }

}
