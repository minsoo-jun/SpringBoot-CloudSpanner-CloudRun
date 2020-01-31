# Cloud Run + Spring Boot + Cloud Spanner Example
## 実行方法
### 開発環境
```text
JDK 1.8
SpringBoot: 2.2.4.RELEAS
```
application.properties
```
spring.cloud.gcp.spanner.project-id=[GCP PROJECT ID]
spring.cloud.gcp.spanner.instance-id=[SPANNER INSTANCE ID]
spring.cloud.gcp.spanner.database=[SPANNER DATABASE NAME]
spring.cloud.gcp.credentials.location=file:[SERVICE ACCOUNT FILE PATH]
```

### ローカル環境
```
mvn clean install
java -jar target/spanner-0.0.1-SNAPSHOT.jar

# check http://127.0.0.1:8080
```
### GCP
```text
gcloud builds submit --tag gcr.io/${PROJECT_ID}/spanner-helloworld
gcloud beta run deploy --image gcr.io/${PROJECT_ID}/spanner-helloworld --set-env-vars="JAVA_TOOL_OPTIONS=-XX:MaxRAM=512m" --concurrency=1 --memory 1G
```