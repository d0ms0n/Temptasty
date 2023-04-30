#!/bin/bash

if [ -z "$1" ]; then
  sleep_time=5
else
  sleep_time=$1
fi

send_json() {
  url=$1
  json=$2
  curl -X POST -H "Content-Type: application/json" -d "$json" "$url"
}

while true; do
  temperature=$(awk -v min=-15 -v max=45 'BEGIN{srand(); printf "%.1f", min+rand()*(max-min)}')
  timestamp=$(date -Iseconds)
  json="{\"value\": $temperature, \"name\": \"sensor1\", \"unit\": \"celsius\", \"timestamp\": \"$timestamp\"}"
  echo $json
  send_json "http://localhost:8080/samples" "$json"
  sleep $sleep_time 
done

