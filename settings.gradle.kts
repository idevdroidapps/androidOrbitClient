import java.util.Properties

var gitHubUser = ""
var gitHubKey = ""
val securePropertiesPath = "${rootProject.projectDir}/secure.properties"

fun readProperties(propertiesFile: File) = Properties().apply {
    propertiesFile.inputStream().use { fis ->
        load(fis)
    }
}

if(file(securePropertiesPath).exists()){
    val properties = readProperties(file(securePropertiesPath))
    gitHubUser = properties.getProperty("github_user")
    gitHubKey = properties.getProperty("github_key")
}

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/idevdroidapps/OrbitSDK")
            credentials {
                username = gitHubUser
                password = gitHubKey
            }
        }
    }
}

rootProject.name = "Orbit Client"
include(":app")
