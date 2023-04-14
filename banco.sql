-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Sequence and defined type
CREATE SEQUENCE IF NOT EXISTS datos_id_seq;

-- Table Definition
CREATE TABLE "public"."datos" (
    "seguridadsocial" int8,
    "dni" bpchar NOT NULL,
    "nombre" bpchar,
    "apellidos" bpchar,
    "provincia" bpchar,
    "calle" bpchar,
    "numero" int2,
    "codigopostal" int4,
    "iban" bpchar,
    "entidad" bpchar,
    "cuantia" float4,
    "atraso" float8,
    "inactivo" bool DEFAULT false,
    "id" int4 DEFAULT nextval('datos_id_seq'::regclass),
    PRIMARY KEY ("dni")
);

-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Sequence and defined type
CREATE SEQUENCE IF NOT EXISTS historial_id_seq;

-- Table Definition
CREATE TABLE "public"."historial" (
    "id" int4 NOT NULL DEFAULT nextval('historial_id_seq'::regclass),
    "dni_registro" bpchar,
    "fecha" timestamp,
    "accion" bpchar,
    "valoranterior" bpchar,
    CONSTRAINT "historial_dni_registro_fkey" FOREIGN KEY ("dni_registro") REFERENCES "public"."datos"("dni"),
    PRIMARY KEY ("id")
);

INSERT INTO "public"."datos" ("seguridadsocial", "dni", "nombre", "apellidos", "provincia", "calle", "numero", "codigopostal", "iban", "entidad", "cuantia", "atraso", "inactivo", "id") VALUES
(55544477788, '11223344A', 'ANGEL', 'MARQUEZ VAQUERO', 'MADRID', 'CALLE NUEVA', 65, 25273, 'ES7701827029724388976366', 'BBVA', 200, 15, 'f', 1);

INSERT INTO "public"."historial" ("id", "dni_registro", "fecha", "accion", "valoranterior") VALUES
(1, '11223344A', '2023-04-14 11:51:43', 'DESACTIVADO', 'Se ha desactivado el registro.');