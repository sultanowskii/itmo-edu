# javadoc headache resolution

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