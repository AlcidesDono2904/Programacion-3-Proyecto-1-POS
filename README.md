# 🏪 Sistema POS (Point of Sale) - Java Enterprise

[![Java](https://img.shields.io/badge/Java-21%2B-ED8B00?style=flat&logo=openjdk&logoColor=white)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-C71A36?style=flat&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-4479A1?style=flat&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Swing](https://img.shields.io/badge/GUI-Java%20Swing-blue?style=flat&logo=java&logoColor=white)](https://docs.oracle.com/javase/tutorial/uiswing/)

## 📋 Descripción del Proyecto

Sistema completo de **Punto de Venta (POS)** desarrollado en Java con arquitectura cliente-servidor distribuida. El sistema permite la gestión integral de ventas, inventarios, clientes y reportes estadísticos para pequeñas y medianas empresas.

### 🎯 Características Principales

- **✅ Arquitectura Cliente-Servidor** con comunicación por sockets TCP
- **✅ Sistema multiusuario** con gestión concurrente de sesiones
- **✅ Base de datos MySQL** con transacciones ACID
- **✅ Interfaz gráfica** desarrollada en Java Swing
- **✅ Generación de reportes** en PDF con iTextPDF
- **✅ Gestión completa** de inventarios, clientes, cajeros y facturas
- **✅ Sistema de autenticación** y control de usuarios
- **✅ Estadísticas y reportes** de ventas por categorías

## 🏗️ Arquitectura del Sistema

### Arquitectura de 3 Capas Distribuidas

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │    Backend      │    │    Database     │
│   (Swing GUI)   │◄──►│  (TCP Server)   │◄──►│     (MySQL)     │
│                 │    │                 │    │                 │
│  • Interfaces   │    │  • Lógica       │    │  • Datos        │
│  • Validación   │    │  • Sockets      │    │  • Persistencia │
│  • Reportes     │    │  • Concurrencia │    │  • Integridad   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### 📁 Estructura del Proyecto

```
Programacion-3-Proyecto-1-POS/
├── 🖥️ FrontEndPOS/          # Cliente - Interfaz gráfica
│   ├── presentation/        # Vistas y controladores MVC
│   │   ├── login/          # Autenticación de usuarios
│   │   ├── facturacion/    # Módulo de ventas
│   │   ├── clientes/       # Gestión de clientes
│   │   ├── productos/      # Gestión de inventario
│   │   ├── cajeros/        # Gestión de empleados
│   │   ├── historico/      # Consultas históricas
│   │   └── estadistica/    # Reportes y estadísticas
│   └── logic/              # Servicios cliente y comunicación
├── 🗄️ BackEndPos/            # Servidor - Lógica de negocio
│   ├── data/               # DAOs y acceso a datos
│   ├── logic/              # Server, Workers y servicios
│   └── resources/          # Configuración BD
├── 📦 Entidades/             # Módulo compartido - DTOs
│   └── logic/              # Clases de dominio
└── 📊 Pos.sql               # Scripts de base de datos
```

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 21** - Lenguaje principal
- **Maven** - Gestión de dependencias
- **MySQL 8.0+** - Base de datos relacional
- **JDBC** - Conectividad con base de datos
- **Sockets TCP** - Comunicación cliente-servidor
- **Threading** - Manejo de concurrencia

### Frontend
- **Java Swing** - Interfaz gráfica de usuario
- **MVC Pattern** - Arquitectura de presentación
- **Observer Pattern** - Manejo de eventos

### Herramientas y Librerías
- **iTextPDF 8.0.5** - Generación de reportes PDF
- **Jakarta XML Bind** - Serialización de datos
- **Maven Surefire** - Testing framework

## ⚡ Funcionalidades Implementadas

### 👥 Gestión de Usuarios
- Sistema de login con validación de credenciales
- Gestión multiusuario concurrente
- Control de sesiones activas
- Notificaciones en tiempo real de conexiones

### 🛒 Módulo de Ventas (Facturación)
- Creación de facturas con múltiples líneas
- Búsqueda de productos en tiempo real
- Cálculo automático de descuentos por cliente
- Validación de existencias antes de venta
- Gestión de cantidades y precios

### 📦 Gestión de Inventario
- CRUD completo de productos
- Categorización de productos
- Control de existencias
- Actualización automática post-venta
- Búsqueda y filtrado avanzado

### 👤 Gestión de Clientes
- Registro completo de clientes
- Sistema de descuentos personalizados
- Historial de compras
- Información de contacto

### 📊 Reportes y Estadísticas
- Reportes de ventas por categoría
- Análisis de rangos de fechas
- Exportación a PDF
- Gráficos estadísticos
- Historial detallado de transacciones

## 🚀 Instalación y Configuración

### Prerrequisitos
```bash
# Versiones requeridas
Java 21+
Maven 3.6+
MySQL 8.0+
```

### 1. Configuración de Base de Datos
```sql
# Ejecutar el script SQL
mysql -u root -p < Pos.sql
```

### 2. Configuración del Backend
```properties
# BackEndPos/src/main/resources/database.properties
database_driver=com.mysql.cj.jdbc.Driver
database_server=localhost
database_port=3306
database_user=tu_usuario
database_password=tu_password
database_name=Pos
```

### 3. Compilación y Ejecución

```bash
# Compilar módulos
cd Entidades && mvn clean install
cd ../BackEndPos && mvn clean compile
cd ../FrontEndPOS && mvn clean compile

# Ejecutar servidor (Terminal 1)
cd BackEndPos
mvn exec:java -Dexec.mainClass="pos.Application"

# Ejecutar cliente (Terminal 2)
cd FrontEndPOS
mvn exec:java -Dexec.mainClass="pos.Application"
```

### 4. Credenciales por Defecto
```
Usuario: admin
Contraseña: root
```

## 💻 Capturas de Pantalla

### Login y Autenticación
Sistema de login con validación de credenciales y gestión de sesiones.

### Panel Principal de Facturación
Interfaz intuitiva para procesamiento de ventas con búsqueda de productos en tiempo real.

### Gestión de Inventario
CRUD completo con categorización y control de existencias.

## 🔧 Arquitectura Técnica Detallada

### Patrón de Comunicación Cliente-Servidor

#### Protocolo de Comunicación
```java
// Protocolo personalizado con códigos de operación
SYNC/ASYNC Connection → Worker Pool → Service Layer → DAO → Database
```

#### Gestión de Concurrencia
- **Thread Pool** para manejo de múltiples clientes
- **Synchronized Collections** para thread-safety
- **Worker Pattern** para procesamiento asíncrono

#### Sistema de Notificaciones Asíncronas en Tiempo Real
El sistema implementa un **mecanismo dual de comunicación** para notificaciones push:

```java
// Arquitectura de comunicación dual
Cliente ──SYNC──► Servidor (Operaciones CRUD)
Cliente ◄─ASYNC─ Servidor (Notificaciones Push)
```

**Características del sistema asíncrono:**
- **🔄 Conexiones duales**: Cada cliente mantiene 2 sockets (SYNC + ASYNC)
- **⚡ Notificaciones instantáneas**: Login/Logout de usuarios en tiempo real
- **🧵 Threading dedicado**: `SocketListener` con hilo exclusivo para eventos
- **📡 Broadcasting**: Servidor notifica a todos los clientes conectados
- **🔒 Thread-safety**: Operaciones sincronizadas para concurrencia segura
- **🎯 Event Delivery**: `SwingUtilities.invokeLater()` para UI thread-safe

**Flujo de notificaciones:**
1. Usuario se conecta → `Server.notificarLogeo()` → Workers sincronizados
2. Cada Worker envía notificación asíncrona a su cliente ASYNC
3. `SocketListener` recibe → `ThreadListener` procesa → UI actualizada

#### Persistencia de Datos
- **DAO Pattern** para acceso a datos
- **Connection Pooling** para optimización
- **Transacciones ACID** para integridad

## 📈 Características Técnicas Destacadas

### 🔒 Seguridad
- Validación de entrada en cliente y servidor
- Manejo seguro de conexiones de base de datos
- Control de sesiones y timeouts

### 📊 Performance
- Conexiones persistentes con pool de workers
- Carga lazy de datos para optimizar memoria
- Índices de base de datos para consultas rápidas

### 🎨 UX/UI
- Interfaz Nimbus Look & Feel
- Validación visual en tiempo real
- Navegación por pestañas intuitiva
- Generación de PDFs profesionales

## 🎓 Conceptos de Ingeniería de Software Aplicados

- **✅ Arquitectura en Capas** - Separación clara de responsabilidades
- **✅ Patrón MVC** - Organización de la interfaz gráfica
- **✅ Patrón DAO** - Abstracción del acceso a datos
- **✅ Singleton Pattern** - Gestión de servicios únicos
- **✅ Observer Pattern** - Manejo de eventos de UI
- **✅ Factory Pattern** - Creación de objetos especializados
- **✅ Dependency Injection** - Inversión de control
- **✅ Socket Programming** - Comunicación distribuida dual (SYNC/ASYNC)
- **✅ Multi-threading** - Concurrencia y paralelismo
- **✅ Producer-Consumer Pattern** - Para notificaciones asíncronas
- **✅ Thread-Safe Collections** - Sincronización de datos compartidos
- **✅ Event-Driven Architecture** - Sistema de notificaciones push
- **✅ Listener Pattern** - Gestión de eventos de conexión/desconexión

## 👨‍💻 Sobre los Desarrolladores

Este proyecto fue desarrollado como parte del curso de **Programación 3** demostrando competencias en:

- **Desarrollo Full-Stack** con Java 
- **Arquitecturas Distribuidas** cliente-servidor
- **Bases de Datos Relacionales** y SQL avanzado
- **Interfaces Gráficas** con Java Swing
- **Patrones de Diseño** y mejores prácticas
- **Programación Concurrente** y multi-threading
- **Gestión de Proyectos** con Maven

---

*⭐ Si te gustó este proyecto, no olvides darle una estrella en GitHub*

