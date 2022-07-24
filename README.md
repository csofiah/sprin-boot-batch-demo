# springbootInicial
Demo with SprinBoot batch where you save the register from a file called sample-data.csv
to database in memory hsqldb. 
With @EnableScheduling in the main Class you can run automatically each time, it depends on the schedule

# JobListener
Permite indicar que pasos va realizar cuando cambie el estado del job

# Build an executable JAR
If you use Gradle, you can run the application by using ./gradlew bootRun. Alternatively, you can build the JAR file by using ./gradlew build and then run the JAR file, as follows:

java -jar build/libs/gs-batch-processing-0.1.0.jar
If you use Maven, you can run the application by using ./mvnw spring-boot:run. Alternatively, you can build the JAR file with ./mvnw clean package and then run the JAR file, as follows:

java -jar target/gs-batch-processing-0.1.0.jar