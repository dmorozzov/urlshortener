#!/bin/bash
docker compose -f docker-compose.yml up -d redis
docker ps
