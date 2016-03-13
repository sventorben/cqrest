## Maven Artifacts

To integrate cqREST in your projects, you can use a maven dependency as follows:

```
    <dependency>
        <groupId>de.sven-torben.cqrs</groupId>
        <artifactId>cqrest-core</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
```

You will also need to integrate the Sonatype Maven repositories. 

```
    <repository>
        <id>sonatype-nexus-snapshots</id>
        <name>Sonatype Nexus Snapshots</name>
        <url>https://oss.sonatype.org/content/repositories/snapshots</url>
        <releases>
            <enabled>false</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository
    
    <repository>
        <id>sonatype-nexus-releases</id>
        <name>Sonatype Nexus Snapshots</name>
        <url>https://oss.sonatype.org/content/repositories/releases</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
```