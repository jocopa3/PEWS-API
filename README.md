# Pocket Edition WebSocket Server API

[![](https://img.shields.io/badge/powered_by-dsh-4D6BFE?style=flat-square&logo=deepseek&logoColor=white)](https://github.com/deepseek-ai/deepseek-harness)

[English](README.md) · [简体中文](README.zh-CN.md)

This fork targets Android integration and keeps the API/server core. An optional desktop Swing source set is included, built only when `-Ppews.desktop=true` is passed.

This API is meant to communicate with the C++ versions of Minecraft, namely Pocket Edition, Windows 10 Edition, and Education Edition using WebSockets. This is not a full multiplayer server, and does not share the same capabilities as multiplayer servers do.

Minecraft uses WebSockets to communicate with external programs (such as the Classroom Mode App for Edu Edition) and developer tools. To connect to a WebSocket server, a user must use the `/connect` or `/wsserver` commands from the in-game chat screen. Once connected, the WebSocket server can listen for telemetry events from the game and send slash-commands (i.e. like `/kill`). A full list of all telemetry events the game tracks can be found here: https://gist.github.com/jocopa3/5f718f4198f1ea91a37e3a9da468675c

Internally, Minecraft sends JSON messages through the WebSocket to communicate to servers. A basic outline of the JSON structure of each message can be found here: https://gist.github.com/jocopa3/54b42fb6361952997c4a6e38945e306f

## Build

This is a Gradle Kotlin/JVM project; dependencies are managed by Gradle and can be imported directly into IntelliJ IDEA.

* [Java-WebSocket](https://github.com/TooTallNate/Java-WebSocket) `1.6.0`
* [Gson](https://github.com/google/gson) `2.15.0`

Dependency versions are managed in `gradle/libs.versions.toml`.

```bash
./gradlew build
```

The optional desktop Swing artifacts are built with:

```bash
./gradlew -Ppews.desktop=true desktopJar
```

The local jars under `lib/` have been removed.
