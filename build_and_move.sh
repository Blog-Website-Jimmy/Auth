#!/bin/bash

DEST_DIR="/home/jemsit/Desktop/blog-website-management/docker-postgres/services/backend"

./mvnw clean package

mv target/Auth-0.0.1.jar "$DEST_DIR"