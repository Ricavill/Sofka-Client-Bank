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

# ⚠️ IMPORTANTE

Para ejecutar correctamente el proyecto es necesario crear un archivo **`.env`** en cada uno de los microservicios:  

### 🔹 MicroServicio 1 (Información de Cliente)
```env
DB_PRIMARY_DATASOURCE_URL=jdbc:postgresql://db:5432/sofkadb?currentSchema=ms1
DB_USERNAME=postgres
DB_PASSWORD=yoshi123
JWT_SECRET=EstaEsUnaFraseSecretaParaPoderProbar
JWT_EXPIRATION_MINUTES=6000000
HASH_STRENGTH=10
KEY_ID=key-2025-08
```
### 🔹 MicroServicio 2 (Transacciones)
```env
DB_PRIMARY_DATASOURCE_URL=jdbc:postgresql://db:5432/sofkadb?currentSchema=ms2
DB_USERNAME=postgres
DB_PASSWORD=yoshi123
```
✅ Nota: Estos valores fueron los usados para desplegar el programa. Se pueden modificar según el entorno o las necesidades, pero sirven como datos de prueba funcionales.
