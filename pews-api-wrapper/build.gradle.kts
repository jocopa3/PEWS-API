plugins {
    `java-library`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

// PEWS-API fork 已移除桌面 GUI / AWT 依赖，
// 这里只把上游 src 同步到 build 目录并接入 Android 依赖。
val preparePewsUpstream = tasks.register<Sync>("preparePewsUpstream") {
    from("../src") {
    }
    into(layout.buildDirectory.dir("pews-upstream-src"))
}

sourceSets {
    main {
        java.srcDir(layout.buildDirectory.dir("pews-upstream-src"))
    }
}

tasks.named("compileJava") {
    dependsOn(preparePewsUpstream)
}

dependencies {
    implementation("org.java-websocket:Java-WebSocket:1.6.0")
    implementation("com.google.code.gson:gson:2.15.0")
}
