# Temptasty - temperature measurements

This repository includes a demo implementation of a fictional IOT scenario,
where sensors take temperature readings and send them to a backend.

Used technologies are:

- influxdb 2.0
- quarkus 3.0.1
- angular 15.2.7
- nginx 1.23.3

# Component overview

![component model](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.github.com/d0ms0n/Temptasty/master/components.iuml)

## Sensor

Then mock sensor is an Alpine Linux docker image with a bash script, which emits randomized numbers between 10 and 300.
This numbers, embedded in a json format, will be sent to a receiver url, or otherwise prints the json to stdout.

You can configure the script via environment variables. Possibilities are:

- RECEIVER_URL=https://temptasty.org:8081/measurements
- SLEEP_TIME=5

The format looks like:

```json
{
    "name": "sensor1",
    "unit": "celsius",
    "time": "2023-05-11T14:34:17Z",
    "value": 66.72
}
```

## Frontend
The frontend component is an angular app. The chart libraries are chart.js and ng2-charts. Learn more about angular [here](https://angular.io/). 

```shell script
# lint the code 
ng lint --fix

# build to dist folder
ng build

# develop with local dev server
ng serve
```


To show the frontend, go to https://temptasty.org:8081/measurement-viewer

## Backend
The backend component is a quarkus application. Lern more about quarkus [here](https://quarkus.io/).

```shell script
# run in dev mode with live coding
./mvnw compile quarkus:dev

# build to target folder
./mvnw package
```

```shell script
# get all measurements
curl -i -X GET 'https://temptasty.org:8081/measurements'

# get mean within a range of the last hour (h) day (d) month (m)
# filter with query parameter "sensor"
curl -i -X GET 'https://temptasty.org:8081/measurements/mean?sensor=sensor1&range=h'
```

To view the openapi description go to http://localhost:8080/q/swagger-ui/

## Database
The database is an influxdb docker image. 
Learn more about influx [here](https://docs.influxdata.com/influxdb/v2.7/).

To configure the database, go to http://localhost:8086/

# Running the application

You must provide an .env file in root folder with following content

```shell script
# InfluxDB Configuration
INFLUXDB_INIT_USERNAME=admin
INFLUXDB_INIT_PASSWORD=password
INFLUXDB_INIT_ADMIN_TOKEN=mytoken
```

Also modify your hosts file to support temptasty.org

```h
127.0.0.1 temptasty.org
```

You also have to create a ssl certificate with private key, store it under reverse-proxy/certs
and put them to the nginx configuration.

To build and run the application, be sure to have npx, docker and docker-compose installed.
From root folder, call

```shell script
./run.sh
```

# Todos

- [ ] backend - ssl cert
- [ ] backend - time validation
- [ ] backend - own model for mean values
- [ ] architecture - split in different docker networks
- [ ] sensor - try mqtt for backend communication
- [ ] backend - support mqtt
- [ ] backend - spotbugs, pitest
- [ ] backend - native executable
- [ ] frontend - use docker build image
- [ ] frontend - tests
- [ ] frontend - enhance measurement-viewer with mean and filters
- [ ] sensor - dynamic sensor count
- [ ] frontend - support dynamic sensor count
