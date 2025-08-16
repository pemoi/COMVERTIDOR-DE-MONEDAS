# Conversor de Monedas en Java (Consola)

> Proyecto creado para el desafío de **Backend con Java** — Alura Latam  
> Autor: **Emmanuel Peña** · Instructora de referencia: **Génesys Rondón**

![Java](https://img.shields.io/badge/Java-17-blue) ![Maven](https://img.shields.io/badge/Maven-3.8+-orange) ![License](https://img.shields.io/badge/License-MIT-green)

Proyecto completo de consola en **Java 17 + Maven** que consume una **API de tipos de cambio** y
permite realizar conversiones de monedas a través de un **menú interactivo**.

---

## ✨ Funcionalidades
- Menú con **6 conversiones predefinidas** y **modo libre** (elige cualquier par de monedas).
- Consumo de API con **`HttpClient`** nativo (sin librerías externas de HTTP).
- Dos proveedores soportados automáticamente:
  1. **ExchangeRate-API (v6)** — requiere API Key (recomendado).
  2. **exchangerate.host** — gratis y **sin API Key** (fallback).
- Validación de entradas y manejo de errores de red/API.
- Registro de **historial** en `conversions.csv`.
- Código organizado en capas: `ui`, `service`, `model`, `util`.
- **Pruebas unitarias** mínimas con JUnit 5 (extensible).

## 🗂️ Estructura
```text
currency-converter/
├─ pom.xml
├─ README.md
├─ .gitignore
├─ .env.example
└─ src
   ├─ main
   │  ├─ java/com/alura/conversor
   │  │  ├─ App.java                      # Entry point
   │  │  ├─ ConsoleUI.java                # Menú y entrada/salida por consola
   │  │  ├─ CurrencyConverter.java        # Orquestación y guardado de historial
   │  │  ├─ RateService.java              # Interfaz de proveedor de tasas
   │  │  ├─ ExchangeRateApiService.java   # Implementación ExchangeRate-API (v6)
   │  │  ├─ ExchangeRateHostService.java  # Implementación exchangerate.host
   │  │  ├─ util/Env.java                 # Loader de variables de entorno y .env
   │  │  └─ model/ConversionResult.java   # DTO del resultado
   │  └─ resources/
   └─ test
      └─ java/com/alura/conversor/AppTest.java
```

## 🔐 Configurar API Key (opcional pero recomendado)
- Crea una cuenta y obtén tu API Key de **ExchangeRate-API (v6)**.
- Configura la variable de entorno `EXCHANGE_RATE_API_KEY` o edita un archivo `.env` en la raíz:
  ```env
  EXCHANGE_RATE_API_KEY=tu_api_key_aqui
  ```
- Si no defines la clave, el proyecto usará **exchangerate.host** automáticamente.


## 🌐 Proveedor principal: Exchange Rate API (recomendado)
Usamos **Exchange Rate API** por sus tasas de cambio en **tiempo real**, lo que proporciona información **precisa y actualizada** para las conversiones. Es **gratuita** y **muy fácil de usar**, garantizando un acceso **confiable y eficiente** a los datos esenciales del proyecto.

### Obtención de la clave (API Key)
1. Ingresa tu correo electrónico y regístrate (como se muestra en la guía del curso).
2. Recibirás tu **API Key** en el correo.
3. Configúrala como variable de entorno `EXCHANGE_RATE_API_KEY` o en el archivo `.env` en la raíz del proyecto.

> En este proyecto ya dejamos un `.env` de ejemplo **preconfigurado** para que puedas ejecutar de inmediato. **Cámbialo por tu propia clave** si vas a publicar el código en GitHub.

**Registro sugerido:** accede al enlace del curso y realiza tu registro:  
*(ver indicaciones del curso de Alura para el paso a paso de obtención de la API Key)*

## ⚙️ Requisitos
- **Java 17+**
- **Maven 3.8+**
- Conexión a Internet

## 🛠️ Instalación y ejecución
```bash
# 1) Compilar
mvn clean package

# 2) Ejecutar (modo desarrollo)
mvn exec:java

# 3) Ejecutar el JAR (modo distribuible)
java -jar target/currency-converter-1.0.0.jar
```

> **Tip:** en macOS/Linux puedes exportar la API key en la terminal actual:
```bash
export EXCHANGE_RATE_API_KEY="tu_api_key"
```
En Windows (PowerShell):
```powershell
setx EXCHANGE_RATE_API_KEY "tu_api_key"
```

## 🧭 Uso rápido
- Elige una opción del menú (1–6) para conversiones comunes o **7** para **conversión libre**.
- Ingresa el **monto**.
- Recibirás el resultado y la tasa aplicada. Además, quedará registro en `conversions.csv`.

### Ejemplo (salida esperada)
```
===========================================
  Conversor de Monedas - Java + API
===========================================

Menú de opciones:
1) USD -> ARS
2) ARS -> USD
3) BRL -> USD
4) USD -> BRL
5) COP -> USD
6) USD -> COP
7) Conversión libre (elige monedas)
0) Salir
Elige una opción: 1
Ingresa el monto a convertir (USD): 25
El valor de 25 USD corresponde a 20.293,75 ARS (tasa: 811.750000)
```

## 🧪 Pruebas
```bash
mvn test
```

## 🧰 Buenas prácticas incluidas
- Separación de responsabilidades (UI, servicio, dominio).
- Interfaz `RateService` para **injección de dependencias** y **testabilidad**.
- Manejo explícito de errores HTTP y de parseo JSON.
- Archivo `.env.example` para facilitar onboarding.

## 🧱 Roadmap (ideas para extender)
- Historial en **SQLite** o **H2** con JDBC.
- CLI con **picocli** y banderas (`--from`, `--to`, `--amount`).
- Interfaz gráfica (JavaFX/Swing).
- Tests de integración con **WireMock**.

## 🤝 Contribuir
Consulta **CONTRIBUTING.md** para pautas de estilo, issues y PRs.

## 📝 Licencia
Este proyecto se distribuye bajo licencia **MIT**. Consulta `LICENSE`.
