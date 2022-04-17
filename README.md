
<div align="center" style="display:flex;flex-direction:column;">
    <img width="300" src="https://user-images.githubusercontent.com/67834146/163696837-14e86349-21e6-4af1-994f-9b2a72438aa2.png" alt="Probien logo"/>
  <h2>Aplicación Web (MVC) Para Gestionar Incidentes Vecinales</h2>
  <p>
    <a target="_blank" href="https://crowdin.com/project/excalidraw">
      <img src="https://img.shields.io/badge/License-GPL%20v3-yellow.svg">
    </a>
        <a target="_blank" href="https://crowdin.com/project/excalidraw">
      <img src="https://img.shields.io/github/last-commit/ThePandaDevs/Covec">
    </a>
    <h4>COVEC de Comités Vecinales</h4>
  </p>
</div>

## Acerca del proyecto
<br>

![image](https://user-images.githubusercontent.com/67834146/163697329-3e21df1a-224d-495f-b148-d207b58c4d9d.png)

<br>
El proyecto consiste en brindar a todos los municipios de la entidad la oportunidad de
gestionar a los comités vecinales que existen en cada colonia de su zona geográfica, estos
comités están conformados por ciudadanos que son los encargados de notificar a los enlaces
de cada ayuntamiento las necesidades que tienen como comunidad, por ejemplo: Fugas de
agua, problemas de alcantarillado, lámparas fundidas, etc. Por medio de estas notificaciones
los enlaces canalizan las solicitudes a las áreas correspondientes de los ayuntamientos para
ser atendidas y en determinado momento requerir algún cobro por recuperación de material.

### Reglas de negocio
Para el desarrollo de la aplicación se contemplaron los siguientes roles:

#### Administrador
- Administrar a todos los enlaces responsables de cada municipio registrado en el
sistema.
- Administrar los accesos de cada enlace registrado, permitiendo restablecer los accesos
en caso de ser necesario, dar de alta nuevos enlaces e inhabilitación de usuarios.
- Administrar municipios en la plataforma con el objetivo de mantener actualizada la lista
en caso de formación de nuevos municipios.
- Administrar las categorías de servicios públicos a atender por ejemplo: Drenaje,
Alumbrado público, Agua potable, Seguridad, Recolección de basura, etc.

#### Enlace
- Administrar la lista de colonias pertenecientes a su municipio.
- Administrar a los comités vecinales que pertenecen a sus colonias registradas, tomar en
cuenta que una colonia puede tener más de un comité.
- Los comités vecinales deberán estar conformados por al menos 3 o máximo 6
ciudadanos con identificación oficial, uno de ellos deberá ser marcado presidente a
quien se le creará un acceso al sistema para registrar incidencias.
- Visualizar las solicitudes realizadas por los comités pertenecientes a su municipio para
cambiar el estado de cada una (Cancelada, Canalizada, Atendida) y realizar
comentarios en cada solicitud con el objetivo de mantener contacto con los comités.
- Marcar una solicitud como “por cobrar” e incluir el monto que deberá ser cobrado al
comité.

#### Presidente de comité
- Deberá tener la posibilidad de registrar y consultar el estado de las incidencias.
- Deberá tener la posibilidad de cargar evidencias de las incidencias como fotografías o
documentos PDF.
- Por cada incidencia deberá poder registrar comentarios adicionales en caso de ser
necesarios, de igual manera visualizar los comentarios realizados por el enlace.
- Realizar el pago solicitado por el enlace.

## Tecnologías implementadas

A continuación una lista de las tencologías implementadas para el desarrollo de esta aplicación.

- [Spring](https://spring.io/)
  - [Thymeleaf](https://www.thymeleaf.org/)
  - [MySQL8](https://www.mysql.com/)
  - [Lombok](https://projectlombok.org/)
  - [Spring security](https://spring.io/projects/spring-security)
  - [Spring data JPA](https://spring.io/projects/spring-data-jpa)
- [Daysi UI](https://daisyui.com/)
- [Simple Notify](https://www.cssscript.com/toast-simple-notify/)

## Contribuidores

- Blass Zamudio Andrea
- Carrión Barcelas Keyla
- Adame Najera Raúl Genaro
- Rodriguéz Pavón José Manuel
- Vasquez Martinez Jair David
