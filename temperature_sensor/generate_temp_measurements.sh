#!/bin/bash

generate_temperature() {
  local min=10
  local max=300
  local temp=$(echo "scale=2; $min + ($max-$min)*$RANDOM/32767" | bc)
  echo "$temp"
}

send_payload() {
  local name=$1
  local temperature=$(generate_temperature)
  local payload="{\"name\": \"$name\", \"unit\": \"celsius\", \"time\": \"$(date -Iseconds)\", \"value\": $temperature}"

  if [ -n "$RECEIVER_URL" ]; then
    curl -X POST -k -H "Content-Type: application/json" -d "$payload" "$RECEIVER_URL"
  else
    echo "$payload"
  fi
}

sleep_time=${SLEEP_TIME:-5}

while true
do
  send_payload "sensor1"
  send_payload "sensor2"
  sleep "$sleep_time"
done
