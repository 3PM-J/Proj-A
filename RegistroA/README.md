# Sistema de Registro de Pacientes

Una aplicación de escritorio desarrollada con Spring Boot y JavaFX para la gestión de registros de pacientes.

## Características

- **Interfaz gráfica moderna**: Desarrollada con JavaFX
- **Gestión completa de pacientes**: Crear, leer, actualizar y eliminar registros
- **Validación de datos**: Validación robusta de formularios
- **Búsqueda avanzada**: Búsqueda por nombre, apellido, DNI o email
- **Base de datos**: Integración con H2 Database (en memoria)
- **Arquitectura limpia**: Patrón MVC con Spring Boot

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.5.0**
- **JavaFX 21.0.1**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **Maven**

## Requisitos del sistema

- Java 17 o superior
- Maven 3.6 o superior

## Instalación y ejecución

1. **Clonar el repositorio**
   ```bash
   git clone <url-del-repositorio>
   cd RegistroA
   ```

2. **Compilar el proyecto**
   ```bash
   ./mvnw clean compile
   ```

3. **Ejecutar la aplicación**
   ```bash
   ./mvnw javafx:run
   ```

   O alternativamente:
   ```bash
   ./mvnw spring-boot:run
   ```

## Estructura del proyecto

```
src/
├── main/
│   ├── java/
│   │   └── upeu/edu/pe/registroa/
│   │       ├── config/          # Configuraciones
│   │       ├── controller/      # Controladores JavaFX
│   │       ├── exception/       # Excepciones personalizadas
│   │       ├── model/          # Entidades JPA
│   │       ├── repository/     # Repositorios Spring Data
│   │       ├── service/        # Servicios de negocio
│   │       ├── util/           # Utilidades
│   │       ├── JavaFXApplication.java
│   │       └── RegistroAApplication.java
│   └── resources/
│       ├── fxml/               # Archivos FXML
│       ├── data.sql           # Datos de ejemplo
│       └── application.properties
└── test/                      # Pruebas unitarias
```

## Funcionalidades

### Gestión de Pacientes
- **Registrar**: Agregar nuevos pacientes con validación completa
- **Actualizar**: Modificar información existente
- **Eliminar**: Remover registros con confirmación
- **Buscar**: Filtrar pacientes por múltiples criterios

### Validaciones implementadas
- Nombres y apellidos: mínimo 2 caracteres
- DNI: exactamente 8 dígitos numéricos
- Teléfono: 9 dígitos (opcional)
- Email: formato válido (opcional)
- Fecha de nacimiento: no puede ser futura
- Género: selección obligatoria

### Características de la interfaz
- Formulario intuitivo con campos claramente etiquetados
- Tabla con todos los registros
- Búsqueda en tiempo real
- Botones con estados dinámicos
- Mensajes de confirmación y error
- Doble clic para editar registros

## Base de datos

La aplicación utiliza H2 Database en memoria con datos de ejemplo precargados. Para acceder a la consola de H2:

1. Ejecutar la aplicación
2. Abrir navegador en: `http://localhost:8080/h2-console`
3. Usar la configuración:
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Usuario: `sa`
   - Contraseña: (vacía)

## Pruebas

Ejecutar las pruebas unitarias:
```bash
./mvnw test
```

## Configuración

### application.properties
```properties
# Configuración de base de datos H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# Configuración JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Consola H2
spring.h2.console.enabled=true
```

## Contribuir

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Autor

Desarrollado como proyecto educativo para la Universidad Peruana Unión.

## Soporte

Para reportar bugs o solicitar nuevas características, por favor crear un issue en el repositorio.