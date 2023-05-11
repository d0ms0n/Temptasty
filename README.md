# Temptasty - temperature samples

This repository includes a demo implementation of a fictional IOT scenario,
where sensors take temperature readings and send them to a backend.

Used technologies are:

- influxdb 2.0
- quarkus 3.0.1
- angular 15.2.7
- nginx 1.23.3

# Component overview
![component model](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.https://github.com/d0ms0n/Temptasty/master/components.iuml)
## Frontend

To show the frontend, go to https://temptasty.org:8081/sample-viewer

## Backend

To get all samples, call

```
curl -i -X GET 'https://temptasty.org:8081/samples'
```

To get the mean within a range of the last hour, last day, or last month, call

```
curl -i -X GET 'https://temptasty.org:8081/samples/mean?sensor=sensor1&range=h'
```

To view the openapi description go to http://localhost:8080/q/swagger-ui/

## Database

To configure the database, go to http://localhost:8086/

# Running the application

You must provide an .env file in root folder with following content

```
# InfluxDB Configuration
INFLUXDB_INIT_USERNAME=admin
INFLUXDB_INIT_PASSWORD=password
INFLUXDB_INIT_ADMIN_TOKEN=mytoken
```

Also modify your hosts file to support temptasty.org

```
127.0.0.1 temptasty.org
```

To build and run the application, be sure to have npx, docker and docker-compose installed.
From root folder, call

```
./run.sh
```

# Todos

- [ ] backend - ssl cert
- [ ] sensor - try mqtt for backend communication
- [ ] backend - support mqtt
- [ ] backend - spotbugs, pitest
- [ ] backend - native executable
- [ ] frontend - tests
- [ ] frontend - enhance sample-viewer with mean and filters
- [ ] sensor - dynamic sensor count
- [ ] frontend - support dynamic sensor count
