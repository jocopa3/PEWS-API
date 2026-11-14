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
