# PEWS-API Gradle 包装模块

本目录是 PEWS-API 的 Android 构建包装模块。
Android / Java-WebSocket 1.6 的兼容改动已回到这份 fork，wrapper 不再重复维护。

这个模块只负责把源码接入 Android 构建：

- `preparePewsUpstream` 把 `../src` 同步到 `build/pews-upstream-src`
- 排除桌面 GUI / AWT：`ServerGUI`、`TextToBlockListener`、`ChatFormatCode`
- `src/main/java` 提供 Android 兼容实现：
  - `mcpews.WSEncrypt`：适配 Java-WebSocket 1.6
  - `mcpews.MCSocketServer`：适配新 `Draft_6455` / `onStart`
  - `mcpews.util.ChatFormatCode`：去掉 `java.awt.Color`，保留字符串常量

以后同步 fork 时，把 `codesenlin/PEWS-API` 的 `src/` 覆盖到 `PEWS-API/src/`，
再跑一遍 `:pews-api-wrapper:compileJava` 和 `:app:assembleDebug` 验证。

源码可直接进入 Android 构建，wrapper 只负责 Gradle 依赖与同步。

这个模块可以单独被 cadroid 的 Gradle 构建引用。
