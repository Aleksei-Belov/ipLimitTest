## Software required
+ Java 17
+ Docker


## Build

```
gradle build
```

## Run docker

```
docker build -t ip_limit_test .
docker run -p 8080:8080 ip_limit_test
```

## Available REST APIs

1.   Main get API
```
GET: http://localhost:8080/api/limit
```

2. Test TestRestController to validate executing service methods
```
GET: http://localhost:8080/api/test/callTestMethod
```