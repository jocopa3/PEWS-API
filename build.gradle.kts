plugins {
    java
}

group = "mcpews"
version = "1.0.0"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

sourceSets {
    main {
        java.srcDirs("src")
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.15.0")
    implementation("org.java-websocket:Java-WebSocket:1.6.0")
}

// 桌面 Swing/GUI 只在显式开启时参与构建；Android 默认不碰 AWT。
val desktopEnabled = providers.gradleProperty("pews.desktop")
    .map { it.toBoolean() }
    .getOrElse(false)

if (desktopEnabled) {
    val desktop = sourceSets.create("desktop") {
        java.srcDir("desktop/src/java")
        compileClasspath += sourceSets.main.get().output
        compileClasspath += configurations.runtimeClasspath.get()
        runtimeClasspath += sourceSets.main.get().output
        runtimeClasspath += configurations.runtimeClasspath.get()
    }

    tasks.register<Jar>("desktopJar") {
        group = "build"
        description = "Build the optional desktop Swing GUI jar"
        dependsOn(tasks.named("desktopClasses"))
        from(desktop.output)
        archiveClassifier.set("desktop")
        manifest {
            attributes["Main-Class"] = "mcpews.listeners.ServerGUI"
        }
    }
}
