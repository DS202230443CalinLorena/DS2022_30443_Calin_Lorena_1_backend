FROM maven:latest AS builder

COPY ./src/ /root/src
COPY ./pom.xml /root/
WORKDIR /root
RUN mvn package -Dmaven.test.skip=true
RUN java -Djarmode=layertools -jar /root/target/assignment1-backend-0.0.1-SNAPSHOT.jar list
RUN java -Djarmode=layertools -jar /root/target/assignment1-backend-0.0.1-SNAPSHOT.jar extract
RUN ls -l /root

FROM amazoncorretto:11

COPY --from=builder /root/dependencies/ ./
COPY --from=builder /root/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /root/spring-boot-loader/ ./
COPY --from=builder /root/application/ ./
#EXPOSE 3001
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]