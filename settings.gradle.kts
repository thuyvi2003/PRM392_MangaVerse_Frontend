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
        maven { url = uri("https://readium.github.io/android-toolkit/maven/") }

    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://readium.github.io/android-toolkit/maven/") }
        maven { url = uri("https://jitpack.io") }    }
}

rootProject.name = "MangaVerse"
include(":app")
