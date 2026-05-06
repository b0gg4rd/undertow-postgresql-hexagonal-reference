@ignore
Feature: Helper para crear un payee-alias-directory-entry

  Background:
    * url ${functional-tests.server}
    * header Content-Type = 'application/json'

  Scenario: Crea un 'payee-alias-directory-entry' y extrae el identificador
    Given path '/payee-alias-directory-entries'
    And request
      """
      {
        "a1": "773899273774",
        "a2": "tanda"
      }
      """
    When method POST
    Then status 201
    * def payeeAliasDirectoryEntryReference = responseHeaders['a0'][0]