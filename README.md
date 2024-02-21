# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## Server Design

https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWOZVYSnfoccKQCLAwwAIIgQKAM4TMAE0HAARsAkoYMhZkwBzKBACu2GAGI0wKgE8YAJRRakEsFEFIIaYwHcAFkjAdEqUgBaAD4WakoALhgAbQAFAHkyABUAXRgAej0VKAAdNABvLMpTAFsUABoYXCl3aBlKlBLgJAQAX0wKcNgQsLZxKKhbe18oAAoiqFKKquUJWqh6mEbmhABKDtZ2GB6BIVFxKSitFDAAVWzx7Kn13ZExSQlt0PUosgBRABk3uCSYCamYAAzXQlP7ZTC3fYPbY9Tp9FBRNB6BAIDbULY7eRQw4wECDQQoc6US7FYBlSrVOZ1G5Y+5SJ5qBRRACSADl3lZfv8ydNKfNFssWjA2Ul4mDaHCMaFIXSJFE8SgCcI9GBPCTJjyaXtZQyXmyOVyrjzKsAVZ4khAANboYWs0UwU2qiG0g6PQKwzb9B1mi3WtBo+Ew0JwyLe1W+9AByhB+DIdBgKIAJgADMm8vlHeareh2ugZJodPpDEZoLxjjAPhA7G4jF4fH440Fg6xQ3FEqkMiopC40OnueSZjU6u0Q1AQpL+ggq0g0OqphTZvz1hOUCEZa6Isczhd+7zF9TndrXSEXu8vj9xRqykCQZfNCu1y6HhEFUqzXPNYe7sfgnr2W9OUvecwyzP1bXtTMA3YAAed112fTMI39UdYMCFcIkQ7N-X8eNYNHCIUzTApML9XM0HzQsDGMHQUBtSstH0Zha28XxMBwptelbPhPjeJI3jSdIuwkHs8hIyNR3HT0ESnBiVVGMS0GXKTHyPZ8qGAZA5F8ZVVXkn0sK1b8HhPJkz2+X4FJgAAxKx4gAWRAvIoPEFSjMOGQUAQE4UB0tUFMM7EJBMiIrDeOz4gANTeECkOs2yHMgldUPgw4kRRKMoFQ9C0tRdiwCylsoAI1NMDzAtdCooxBhkCthhgABxHlHmY+s2MbZgPS6KJonq3iBK0HlRP0v0Msk9FJ2GRqygkPTwwM5zV2CFK5XUzS310-yv0C4KzIvSybPskCFtcwLNxOKbJFm0D0AC2Udp4i8BumhbkqfQ4KHyuBPAeC7ggygrxoRD7oK+n6eT+vKAZoIrCPTYHQakX6yPzbQKuLbA9CgbAvPgfFfAankPBYhsAg65supiBJkn6wbTGG9B0yelBWR5EdCrG+EXzxlALqupDKiZlmyiUwGTtlCJVqQLSfPfTblvu88LPptwDoSs1jqWt65S3XnBc-eXf1Mh7fiZuLDqZ6QkrgrWIhyjXlq5xVfAu5k+FGPXha2u7DYifUAJNnkhemJnXfAsUmZe63VMOEO+H+910NjtrSahyJYYKWPkfKotjHMTyp3cGAACkIBnAnryMBQEFAS1k-jBl8Mp04O3SJmhrmv10zyuAICnKABZ5V22a6Dn2AiAArUu0F5hTKm73voAHspXZF+ExY3SXpd8vn5oN09jZirCzbVp0H016PtfOnkd79JeUBXr2f33xWYFj4-X6a+2bcnmcZ+Vuf2o9z7nfB+e8mR+0ArPWMpMgHQDDh-ZeccraBAdnbFCCcpK22RLldqqcYYlTKqjHORgdDAEsIgRUsBgDYGxoQZwrgiatTyg3Qq3VuJfD4gJdQo1gjoRAF5PAoxV7sHXs+fhlChGP2Mj7dhvFooTAkHfRRh8-SWykq9C+WD0roLQpgu2kMMFdTtmVIAA

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
