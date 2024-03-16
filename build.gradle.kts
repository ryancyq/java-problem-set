plugins {
    id("java")
    application
}

group = "com.ryancyq"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("com.ryancyq.concurrency.Program")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}