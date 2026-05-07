Feature: operative create-payee-alias-directory-entry

  Background:
    * url ${functional-tests.server}
    * header Content-Type = 'application/json'

  Scenario: Caso 01: Éxito - Debe retornar 201 al crear un "payee-alias-directory-entry"
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
    And match responseHeaders['a0'][0] == '#uuid'

  Scenario: Caso 02: Falla - Debe retornar 400 cuando el request no tiene el atributo a1
    Given path '/payee-alias-directory-entries'
    And request
      """
      {
        "a2": "tanda"
      }
      """
    When method POST
    Then status 400

  Scenario: Caso 03: Falla - Debe retornar 400 cuando el request no tiene el atributo a2
    Given path '/payee-alias-directory-entries'
    And request
      """
      {
        "a1": "773899273774"
      }
      """
    When method POST
    Then status 400

  Scenario: Caso 04: Falla - Debe retornar 400 cuando el request tiene el campo a1 vacio
    Given path '/payee-alias-directory-entries'
    And request
      """
      {
        "a1": "",
        "a2": "tanda"
      }
      """
    When method POST
    Then status 400

  Scenario: Caso 05: Falla - Debe retornar 400 cuando el request tiene el campo a2 vacio
    Given path '/payee-alias-directory-entries'
    And request
      """
      {
        "a1": "773899273774",
        "a2": ""
      }
      """
    When method POST
    Then status 400

  Scenario: Caso 06: Falla - Debe retornar 400 cuando el request tiene el campo a1 con espacios
    Given path '/payee-alias-directory-entries'
    And request
      """
      {
        "a1": "   ",
        "a2": "tanda"
      }
      """
    When method POST
    Then status 400

  Scenario: Caso 07: Falla - Debe retornar 400 cuando el request tiene el campo a2 con espacios
    Given path '/payee-alias-directory-entries'
    And request
      """
      {
        "a1": "773899273774",
        "a2": "   "
      }
      """
    When method POST
    Then status 400
