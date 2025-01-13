
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(":app")
include(":code-detail")
include(":code-ui-common")
include(":compose-common")
include(":data:pkpass")
include(":data:barcode")
include(":data:core")
include(":data:common")
include(":data:types")
include(":items")
include(":pass-detail")
include(":pass-ui-common")
