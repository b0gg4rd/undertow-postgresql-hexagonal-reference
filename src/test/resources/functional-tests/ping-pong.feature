Feature: ping-pong

  Background:
    * url ${functional-tests.server}
    * header Content-Type = '*/*'

  Scenario: Caso 01: Éxito - Debe retornar 200 el "ping"
    Given path '/ping'
    When method GET
    Then status 200
