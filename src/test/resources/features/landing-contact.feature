Feature: Formulario de contacto
  Como visitante interesado
  Quiero contactar al equipo de OnControl
  Para obtener más información o resolver dudas

  Scenario: Enviar consulta exitosamente
    Given estoy en la sección "Contacto"
    When completo el formulario con mis datos
      | Nombre | Email | Tipo Usuario | Mensaje |
      | Juan Pérez | juan@email.com | Paciente | ¿Cómo puedo registrarme? |
    And hago clic en "Enviar Mensaje"
    Then veo el mensaje "Mensaje enviado exitosamente"
    And veo "Te contactaremos pronto"
    And recibo email de confirmación

  Scenario: Intento de envío con datos incompletos
    Given estoy completando el formulario de contacto
    When dejo el campo email vacío
    And hago clic en "Enviar Mensaje"
    Then veo el mensaje "Por favor complete todos los campos"
    And el formulario no se envía











