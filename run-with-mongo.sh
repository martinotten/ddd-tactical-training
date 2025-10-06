#!/bin/bash

echo "🐳 Starting MongoDB with Docker..."

# Check if container already exists
if [ "$(docker ps -aq -f name=ddd-training-mongo)" ]; then
    echo "📦 Stopping existing MongoDB container..."
    docker stop ddd-training-mongo >/dev/null 2>&1
    docker rm ddd-training-mongo >/dev/null 2>&1
fi

# Start MongoDB
echo "🚀 Starting fresh MongoDB container..."
docker run --name ddd-training-mongo \
    -p 27017:27017 \
    -d \
    --rm \
    mongo:7.0

# Wait for MongoDB to be ready
echo "⏳ Waiting for MongoDB to be ready..."
for i in {1..30}; do
    if docker exec ddd-training-mongo mongosh --eval "db.adminCommand('ping')" >/dev/null 2>&1; then
        echo "✅ MongoDB is ready!"
        break
    fi
    if [ $i -eq 30 ]; then
        echo "❌ MongoDB failed to start within 30 seconds"
        exit 1
    fi
    sleep 1
done

echo "🎯 Starting Spring Boot application..."
mvn spring-boot:run

echo "🛑 Stopping MongoDB container..."
docker stop ddd-training-mongo >/dev/null 2>&1