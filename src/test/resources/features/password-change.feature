Feature: Cambio de contraseña
  Como usuario autenticado
  Quiero cambiar mi contraseña
  Para mantener mi cuenta segura

  Scenario: Cambio exitoso de contraseña
    Given el usuario está en su perfil
    And hace clic en "Cambiar contraseña"
    When ingresa su contraseña actual correctamente
    And ingresa una nueva contraseña válida
    And confirma la nueva contraseña
    Then ve el mensaje "Contraseña actualizada exitosamente"
    And su sesión se cierra automáticamente

  Scenario: Error al ingresar contraseña actual incorrecta
    Given el usuario está cambiando su contraseña
    When ingresa una contraseña actual incorrecta
    Then ve el mensaje "Contraseña actual incorrecta"
    And la contraseña no se actualiza






