plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

// tell gradle where to download from
repositories {
    mavenCentral()
}

// Configure JavaFX
javafx {
    version = "21.0.2"
    // We need 'controls' for Buttons/Menus, and 'swing' for SwingNode
    modules("javafx.controls", "javafx.swing")
}

// link to shared module
dependencies {
    implementation(project(":shared"))
}

// Tell Gradle where the main method is
application {
    mainClass.set("engine.Main")
}