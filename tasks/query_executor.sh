#!/usr/bin/env bash

sleep 10s
java -cp "$EXERCISE_HOME/eclipse_workspace_de/DataEngineerExerciseProject/target/DataEngineerExerciseProject-0.0.1-SNAPSHOT.jar:$EXERCISE_HOME/eclipse_workspace_de/DataEngineerExerciseProject/lib/*" com.gera.QueryExecutor -Dfrom=2019-01-13
