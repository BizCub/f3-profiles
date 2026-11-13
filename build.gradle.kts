multiloader {
    setMREnvironment(mrEnvs.clientOnly)
    setCFEnvironment(cfEnvs.client)

    configureLoaders {
        if (isFabric) {
            addDependency(
                dependency = "net.fabricmc:fabric-loader:${getDep("fabric")}"
            )
            addDependency(
                dependency = "net.fabricmc.fabric-api:fabric-api:${getDep("fabric-api")}",
                isPublishDepEnabled = true,
                isPublishDepRequired = true
            )
        }
    }
}
