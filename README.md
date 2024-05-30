# T'PAGO

## Visión

**Versión 3.0**

### Posicionamiento

#### El Problema

1. Falta de una interfaz para que el empleado sea admitido por el empleador.
2. Ausencia de medios de pago más rápidos.
3. Límite del monto operacional de S/500 por día.

#### Afecta

1. La verificación eficiente del pago realizado.
2. El tiempo de la operación de pago.
3. A los usuarios que requieren realizar operaciones de pago con montos mayores a S/500 por día.

#### El Impacto

1. Nuevos modos de estafa por la incertidumbre generada.
2. Más tiempo de espera al realizar operaciones.
3. Frustración de los usuarios al no cumplir con sus objetivos.

#### Solución Satisfactoria

1. Implementar una opción para que el empleador pueda agregar a sus empleados como invitados y darles permisos de solo visualización.
2. Implementar el método de pago automático mediante tecnología sin contacto: NFC.
3. Ampliar el límite del monto de operación a S/.2000.

### Posición del Producto

**Para**: Usuarios comunes y emprendedores.  
**Quién**: Busca simplificar sus transacciones financieras y mejorar la gestión de sus finanzas comerciales.  
**Producto**: Un monedero virtual que incluye funciones para negocios.  
**Que**: Permite a los usuarios gestionar sus finanzas personales y comerciales de manera eficiente y segura.  
**A diferencia de**: Otros monederos virtuales que no ofrecen funciones específicas para negocios.

### Stakeholders y Descripciones de Usuarios

#### Stakeholders

- **Administrador**: Encargado de la administración y mantenimiento del sistema.
- **Empleador**: Personas que usan el sistema T'PAGO para gestionar transacciones financieras de su negocio.
- **Empleado**: Empleados de empresas que utilizan T'PAGO como parte de sus funciones laborales.

#### Usuarios

- **Usuario**: Realizan transacciones, recargan saldo, entre otras acciones.
- **Administrador**: Responsable de la administración y mantenimiento del sistema, gestión de reportes, seguridad, y estabilidad operativa.

---

## Casos de Uso

### Realizar Operación

**Versión 4.0**

- **Propósito**: Especificar el diseño del caso de uso Realizar Operación.
- **Alcance**: Caso de uso Realizar Operación del sistema T'PAGO.

**Flujos:**
- Flujo normal: Realizar operación.
- Flujo alternativo 1: Fondos insuficientes.
- Flujo alternativo 2: Seleccionar medio de pago NFC.
- Flujo alternativo 3: Seleccionar medio de pago QR.
- Flujo alternativo 4: Límite de monto superado.

### Recargar Saldo

**Versión 4.0**

- **Propósito**: Especificar el diseño del caso de uso Recargar Saldo.
- **Alcance**: Caso de uso Recargar Saldo del sistema T'PAGO.

**Flujos:**
- Flujo normal: Recargar saldo.
- Flujo alternativo 1: Agregar tarjeta.
- Flujo alternativo 2: Elegir pago efectivo.

### Gestión de Tarjetas

**Versión 4.0**

- **Propósito**: Especificar el diseño del caso de uso Gestión de Tarjetas.
- **Alcance**: Caso de uso Gestión de Tarjetas del sistema T'PAGO.

**Flujos:**
- Flujo normal: Agregar tarjeta.
- Flujo alternativo 1: Excede número máximo de tarjetas.
- Flujo alternativo 2: Eliminar tarjeta.

### Gestión de Accesos

**Versión 4.0**

- **Propósito**: Especificar el diseño del caso de uso Gestión de Accesos.
- **Alcance**: Caso de uso Gestión de Accesos del sistema T'PAGO.

**Flujos:**
- Flujo normal: Otorgar acceso.
- Flujo alternativo 1: Otorgar acceso ingresando número.
- Flujo alternativo 2: Eliminar acceso.

### Registrar Usuario

**Versión 4.0**

- **Propósito**: Especificar el diseño del caso de uso Registrar Usuario.
- **Alcance**: Caso de uso Registrar Usuario del sistema T'PAGO.

**Flujos:**
- Flujo normal: Registrar usuario.
- Flujo alternativo: Verificación de datos incorrecta al escanear el DNI.

### Ver Movimientos

**Versión 4.0**

- **Propósito**: Especificar el diseño del caso de uso Ver Movimientos.
- **Alcance**: Caso de uso Ver Movimientos del sistema T'PAGO.

### Mostrar Saldo

**Versión 4.0**

- **Propósito**: Especificar el diseño del caso de uso Mostrar Saldo.
- **Alcance**: Caso de uso Mostrar Saldo del sistema T'PAGO.

### Generar Reporte

**Versión 4.0**

- **Propósito**: Especificar el diseño del caso de uso Generar Reporte.
- **Alcance**: Caso de uso Generar Reporte del sistema T'PAGO.

---

## Requisitos Especiales

- **Estándares y Requisitos de Plataforma**:
  - Cumplimiento con PCI-DSS.
  - Operar en versiones posteriores a Android 10.
  - Integración con tecnología NFC.

- **Requisitos de Rendimiento**:
  - Tiempo de respuesta de 400 ms.
  - Capacidad para manejar 200 transacciones simultáneas.

- **Requisitos de Calidad**:
  - Robustez y tolerancia a fallos.
  - Usabilidad intuitiva.

- **Restricciones de Diseño y Dependencias Externas**:
  - Cumplimiento con regulaciones gubernamentales.
  - Integración con sistemas bancarios y de pago.
  - Dependencia de la infraestructura de red y servicios de terceros.

---

## Documentación

- Manual de usuario.
- Documentación técnica.
- Instrucciones de instalación.

---

## Autores

- Carlos
- Leandro
- Yony
- Erika

---

## Historial de Versiones

- 15/04/2024: Versión 1.0 - Diagramas de secuencia, modelo de datos y mockups.
- 04/05/2024: Versión 2.0 - Actualización de diagramas de secuencia y mockups.
- 07/05/2024: Versión 3.0 - Revisión del modelo de datos.
- 30/05/2024: Versión 4.0 - Última revisión del modelo de datos y diagramas de secuencia.

---
