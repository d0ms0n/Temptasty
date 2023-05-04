# Temptasty - temperature samples

This docker compose file starts influxdb, a mock temperature sensor and a quarkus application as receiver

## Running the application

```
./mvnw package
docker-compose up --build -d
```

```
PROJECT_NAME=influxdb2

# InfluxDB Configuration
INFLUXDB_HTTP_PORT=8086
INFLUXDB_INIT_MODE=setup
INFLUXDB_INIT_USERNAME=myuser
INFLUXDB_INIT_PASSWORD=mypassword
INFLUXDB_INIT_ORG=myorg
INFLUXDB_INIT_BUCKET=mybucket
INFLUXDB_INIT_ADMIN_TOKEN=mytoken
INFLUXD_LOG_LEVEL=warn

# Timezone
TZ=Europe/Berlin
```
