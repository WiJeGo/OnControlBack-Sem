Feature: Registro de cuenta de usuario
  Como usuario general
  Quiero registrarme en la plataforma OnControl
  Para acceder a las funcionalidades del sistema

  Scenario: Registro exitoso de nueva cuenta
    Given el usuario está en la página de registro
    When ingresa sus datos personales
      | Email | Contraseña | Organización | País | Ciudad |
      | test@test.com | password | Test Org | PE | Lima |
    And hace clic en "Registrarse"
    Then ve el mensaje "Cuenta creada exitosamente"
    And es redirigido al dashboard
    And su cuenta está activa

  Scenario: Intento de registro con email existente
    Given existe una cuenta con email "test@test.com"
    When el usuario intenta registrarse con ese mismo email
    Then ve el mensaje "El email ya está registrado"
    And permanece en la página de registro









