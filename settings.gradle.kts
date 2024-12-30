
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
include(":compose-common")
include(":data:pkpass")
include(":data:barcode")
include(":data:core")
include(":data:common")
include(":passdetail")
include(":pass-ui-common")
