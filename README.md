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
  ]
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

## Salida de PDF
Los PDFs se generan automáticamente en la carpeta:

```text
generated-pdfs/
```
