Feature: Inicio de sesión
  Como usuario registrado
  Quiero iniciar sesión con mis credenciales
  Para acceder a mi cuenta personalizada

  Scenario: Inicio de sesión exitoso
    Given el usuario tiene una cuenta registrada como "org@test.com"
    And está en la página de inicio de sesión
    When ingresa email "org@test.com" y contraseña correcta
    And hace clic en "Iniciar Sesión"
    Then accede a su dashboard exitosamente
    And ve su nombre de usuario en la barra superior

  Scenario: Intento de acceso con cuenta inactiva
    Given el usuario "test@test.com" tiene su cuenta desactivada
    When intenta iniciar sesión con sus credenciales
    Then ve el mensaje "Cuenta inactiva. Contacte al administrador"
    And no puede acceder al sistema






