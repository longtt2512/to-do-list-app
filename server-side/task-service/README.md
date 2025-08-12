# Task Service - Infrastructure Credentials

This service provides a Docker Compose setup for PostgreSQL and MinIO. Below are the credentials and connection URLs you can use to connect to these services when running locally.

## Docker Compose Services
The docker-compose file is located at `docker/docker-compose.yml` and starts:
- PostgreSQL 14 (exposed on host port 5432)
- MinIO (S3-compatible) API on 9000 and Console on 9001

Start the stack:

```
cd docker
docker compose up -d
```

## PostgreSQL Connection Details
- Host: `localhost`
- Port: `5432`
- Database: `appdb`
- Username: `appuser`
- Password: `secretpassword`
- JDBC URL: `jdbc:postgresql://localhost:5432/appdb`

Examples:
- psql CLI:
  ```bash
  PGPASSWORD=secretpassword psql -h localhost -p 5432 -U appuser -d appdb
  ```
- Spring application properties:
  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/appdb
  spring.datasource.username=appuser
  spring.datasource.password=secretpassword
  ```

## MinIO Connection Details
- S3 API Endpoint: `http://localhost:9000`
- Web Console: `http://localhost:9001`
- Access Key (username): `minio`
- Secret Key (password): `miniosecret123`
- Default Region: `us-east-1` (used in the app config)
- Bucket created on startup: `app-bucket`

Examples:
- MinIO Console login: visit `http://localhost:9001` and use the credentials above.
- AWS SDK/S3-compatible config (environment variables):
  ```bash
  export MINIO_ENDPOINT=http://localhost:9000
  export MINIO_ACCESS_KEY=minio
  export MINIO_SECRET_KEY=miniosecret123
  ```
- Spring application properties (as used by this service):
  ```properties
  minio.endpoint=http://localhost:9000
  minio.access-key=minio
  minio.secret-key=miniosecret123
  minio.region=us-east-1
  ```

If you change any credentials or ports in `docker/docker-compose.yml`, update the values above accordingly.
