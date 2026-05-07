--liquibase formatted sql

--changeset fcruz@bancobase.com:create-table-payee-alias-directory-entry context:ddl
CREATE TABLE payee_alias_directory_entry (
  id UUID NOT NULL,
  customer_id VARCHAR(200) NOT NULL,
  alias VARCHAR(150),
  status VARCHAR(80),
  created_at TIMESTAMP NOT NULL
)