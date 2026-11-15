plugins {
    java
    kotlin("jvm")
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

provider {
}.let {}

sourceSets {
    main {
        java.srcDirs("src")
        kotlin.srcDirs("src")
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
    }
}

dependencies {
    implementation(libs.gson)
    implementation(libs.java.websocket)
}

tasks.jar {
    manifest {
        attributes["Implementation-Version"] = project.version
    }
}

// 桌面 Swing/GUI 只在显式开启时参与构建；Android 默认不碰 AWT。
val desktopEnabled = providers.gradleProperty("pews.desktop")
    .map { it.toBoolean() }
    .getOrElse(false)

if (desktopEnabled) {
    val desktop = sourceSets.create("desktop") {
        java.srcDir("desktop/src/java")
        kotlin.srcDir("desktop/src/java")
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
