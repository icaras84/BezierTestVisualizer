plugins {
    id("java")
}

group = "com.icaras84"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.ejml:ejml-all:0.44.0") // EJML
}

tasks.test {
    useJUnitPlatform()
}