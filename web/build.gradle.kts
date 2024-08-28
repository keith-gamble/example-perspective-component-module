import com.github.gradle.node.npm.task.NpmTask

plugins {
    java
    id("com.github.node-gradle.node") version("3.2.1")
}

val projectOutput: String by extra("$buildDir/generated-resources/")

node {
    version.set("22.6.0")
    npmVersion.set("10.8.2")
    download.set(true)
    nodeProjectDir.set(file("$projectDir"))
}

val installDependencies by tasks.registering(NpmTask::class) {
    args.set(listOf("install"))
}

val webpack by tasks.registering(NpmTask::class) {
    args.set(listOf("run", "build"))

    dependsOn(installDependencies)
    
    inputs.files(project.fileTree(".").matching {
        exclude("**/node_modules/**", "**/dist/**", "**/.awcache/**", "**/yarn-error.log")
    }.toList())

    outputs.files(fileTree(projectOutput))
}


tasks {
	processResources {
		dependsOn(webpack)	
	}

	clean {
		delete("dist")
	}
}





val deepClean by tasks.registering {
    doLast {
        delete(file(".gradle"))
        delete(file("node_modules"))
    }

    dependsOn(project.tasks.named("clean"))
}

project(":gateway")?.tasks?.named("processResources")?.configure {
    dependsOn(webpack)
}

sourceSets {
    main {
        output.dir(projectOutput, "builtBy" to listOf(webpack))
    }
}
