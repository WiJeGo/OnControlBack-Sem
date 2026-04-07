Feature: Visualización de citas agendadas
  Como médico oncólogo
  Quiero ver todas mis citas programadas
  Para organizar mi agenda médica

  Scenario: Ver lista de citas del día
    Given el médico ha iniciado sesión
    When accede a "Mis Citas"
    Then ve todas sus citas programadas ordenadas por fecha
    And cada cita muestra: paciente, fecha, hora y tipo
    And puede hacer clic en una cita para ver detalles completos

  Scenario: Médico sin citas programadas
    Given el médico no tiene citas agendadas
    When accede a "Mis Citas"
    Then ve el mensaje "No tienes citas agendadas"
    And ve la opción "Ver solicitudes de citas"