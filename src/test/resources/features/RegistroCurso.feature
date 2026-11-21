# language: es
Característica: Registro de cursos

  Escenario: Registro de curso valido
    Dado que ingreso los datos del curso "Selenium Avanzado" con precio 400.0 y 20 vacantes
    Cuando envio la solicitud para registrar el curso
    Entonces el sistema responde con codigo 200
    Y devuelve el nombre del curso "Selenium Avanzado"

  Escenario: Registro con precio invalido
    Dado que ingreso los datos del curso "Curso Invalido" con precio 0.0 y 10 vacantes
    Cuando envio la solicitud para registrar el curso
    Entonces el sistema rechaza el registro con un codigo de error 4xx o 5xx