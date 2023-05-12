# Temptasty - temperature measurements

This repository includes a demo implementation of a fictional IOT scenario,
where sensors take temperature readings and send them to a backend.

Used technologies are:

- influxdb 2.0
- quarkus 3.0.1
- angular 15.2.7
- nginx 1.23.3

# Component overview
The application consists of five components: a sensor, a frontend, a backend, a reverse-proxy and a database. The sensor collects temperature readings and sends them to the backend, which stores them in the database. The frontend displays the readings in a chart. The reverse-proxy takes the calls from browser or sensor and roots them to the correct direction.

![component model](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.github.com/d0ms0n/Temptasty/master/components.iuml)

## Sensor

The sensor is a mock device that generates randomized temperature readings between 10 and 300 degrees Celsius. The sensor runs in an Alpine Linux docker container and sends the readings to a receiver URL. 
You can configure the sensor by setting environment variables such as:

- RECEIVER_URL=https://temptasty.org/measurements
- SLEEP_TIME=5

The produced json format looks like:

```json
{
    "name": "sensor1",
    "unit": "celsius",
    "time": "2023-05-11T14:34:17Z",
    "value": 66.72
}
```

## Frontend
The frontend is an Angular app that displays a chart of the temperature readings. The chart is generated using the Chart.js and ng2-charts libraries. Learn more about Angular [here](https://angular.io/). To build and run the frontend, use the following commands:

```shell script
# lint the code 
ng lint --fix

# build to dist folder
ng build

# develop with local dev server
ng serve
```


To view the frontend, go to https://temptasty.org/measurement-viewer

## Backend
The backend is a Quarkus application that receives temperature readings from the sensor and stores them in the database. Learn more about quarkus [here](https://quarkus.io/). To build and run the backend, use the following commands:

```shell script
# run in dev mode with live coding
./mvnw compile quarkus:dev

# build to target folder
./mvnw package
```

To view the API documentation, go to http://localhost:8080/q/swagger-ui/. You can use the following cURL commands to interact with the backend:

```shell script
# get all measurements
curl -i -X GET 'https://temptasty.org/measurements'

# get mean within a range of the last hour (h) day (d) month (m)
# filter with query parameter "sensor"
curl -i -X GET 'https://temptasty.org/measurements/mean?sensor=sensor1&range=h'
```

## Database
The database is an InfluxDB docker image. 
Learn more about InfluxDB [here](https://docs.influxdata.com/influxdb/v2.7/). To configure the database, go to http://localhost:8086/

# Running the application

To run the application, you must provide an .env file in the root folder with the following content:

```shell script
# InfluxDB Configuration
INFLUXDB_INIT_USERNAME=admin
INFLUXDB_INIT_PASSWORD=password
INFLUXDB_INIT_ADMIN_TOKEN=mytoken
```

You also need to modify your hosts file to support the domain `temptasty.org`:

```h
127.0.0.1 temptasty.org
```

To create a self-signed SSL certificate using OpenSSL, run the following command:
```shell script
openssl req -x509 -newkey rsa:4096 -keyout reverse-proxy/certs/self-signed.key -out reverse-proxy/certs/self-signed.crt -days 365 -nodes -subj '/CN=temptasty.org'

``` 

This will create a self-signed SSL certificate with a validity period of 365 days, and store the certificate and private key in the reverse-proxy/certs directory.

Before starting the application, you will need to build the Docker images and start the Docker containers. To do this, ensure that you have `npx`, `docker`, and `docker-compose` installed on your system, and then run the following command from the root folder:

```shell script
./run.sh
```
This will build the Docker images and start the Docker containers for the InfluxDB, Quarkus, Angular, and Nginx components of the application. Once the containers are running, you can access the application by navigating to https://temptasty.org/measurement-viewer in your web browser.


# Todos

- [ ] backend - ssl cert
- [ ] frontend - store google fonts locally
- [ ] backend - time validation
- [ ] backend - own model for mean values
- [ ] backend - authentication and authorization
- [ ] architecture - split in different docker networks
- [ ] sensor - try mqtt for backend communication
- [ ] backend - support mqtt
- [ ] backend - spotbugs, pitest
- [ ] backend - native executable
- [ ] frontend - use docker build image
- [ ] frontend - tests
- [ ] frontend - improve measurement-viewer with mean and filters
- [ ] sensor - dynamic sensor count
- [ ] frontend - support dynamic sensor count
- [ ] frontend - internationalization (i18n)
- [ ] frontend - accessibility
