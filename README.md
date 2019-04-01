## Very simple Spring Boot 2 JWT Oauth implementation

Admin credentials: admin/admin
<br>User credentials: davi/davi123

Admin token retrieval example
```
curl -X POST -H "Authorization: Basic $(echo -n showme-client:showme-secret | base64)" -H "Content-type: application/x-www-form-urlencoded" -d "username=admin&password=admin&grant_type=password" http://localhost:8080/oauth/token
```

Accessing admin area

```
curl -i -H "Accept: application/json" -H "Authorization: Bearer $TOKEN" -X GET http://localhost:8080/admin
```

Accessing user area

```
curl -i -H "Accept: application/json" -H "Authorization: Bearer $TOKEN" -X GET http://localhost:8080/standard
```

Accessing public area

```
curl http://localhost:8080/public
```