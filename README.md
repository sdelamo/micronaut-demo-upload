# Micronaut Demo upload Files

The goal of this code is to test the updload of big files using Streaming capabilities


Current Code use Micronaut 4.6.3


In the data directory are some files of different sizes to test the upload works



### how to test


Start Application
---

```
./gradlew run
```

### Test upload Files using the Stream Controller

Customer Purchases 1Kb File

```
curl -X POST http://localhost:8080/upload-stream -F "file=@./data/1k.csv"
```

LakeCounty Health - 3kb F

```
curl -X POST http://localhost:8080/upload-stream  -F "file=@./data/3k.csv"
```

Air Quality File - 2.1MB

```
curl -X POST http://localhost:8080/upload-stream -F "file=@./data/2MB.csv"
```

Electric Vehicle Population 51MB File

```
curl -X POST http://localhost:8080/upload-stream  -F "file=@./data/51MB.csv"
```

### Test upload Files using the CompletedFileUpload Controller

Customer Purchases 1Kb File

```
curl -X POST http://localhost:8080/upload -F "file=@./data/1k.csv"
```

LakeCounty Health - 3kb F

```
curl -X POST http://localhost:8080/upload  -F "file=@./data/3k.csv"
```

Air Quality File - 2.1MB

```
curl -X POST http://localhost:8080/upload  -F "file=@./data/2MB.csv"
```

Electric Vehicle Population 51MB File

```
curl -X POST http://localhost:8080/upload -F "file=@./data/51MB.csv"
```