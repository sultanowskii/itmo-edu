# how to deal with java frustration

We all know java and the level of frustration using it. So here are some tips to have less pain preparing YET ANOTHER JAVA LAB

Please note that I'm using `maven`, so, well, all project structure related tips are only appliable for maven. Ye.

### how not to sweat to death trying to generate javadoc

1. Install `maven-javadoc-plugin` by placing it into your **pom.xml** plugin list (try to change the version to the newer):
    ```xml
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-javadoc-plugin</artifactId>
        <version>2.9</version>
        <configuration>
            <source>8</source>
            <javadocExecutable>${java.home}/bin/javadoc</javadocExecutable>
        </configuration>
    </plugin>
    ```

2. Add `JAVA_HOME` env variable
    ```bash
    export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
    ```

3. Run the plugin
    ```bash
    $ mvn javadoc:javadoc 
    ```

4. The resulting docs website will be placed in **target/site/**

### several build targets

1. Open up `pom.xml`

2. There, insert `<profiles>` - that's a list of profiles

3. Add 1st `<profile>`

4. Specify its `<id>` (aka name)

5. Now insert any change you have to make (like adding `<dependency>`) - just like you would do inside `<project>`

6. Copy+paste and edit as many profiles as you want. So in the end you may end up somewhat similar to this:

   ```xml
     <project>
      ...
      <profiles>
        <profile>
          <id>browser</id>
          <url>https://chrome.sucks.ass.xyz</url>
        </profile>
        <profile>
          <id>tanki</id>
          <build>
            <plugins>
              <plugin>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.0.2</version>
                <configuration>
                  <finalName>client</finalName>
                  <archive>
                    <manifest>
                      <mainClass>tanki.online.Game</mainClass>
                    </manifest>
                  </archive>
                </configuration>
              </plugin>
            </plugins>
          </build>
        </profile>
      </profiles>
    ...
    </project>
   ```

7. To build a specific profile, say, `tanki`, run

   ```bash
   mvn package -Ptanki
   ```
   
### Pro tip for true java fanboys
jump out of the window
