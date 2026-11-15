# Pocket Edition WebSocket Server API

[English](README.md) · [简体中文](README.zh-CN.md)

这个 fork 面向 Android 集成，保留 API 与服务器核心；同时包含可选的桌面 Swing source set，只有传入 `-Ppews.desktop=true` 时才会编译。

这个 API 用于通过 WebSocket 与 C++ 版本的 Minecraft 通信，包括 Pocket Edition、Windows 10 Edition 和 Education Edition。它不是完整的多人服务器，也不具备多人服务器的全部能力。

Minecraft 会通过 WebSocket 与外部程序（例如教育版的 Classroom Mode App）和开发者工具通信。要连接到一个 WebSocket 服务器，玩家需要在游戏内聊天栏执行 `/connect` 或 `/wsserver` 命令。连接建立后，服务器可以接收游戏上报的遥测事件，并向游戏发送斜杠命令（例如 `/kill`）。完整的事件列表可以在这里找到：https://gist.github.com/jocopa3/5f718f4198f1ea91a37e3a9da468675c

Minecraft 内部通过 WebSocket 发送 JSON 消息与服务器通信。每种消息的基础 JSON 结构可以在这里找到：https://gist.github.com/jocopa3/54b42fb6361952997c4a6e38945e306f

## 构建

这是一个 Gradle Kotlin/JVM 项目；依赖由 Gradle 管理，可以直接导入 IntelliJ IDEA。

* [Java-WebSocket](https://github.com/TooTallNate/Java-WebSocket) `1.6.0`
* [Gson](https://github.com/google/gson) `2.15.0`

依赖版本统一由 `gradle/libs.versions.toml` 管理。

```bash
./gradlew build
```

可选的桌面 Swing 产物通过下面的命令构建：

```bash
./gradlew -Ppews.desktop=true desktopJar
```

`lib/` 下的本地 jar 已移除。

---

[![](https://img.shields.io/badge/powered_by-dsh-4D6BFE?style=flat-square&logo=deepseek&logoColor=white)](https://github.com/deepseek-ai/deepseek-harness)
