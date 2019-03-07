#!/usr/bin/env bash
curl --url 'smtps://smtp.gmail.com:465' --ssl-reqd \
  --mail-from 'gera.witzling@gmail.com' --mail-rcpt 'gera.witzling@gmail.com' \
  --upload-file $EXERCISE_HOME/outputs/query_result.csv --user 'gwitzg@gmail.com:gw041076' --insecure
