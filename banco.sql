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
    "id" int4 NOT NULL DEFAULT nextval('datos_id_seq'::regclass),
    PRIMARY KEY ("dni")
);

-- Table Definition
CREATE TABLE "public"."historial" (
    "id" int4 NOT NULL,
    "dni_registro" bpchar,
    "fecha" timestamp,
    "accion" bpchar,
    "valoranterior" bpchar,
    CONSTRAINT "historial_dni_registro_fkey" FOREIGN KEY ("dni_registro") REFERENCES "public"."datos"("dni"),
    PRIMARY KEY ("id")
);

INSERT INTO "public"."datos" ("seguridadsocial", "dni", "nombre", "apellidos", "provincia", "calle", "numero", "codigopostal", "iban", "entidad", "cuantia", "atraso", "inactivo", "id") VALUES
(88014420561, '19669426X', 'CARLES', 'DE MIGUEL ALCAIDE', 'ÁVILA', 'PASEO DE BARCELONA', 100, 5262, 'ES5901825888106723050078', 'BANKIA', 2023, 2002, 'f', 48);

INSERT INTO "public"."historial" ("id", "dni_registro", "fecha", "accion", "valoranterior") VALUES
(94, '19669426X', '2023-04-13 12:20:49', 'MODIFICAR', 'Nombre: CARLES');
