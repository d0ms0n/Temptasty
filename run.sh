#!/bin/bash

cd receiver;
./mvnw clean package;
cd ../frontend/temptasty;
npx ng build;
cd ../../;
docker-compose up --build -d;
