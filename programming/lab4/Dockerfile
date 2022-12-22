FROM openjdk:17

RUN microdnf install socat

RUN mkdir /lab4
COPY ./target/lab4.jar /lab4
COPY ./run.sh /lab4

RUN chmod 555 /lab4/run.sh
RUN chmod 555 /lab4/lab4.jar

CMD ["socat", "TCP-LISTEN:13370,reuseaddr,fork", "EXEC:/lab4/run.sh"]
