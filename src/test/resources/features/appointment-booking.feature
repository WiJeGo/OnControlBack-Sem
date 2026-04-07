Feature: Agendamiento de citas médicas
  Como paciente
  Quiero agendar citas con médicos especialistas
  Para recibir atención médica oncológica

  Scenario: Agendar cita médica exitosamente
    Given el paciente ha iniciado sesión
    And está en la sección "Agendar Cita"
    When busca especialidad "Oncología"
    And selecciona al doctor "Dr. Williams Gongora"
    And elige fecha "15 de octubre" y hora "10:00 AM"
    And selecciona tipo de cita "Consulta"
    And ingresa notas "Chequeo regular"
    And hace clic en "Confirmar Cita"
    Then ve el mensaje "Cita agendada exitosamente"
    And la cita aparece en "Mis Citas" con estado "Programada"
    And recibe notificación de confirmación

  Scenario: Intento de agendar sin doctor disponible
    Given el paciente está agendando una cita
    When intenta agendar con un doctor que no existe
    Then ve el mensaje "Doctor no encontrado"
    And la cita no se agenda





