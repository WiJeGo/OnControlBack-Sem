Feature: Navegación en landing page
  Como visitante de OnControl
  Quiero navegar fácilmente por la página
  Para conocer el producto y sus beneficios

  Scenario: Visualizar información principal
    Given estoy visitando la página principal de OnControl
    When la página carga completamente
    Then veo el título "Apoyo integral para pacientes oncológicos"
    And veo una descripción clara del propósito
    And veo una imagen de médico y paciente usando la plataforma
    And veo el menú principal con: Características, Beneficios, Problemática, Testimonios, Contacto
    And el menú permanece visible cuando hago scroll

  Scenario Outline: Navegar a diferentes secciones
    Given estoy en la página principal
    When hago clic en el menú "<seccion>"
    Then la página se desplaza automáticamente a "<seccion>"
    And veo el contenido relacionado a "<contenido_esperado>"

    Examples:
      | seccion         | contenido_esperado                           |
      | Características | Calendario, medicamentos, comunicación       |
      | Beneficios      | Ventajas para médicos, pacientes y familiares|
      | Problemática    | Estadísticas del cáncer en Perú              |
      | Testimonios     | Experiencias de usuarios reales              |



