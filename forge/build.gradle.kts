architectury {
    forge()
}

loom {
    forge {
        mixinConfig("heracles-common.mixins.json")
    }
}

dependencies {
    val minecraftVersion: String by project
    val forgeVersion: String by project

    forge(group = "net.minecraftforge", name = "forge", version = "$minecraftVersion-$forgeVersion")
}
