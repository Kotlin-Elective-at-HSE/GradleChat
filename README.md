[![Build](https://github.com/Kotlin-Elective-at-HSE/GradleChat/workflows/Build/badge.svg)](https://github.com/Kotlin-Elective-at-HSE/GradleChat/actions)

# GradleChat
Монорепа с чатом на вебсокетах.

За основу взяты два отдельных проекта: [chat-server](https://github.com/Kotlin-Elective-at-HSE/chat-server) и [chat-client-web](https://github.com/Kotlin-Elective-at-HSE/chat-client-web).

28-го февраля 2020 мы показывали:
- [x] Выделение разных частей компиляции в одном проекте &mdash; Gradle модули (папки `client-web` и `server`).
- [x] [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) для получения и декодирования JSON.

Также в репозитории:
- [x] Multiplatform module (для хранения констант и протокола для клиента и сервера) &mdash; папка `common`.
- [x] GitHub actions (для проверки компиляции и тестирования проекта на чистой машине).
- [x] GitHub actions шильдик (для красоты).

## Построение и запуск
Клиент строится с помощью `./gradlew :client-web:browserProductionWebpack`, и в папке `client-web/build/distributions` оказывается сгенерированный общий JS файл и все файлы ресурсов. Для запуска нужно открыть в браузере HTML файл.

Сервер строится с помощью `./gradlew :server:jar` и запускается с помощью `java -jar server/build/libs/server-1.0-SNAPSHOT.jar`.
