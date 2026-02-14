CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS presupuestos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS capitulos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    presupuesto_id BIGINT NOT NULL,
    CONSTRAINT fk_capitulos_presupuestos
        FOREIGN KEY (presupuesto_id)
        REFERENCES presupuestos(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS partidas (
    id BIGSERIAL PRIMARY KEY,
    unidad_medida VARCHAR(30) NOT NULL,
    referencia VARCHAR(80) NOT NULL,
    cantidad NUMERIC(15,3) NOT NULL CHECK (cantidad >= 0),
    precio NUMERIC(15,2) NOT NULL CHECK (precio >= 0),
    capitulo_id BIGINT NOT NULL,
    CONSTRAINT fk_partidas_capitulos
        FOREIGN KEY (capitulo_id)
        REFERENCES capitulos(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS encabezados_empresa (
    id BIGSERIAL PRIMARY KEY,
    nombre_empresa VARCHAR(120) NOT NULL,
    cif VARCHAR(20) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(120) NOT NULL,
    presupuesto_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_encabezados_presupuestos
        FOREIGN KEY (presupuesto_id)
        REFERENCES presupuestos(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS clientes (
    id BIGSERIAL PRIMARY KEY,
    nombre_cliente VARCHAR(120) NOT NULL,
    documento VARCHAR(30) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(120) NOT NULL,
    presupuesto_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_clientes_presupuestos
        FOREIGN KEY (presupuesto_id)
        REFERENCES presupuestos(id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_capitulos_presupuesto_id ON capitulos(presupuesto_id);
CREATE INDEX IF NOT EXISTS idx_partidas_capitulo_id ON partidas(capitulo_id);
