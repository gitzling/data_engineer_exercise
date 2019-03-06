FROM puckel/docker-airflow:1.10.2
#MAINTAINER itzg
# Install OpenJDK-8
USER root

RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get clean; exit 0

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/bin/
ENV PATH "${JAVA_HOME}:${PATH}"
RUN export JAVA_HOME

USER airflow