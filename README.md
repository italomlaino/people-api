## Getting Started

* Download the project:

```
git clone https://github.com/italomlaino/people-api.git
cd people-api
```

* Run (via docker-compose)

```
  docker-compose up
```

* or (without using a docker container)

```
  ./gradlew bootRun
```

## Routes

* Create

```
Path: POST /api/people
Body: {
    "name": {name},
    "birthday": {birthday},
    "maritalStatus": {maritalStatus},
    "spouseName": {spouseName},
    "spouseBirthday": {spouseBirthday}
}
Params:
    name:            min 5 characters, only letters and space allowed
    birthday:        string formatted as dd/MM/YYYY
    maritalStatus:   MARRIED, WIDOWED, DIVORCED or SINGLE
    spouseName:      only required if MARRIED
    spouseBirthday:  only required if MARRIED
```

* Update

```
Path: PUT /api/people/{id}
Body: {
    "name": {name},
    "birthday": {birthday},
    "maritalStatus": {maritalStatus},
    "spouseName": {spouseName},
    "spouseBirthday": {spouseBirthday}
}
Params:
    id:              person id     
    name:            min 5 characters, only letters and space allowed
    birthday:        string formatted as dd/MM/YYYY
    maritalStatus:   MARRIED, WIDOWED, DIVORCED or SINGLE
    spouseName:      only required if MARRIED
    spouseBirthday:  only required if MARRIED
```

* Delete

```
Path: DELETE /api/people/{id}
params:
    id:             person id
```

* Find by ID

```
Path: GET /api/people/{id}
Params:
    id:             person id
```

* Find by Name

```
Path: GET /api/people?name={name}&pageNumber={pageNumber}&pageSize={pageSize}
Params:
    name:           the name of the person to search for
    pageNumber:     zero-based page index, must not be negative
    pageSize:       the size of the page to be returned, must be greater than 0     
```

## Dependencies

- JDK 11
- Spring Boot
- Spring Data
- Spring MVC
- Spring Validation
- JUnit 5
- Mockito
- H2
- Gradle
- Docker
- Docker-Compose


