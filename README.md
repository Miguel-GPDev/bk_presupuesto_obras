# Backend de presupuestos de obras (Spring Boot + PostgreSQL)

Backend en Java Spring Boot para gestionar presupuestos de obras.

## Funcionalidad principal
- Autenticación con usuario y contraseña usando Spring Security (HTTP Basic).
- Creación de presupuestos.
- Cada presupuesto contiene capítulos.
- Cada capítulo contiene partidas con:
  - unidad de medida
  - referencia
  - cantidad
  - precio
- Al crear un presupuesto:
  1. Se persiste en base de datos PostgreSQL.
  2. Se vuelven a leer los datos desde BD.
  3. Se genera un PDF en `generated-pdfs/` dentro del proyecto.

## Requisitos
- Java 17+
- Maven 3.9+
- PostgreSQL

## Configuración por variables de entorno
| Variable | Default | Descripción |
|---|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/presupuestos_db` | URL de la BD |
| `DB_USERNAME` | `postgres` | Usuario de PostgreSQL |
| `DB_PASSWORD` | `postgres` | Password de PostgreSQL |
| `APP_USER` | `admin` | Usuario para autenticación |
| `APP_PASSWORD` | `admin123` | Password para autenticación |
| `PORT` | `8080` | Puerto del backend |

## Ejecutar
```bash
mvn spring-boot:run
```

## Endpoints
### Crear presupuesto
`POST /api/presupuestos`

Autenticación: HTTP Basic.

Ejemplo de petición:
```json
{
  "nombre": "Presupuesto Vivienda Unifamiliar",
  "capitulos": [
    {
      "nombre": "Movimiento de tierras",
      "partidas": [
        {
          "unidadMedida": "m3",
          "referencia": "MT-001",
          "cantidad": 120.5,
          "precio": 18.75
        }
      ]
    }
  ],
  "empresa": {
    "nombreEmpresa": "Constructora Norte S.L.",
    "cif": "B12345678",
    "direccion": "Calle Mayor 10, Madrid",
    "telefono": "+34910000000",
    "email": "info@constructoranorte.com"
  },
  "cliente": {
    "nombreCliente": "Juan Pérez",
    "documento": "12345678Z",
    "direccion": "Av. Principal 99, Madrid",
    "telefono": "+34600000000",
    "email": "juanperez@email.com"
  }
}
```

Ejemplo con `curl`:
```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/presupuestos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre":"Presupuesto Vivienda",
    "capitulos":[
      {
        "nombre":"Cimentación",
        "partidas":[
          {"unidadMedida":"m3","referencia":"CIM-01","cantidad":12,"precio":125.50}
        ]
      }
    ]
  }'
```

### Listar presupuestos
`GET /api/presupuestos`


### Añadir capítulo a un presupuesto existente
`POST /api/presupuestos/{presupuestoId}/capitulos`

Ejemplo body:
```json
{
  "nombre": "Estructura"
}
```

### Añadir partida a un capítulo existente
`POST /api/presupuestos/capitulos/{capituloId}/partidas`

Ejemplo body:
```json
{
  "unidadMedida": "m2",
  "referencia": "EST-001",
  "cantidad": 25,
  "precio": 49.9
}
```

### CRUD de capítulos
- Crear: `POST /api/presupuestos/{presupuestoId}/capitulos`
- Listar: `GET /api/presupuestos/{presupuestoId}/capitulos`
- Obtener por id: `GET /api/presupuestos/{presupuestoId}/capitulos/{capituloId}`
- Actualizar: `PUT /api/presupuestos/{presupuestoId}/capitulos/{capituloId}`
- Eliminar: `DELETE /api/presupuestos/{presupuestoId}/capitulos/{capituloId}`

### CRUD de partidas
- Crear: `POST /api/presupuestos/capitulos/{capituloId}/partidas`
- Listar: `GET /api/presupuestos/capitulos/{capituloId}/partidas`
- Obtener por id: `GET /api/presupuestos/capitulos/{capituloId}/partidas/{partidaId}`
- Actualizar: `PUT /api/presupuestos/capitulos/{capituloId}/partidas/{partidaId}`
- Eliminar: `DELETE /api/presupuestos/capitulos/{capituloId}/partidas/{partidaId}`

### CRUD de encabezado de empresa
- Crear encabezado para presupuesto: `POST /api/encabezados/{presupuestoId}`
- Listar encabezados: `GET /api/encabezados`
- Obtener encabezado por presupuesto: `GET /api/encabezados/{presupuestoId}`
- Actualizar encabezado por presupuesto: `PUT /api/encabezados/{presupuestoId}`
- Eliminar encabezado por presupuesto: `DELETE /api/encabezados/{presupuestoId}`

Ejemplo body:
```json
{
  "nombreEmpresa": "Constructora Norte S.L.",
  "cif": "B12345678",
  "direccion": "Calle Mayor 10, Madrid",
  "telefono": "+34910000000",
  "email": "info@constructoranorte.com"
}
```

### CRUD de cliente
- Crear cliente para presupuesto: `POST /api/clientes/{presupuestoId}`
- Listar clientes: `GET /api/clientes`
- Obtener cliente por presupuesto: `GET /api/clientes/{presupuestoId}`
- Actualizar cliente por presupuesto: `PUT /api/clientes/{presupuestoId}`
- Eliminar cliente por presupuesto: `DELETE /api/clientes/{presupuestoId}`

Ejemplo body:
```json
{
  "nombreCliente": "Juan Pérez",
  "documento": "12345678Z",
  "direccion": "Av. Principal 99, Madrid",
  "telefono": "+34600000000",
  "email": "juanperez@email.com"
}
```

## Salida de PDF
Los PDFs se generan automáticamente en la carpeta y, cuando existen, incluyen los datos de empresa y cliente:

```text
generated-pdfs/
```


## Levantar PostgreSQL con Docker
Se incluye un `docker-compose.yml` y un script de inicialización SQL en `docker/postgres/init/01-schema.sql`.

### 1) Iniciar contenedor
```bash
docker compose up -d postgres
```

### 2) Verificar estado
```bash
docker compose ps
docker compose logs -f postgres
```

### 3) Detener contenedor
```bash
docker compose down
```

> El script SQL se ejecuta automáticamente la **primera vez** que se crea el volumen de datos.
> Si quieres relanzar la inicialización desde cero:
```bash
docker compose down -v
docker compose up -d postgres
```
