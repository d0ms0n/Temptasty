FROM alpine:3.17.3

RUN apk add --no-cache bash curl
COPY generate_temp_measurements.sh /usr/local/bin/

RUN adduser --disabled-password --no-create-home tempuser && chmod +rx /usr/local/bin/generate_temp_measurements.sh
USER tempuser

CMD ["/usr/local/bin/generate_temp_measurements.sh"]