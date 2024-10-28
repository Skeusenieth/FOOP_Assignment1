import java.net.URL
import java.io.FileOutputStream


plugins {
    java
    jacoco
//    id("com.github.spotbugs") version "5.0.13"
}

group = "ecs658"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // JUnit Jupiter API and Engine for testing
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // Other dependencies
    // https://mvnrepository.com/artifact/com.github.mauricioaniche/ck
    implementation("com.github.mauricioaniche:ck:0.7.0")
    implementation("black.ninia:jep:4.2.0")
    implementation("com.google.code.gson:gson:2.8.9")
    // SpotBugs for static code analysis
//    spotbugs("com.github.spotbugs:spotbugs:4.7.3")
}


tasks.withType<JavaExec> {
    systemProperty("java.library.path", "/opt/miniconda3/lib/python3.12/site-packages/jep")
}

tasks.register<JavaExec>("ckMetrics") {
    group = "verification"
    description = "Run CK Metrics"

    // Ensure classes are compiled before running CK Metrics
    dependsOn(tasks.classes)

    // Set the classpath to include all dependencies
    classpath = sourceSets.main.get().runtimeClasspath

    // Set the main class to your CKMetricsRunner class
    mainClass.set("metrics.CKMetricsRunner")
}


val ckVersion = "0.7.0"



tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // Generate JaCoCo report after tests
}

jacoco {
    toolVersion = "0.8.10" // Use the latest JaCoCo version
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // Ensure tests run before generating the report
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

//tasks.withType<com.github.spotbugs.snom.SpotBugsTask>().configureEach {
//    reports {
//        xml.required.set(false)
//        html.required.set(true)
//        // Optional: Set a custom stylesheet for the HTML report
//        // html.stylesheet.set(resources.text.fromFile("spotbugs.css"))
//    }
//}
tasks.check {
    dependsOn(
        tasks.test,
//        tasks.ckMetrics,
        tasks.named("ckMetrics"), // Use named to reference ckMetrics properly

//        tasks.spotbugsMain
    )
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java")
        }
    }
    test {
        java {
            srcDirs("src/test/java")
        }
    }
}

