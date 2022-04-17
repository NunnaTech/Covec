create table covec_bd.categorias
(
    id_categoria int auto_increment
        primary key,
    nombre       varchar(45) null
);

create table covec_bd.municipios
(
    id_municipio int auto_increment
        primary key,
    nombre       varchar(45) null
);

create table covec_bd.colonias
(
    id_colonia    int auto_increment
        primary key,
    nombre        varchar(45) null,
    codigo_postal varchar(45) null,
    id_municipio  int         null,
    constraint fk_Colonias_Municipio
        foreign key (id_municipio) references covec_bd.municipios (id_municipio)
);

create index fk_Colonias_Municipio_idx
    on covec_bd.colonias (id_municipio);

create table covec_bd.comites
(
    id_comites int auto_increment
        primary key,
    id_colonia int        null,
    activo     tinyint(1) null,
    constraint fk_Comites_Colonia
        foreign key (id_colonia) references covec_bd.colonias (id_colonia)
);

create index fk_Comites_Colonia_idx
    on covec_bd.comites (id_colonia);

create table covec_bd.roles
(
    id_rol    int auto_increment
        primary key,
    authority varchar(45) null,
    constraint roles_authority_uindex
        unique (authority)
);

create table covec_bd.users
(
    id_usuario           int auto_increment
        primary key,
    username             varchar(50)       null,
    password             varchar(100)      null,
    reset_password_token varchar(100)      null,
    telefono             varchar(45)       null,
    imagen               varchar(256)      null,
    tipo_usuario         varchar(45)       null,
    enabled              tinyint default 1 null,
    nombre_completo      varchar(45)       null,
    constraint usuarios_telefono_uindex
        unique (telefono),
    constraint usuarios_username_uindex
        unique (username)
);

create table covec_bd.administrador
(
    id_usuario int not null
        primary key,
    constraint fk_Administrador_Usuarios
        foreign key (id_usuario) references covec_bd.users (id_usuario)
);

create table covec_bd.authorities
(
    id_usuario int not null,
    id_rol     int not null,
    primary key (id_rol, id_usuario),
    constraint authorities_roles_id_rol_fk
        foreign key (id_rol) references covec_bd.roles (id_rol),
    constraint authorities_users_id_usuario_fk
        foreign key (id_usuario) references covec_bd.users (id_usuario)
);

create table covec_bd.enlace
(
    id_usuario   int         not null
        primary key,
    num_empleado varchar(45) null,
    id_municipio int         null,
    constraint Enlace_MunicipioFK
        foreign key (id_municipio) references covec_bd.municipios (id_municipio),
    constraint enlace_users_id_usuario_fk
        foreign key (id_usuario) references covec_bd.users (id_usuario)
);

create table covec_bd.integrante
(
    id_usuario    int        not null
        primary key,
    es_presidente tinyint(1) null,
    id_comites    int        null,
    constraint fk_Integrantes_Usuarios
        foreign key (id_usuario) references covec_bd.users (id_usuario),
    constraint integrante_comites_id_comites_fk
        foreign key (id_comites) references covec_bd.comites (id_comites)
);

create table covec_bd.incidencias
(
    id_incidencias int auto_increment
        primary key,
    descripcion    varchar(128) null,
    fecha_registro date         null,
    pagar          tinyint(1)   null,
    estatus        varchar(45)  null,
    id_categoria   int          null,
    id_integrante  int          null,
    monto          double       null,
    constraint fk_Incidencias_Categoria
        foreign key (id_categoria) references covec_bd.categorias (id_categoria),
    constraint fk_Incidencias_Integrante
        foreign key (id_integrante) references covec_bd.integrante (id_usuario)
);

create table covec_bd.comentario_incidencia
(
    id_comentario_incidencia int auto_increment
        primary key,
    id_enlace                int          null,
    id_incidencia            int          null,
    comentario               varchar(250) null,
    es_enlace                tinyint      null,
    constraint fk_Comentario_Incidencia
        foreign key (id_incidencia) references covec_bd.incidencias (id_incidencias),
    constraint fk_Comentario_Incidencia_Enlace
        foreign key (id_enlace) references covec_bd.enlace (id_usuario)
);

create index fk_Comentario_Incidencia_Enlace_idx
    on covec_bd.comentario_incidencia (id_enlace);

create index fk_Comentario_Incidencia_Integrante_idx
    on covec_bd.comentario_incidencia (id_incidencia);

create table covec_bd.evidencias
(
    id_evidencia  int auto_increment
        primary key,
    evidencia     varchar(128) null,
    id_incidencia int          null,
    constraint evidencias___incidenciafk
        foreign key (id_incidencia) references covec_bd.incidencias (id_incidencias)
);

create index fk_Incidencias_Categoria_idx
    on covec_bd.incidencias (id_categoria);

create index fk_Incidencias_Integrante_idx
    on covec_bd.incidencias (id_integrante);

create table covec_bd.operaciones
(
    id_operacion int auto_increment
        primary key,
    accion       varchar(45)  null,
    idusuario    int          null,
    anterior     varchar(512) null,
    actual       varchar(512) null,
    fecha_hora   timestamp    null,
    constraint fk_Operaciones_Usuarios
        foreign key (idusuario) references covec_bd.users (id_usuario)
);

create index fk_Operaciones_Usuarios_idx
    on covec_bd.operaciones (idusuario);

create table covec_bd.pagos
(
    id_pagos       int auto_increment
        primary key,
    id_incidencias int          null,
    cantidad       double       null,
    fecha_pago     varchar(255) null,
    constraint pagos_incidencias_id_incidencias_fk
        foreign key (id_incidencias) references covec_bd.incidencias (id_incidencias)
);

create index fk_Pagos_Integrante_idx
    on covec_bd.pagos (id_incidencias);

create table covec_bd.sesiones
(
    id_sesiones  int auto_increment
        primary key,
    fecha_sesion timestamp null,
    id_usuario   int       null,
    constraint fk_Sesiones_Usuarios
        foreign key (id_usuario) references covec_bd.users (id_usuario)
            on delete cascade
);

create index fk_Sesiones_Usuarios_idx
    on covec_bd.sesiones (id_usuario);

DELIMITER $$;
DROP PROCEDURE IF EXISTS sesiones $$;
CREATE PROCEDURE sesiones(IN eid_usuario int)
BEGIN
INSERT INTO sesiones(fecha_sesion,id_usuario) VALUES(current_timestamp(),eid_usuario);
END $$;



DELIMITER $$;
DROP PROCEDURE IF EXISTS operaciones $$;
CREATE PROCEDURE operaciones(IN eaccion varchar(15), IN eid_usuario int, IN eanterior varchar(512), IN eactual varchar(512))
BEGIN
INSERT INTO operaciones(accion, idusuario, anterior, actual, fecha_hora) VALUES(eaccion,eid_usuario,eanterior, eactual,current_timestamp());
END $$;