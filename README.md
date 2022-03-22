# npuzzle-solver project

This project uses Quarkus Java Framework.

## Build instructions

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/npuzzle-solver-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native linux executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/npuzzle-solver-1.0.0-SNAPSHOT-runner`

