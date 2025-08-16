# Guía de Contribución

¡Gracias por tu interés en contribuir!

## Flujo sugerido
1. Crea un fork y una rama: `feat/mi-mejora`.
2. Asegúrate de que el código compila y las pruebas pasan: `mvn -q -DskipTests=false test`.
3. Abre un Pull Request describiendo la motivación y los cambios.

## Estilo de código
- Java 17, convenciones estándar.
- Métodos cortos, clases con una responsabilidad clara.
- Nombres descriptivos en español o inglés de forma consistente.

## Pruebas
- Añade tests unitarios para nueva lógica.
- Si cambias endpoints o parsing, agrega pruebas que validen errores y éxitos.
