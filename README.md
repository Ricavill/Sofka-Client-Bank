# Proyecto Banca Virtual - Sofka

## 📌 Descripción
Proyecto de banca virtual desarrollado para **Sofka**, dividido en **dos microservicios**:  
- **Microservicio de información de cliente**  
- **Microservicio de transacciones**  

El sistema permite gestionar clientes, cuentas y transacciones bancarias, incluyendo autenticación y validación mediante **OAuth2**.

---

## 🚀 Implementaciones

### Microservicio de Cliente
Incluye funcionalidades básicas como:
- Crear cliente  
- Buscar cliente  
- Editar cliente  
- Login (con generación de JWT)  
- Validación de token para proteger endpoints  

Este servicio además provee validación de token al microservicio de transacciones mediante **OAuth2**.

### Microservicio de Transacciones
Contiene operaciones principales como:
- Registrar nueva cuenta  
- Realizar transacciones (retiros, depósitos, etc.)  
- Revisar reportes de transacciones  

---

## 🧪 Testing
Se realizaron **tests unitarios y de integración** para demostrar las capacidades con testing.

---

## ⚙️ Instalación y Ejecución
git clone https://github.com/Ricavill/Sofka-Client-Bank.git
docker compose up --build

🐳 Docker

El proyecto fue dockerizado para mantener todos los componentes en contenedores, lo que facilita el setup y la comunicación entre ellos:

La base de datos PostgreSQL se ejecuta en un contenedor.

Los dos microservicios (cliente y transacciones) también se ejecutan en contenedores separados.

Al usar Docker, se asegura que los servicios puedan comunicarse fácilmente y que la instalación en nuevos entornos sea rápida y consistente.

Además, el proyecto incluye:
- 📸 **Capturas de pantalla** de resultados  
- 🗂️ **Archivos JSON de prueba** con ejemplos de requests y responses de los microservicios  

Esto permite validar rápidamente el comportamiento de las APIs.
