plugins {
    kotlin("jvm") version "2.0.20"
    application
}

application {
    mainClass.set("MainKt")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

group = "cli"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.xenomachina:kotlin-argparser:2.0.7")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(19)
}