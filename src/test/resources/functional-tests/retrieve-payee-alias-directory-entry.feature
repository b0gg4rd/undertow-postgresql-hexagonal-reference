Feature: operative retrieve-payee-alias-directory-entry

  Background:
    * url ${functional-tests.server}
    * header Content-Type = 'application/json'
    * def setup = callonce read('helpers/create-payee-alias-directory-entry.feature')
    * def payeeAliasDirectoryEntryReference = setup.payeeAliasDirectoryEntryReference

  Scenario: Caso 01: Éxito - Debe retornar 200 al obtener un "payee-alias-directory-entry"
    Given path '/payee-alias-directory-entries', payeeAliasDirectoryEntryReference
    When method GET
    Then status 200

  Scenario: Caso 02: Fallo - Debe retornar 404 si el "payee-alias-directory-entry" no existe
    Given path '/payee-alias-directory-entries/17a5d895-2f44-4870-9478-14900cfe75f9'
    When method GET
    Then status 404

  Scenario: Caso 03: Fallo - Debe retornar 400 si el "payee-alias-directory-entry reference" no es un UUID
    Given path '/payee-alias-directory-entries/dummy'
    When method GET
    Then status 400
