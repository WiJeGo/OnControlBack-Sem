Feature: Gestión de perfil de usuario
  Como usuario del sistema
  Quiero gestionar mi información personal
  Para mantener mis datos actualizados

  Scenario: Ver información de perfil de doctor
    Given el doctor ha iniciado sesión
    When accede a "Mi Perfil"
    Then ve su nombre completo "Dr. Williams Gongora"
    And ve su identificador único que comienza con "DOC-"
    And ve su especialidad médica
    And ve su información de contacto

  Scenario: Ver información de perfil de paciente
    Given el paciente ha iniciado sesión
    When accede a "Mi Perfil"
    Then ve su nombre completo "Johan Perez"
    And ve su identificador único que comienza con "PAT-"
    And ve su historial médico
    And ve sus tratamientos activos

  Scenario: Perfil activo desde la creación
    Given un nuevo usuario completa su registro
    When accede por primera vez a su perfil
    Then su cuenta está activa automáticamente
    And puede usar todas las funcionalidades del sistema







