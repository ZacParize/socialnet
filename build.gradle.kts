plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.25"
    id("com.google.devtools.ksp") version "1.9.25-1.0.20"
    id("io.micronaut.application") version "4.5.4"
    id("com.gradleup.shadow") version "8.3.7"
    id("io.micronaut.aot") version "4.5.4"
    id("nu.studer.jooq") version "8.2"
}

repositories {
    mavenCentral()
}

group = "ru.socialnet"
version = project.property("myProject.version") as String

dependencies {
    runtimeOnly("org.yaml:snakeyaml")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    ksp("io.micronaut:micronaut-http-validation")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.problem:micronaut-problem-json")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.sql:micronaut-jooq")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jooq:jooq:3.19.7")
    implementation("org.postgresql:postgresql:42.7.1")
    jooqGenerator("org.postgresql:postgresql:42.7.1")
    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation("org.testcontainers:postgresql:1.19.1")
}

/*jooq {
    version = '3.19.7'
    configurations {
        main {
            generateSchemaSourceOnCompilation = true
            generationTool {
                jdbc {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/socialnet"
                    user = "postgres"
                    password = "changeme"
                }
                generator {
                    name = 'org.jooq.codegen.KotlinGenerator'
                    database {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        includes = "users" // только таблица users, можно убрать чтобы все
                    }
                    generate {
                        deprecated = false
                        kotlinSetterJvmNameAnnotationsOnIsPrefix = true
                        pojosAsKotlinDataClasses = true
                        fluentSetters = true
                    }
                    target {
                        packageName = "ru.socialnet.jooq"
                        directory = 'build/generated-src/jooq/main'
                    }
                    strategy.name = 'org.jooq.codegen.DefaultGeneratorStrategy'
                }
            }
        }
    }
}*/

//jooq {
//    version.set("3.19.7")
//    configurations {
//        create("main") {
//            generationTool {
//                jdbc {
//                    driver = "org.postgresql.Driver"
//                    url = "jdbc:postgresql://localhost:5432/socialnet"
//                    user = "postgres"
//                    password = "changeme"
//                }
//                generator {
//                    name = "org.jooq.codegen.KotlinGenerator"
//                    database {
//                        name = "org.jooq.meta.postgres.PostgresDatabase"
//                        inputSchema = "public"
//                        includes = "users" // только таблица users, можно убрать чтобы все
//                    }
//                    generate {
//                        isDaos = false
//                        isPojos = true
//                        isRecords = true
//                        isImmutablePojos = false
//                    }
//                    target {
//                        packageName = "social.jooq"
//                        directory = "src/main/generated"
//                    }
//                }
//            }
//        }
//    }
//}

application {
    mainClass = "ru.socialnet.App"
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.toVersion("21")
}