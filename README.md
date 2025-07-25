# 🍝 La Blanquita - Sistema de Gestión

> Backend de un sistema de gestión para una fábrica de pastas, desarrollado con Spring Boot. Este proyecto permite administrar productos, ventas y pedidos de manera eficiente.

---

## ✨ Características Principales

-   **Gestión de Productos:**
    -   Crear, leer, actualizar y eliminar productos.
    -   Soporte para baja lógica de productos, manteniéndolos en la base de datos pero marcándolos como inactivos.
    -   Validación para no crear productos con nombres duplicados si ya existen y están activos.
    -   Capacidad de reactivar un producto inactivo si se intenta crear nuevamente.

-   **Gestión de Ventas:**
    -   Registro de ventas con detalles de productos, fecha, forma de pago y total.
    -   Cálculo automático del total de la venta a partir de los precios y cantidades de los productos.
    -   Validación para asegurar que solo se puedan vender productos activos.

-   **Gestión de Pedidos:**
    -   Creación y seguimiento de pedidos, asociando una venta.
    -   Manejo de estados para cada pedido: `pendiente`, `en proceso` o `completado`.

---

## 🛠️ Tecnologías Utilizadas

-   **Lenguaje:** Java 17
-   **Framework:** Spring Boot 3.5.3
-   **Base de Datos:** MySQL
-   **ORM:** Spring Data JPA / Hibernate
---

## 🚀 Cómo Empezar

### Prerrequisitos

-   JDK 17 o superior.
-   Maven.
-   Una instancia de MySQL en ejecución.

### Pasos para la Instalación

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/francomarinozzi/api_lablanquita_springboot.git](https://github.com/francomarinozzi/api_lablanquita_springboot.git)
    cd api_lablanquita_springboot
    ```

2.  **Configurar la Base de Datos:**
    -   Crea una base de datos en MySQL con el nombre `la_blanquita_db`.
    -   Abre el archivo `src/main/resources/application.properties` y ajusta las credenciales (`spring.datasource.username` y `spring.datasource.password`) según tu configuración local.

3.  **Ejecutar la Aplicación:**
    -   Utiliza el wrapper de Maven incluido para iniciar el servidor:
        ```bash
        ./mvnw spring-boot:run
        ```
    -   La API estará disponible en `http://localhost:8080`.

---

## 🔌 Endpoints de la API

### Productos

-   `POST /api/productos`: Crea un nuevo producto.
-   `GET /api/productos`: Obtiene la lista de todos los productos.
-   `GET /api/productos/{id}`: Obtiene un producto por su ID.
-   `PUT /api/productos/{id}`: Actualiza la información de un producto existente.
-   `PATCH /api/productos/{id}/baja`: Realiza una baja lógica del producto.

### Ventas

-   `POST /api/ventas`: Crea una nueva venta.
-   `GET /api/ventas`: Obtiene el historial de todas las ventas.
-   `GET /api/ventas/{id}`: Obtiene una venta específica por su ID.
-   `PATCH /api/ventas/{id}/baja`: Realiza una baja lógica de la venta.

### Pedidos

-   `POST /api/pedidos`: Crea un nuevo pedido.
-   `GET /api/pedidos`: Obtiene la lista de todos los pedidos.
-   `GET /api/pedidos/{id}`: Obtiene un pedido por su ID.
-   `PATCH /api/pedidos/{id}/baja`: Realiza una baja lógica del pedido.
