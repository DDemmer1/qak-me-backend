# Backend-Service for the https://qak.me Link-Shortener

This repository contains the backend for the link shortener qak.me

If you want to get your qak.me service up and running with a frontend client simply install [Docker](https://docs.docker.com/get-docker/) and run this public container

```
docker run -d -p 8080:8080 ddmr/prune-link
```
Your service will be available under

```
localhost:8080
```



REST-API Entrypoint:

```
Redirect to Short URL
GET /{shoturl}
Response: 
```


```
Set new Short URL
POST /shorten
Request Body: 
{originalUrl: "yourOriginalURl.com", alias: "yourAlias" }

Response:
{success: true/false , message: "qak.me/your-id-or-alias" }
```







