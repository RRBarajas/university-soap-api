# Choice University SOAP API

SOAP API to handle the business operations for the Choice University.

## Getting started

### Building the project

### Setting up the database

This project was set with a [Postgres database running in a Docker container](https://hub.docker.com/_/postgres); however, it can be changed to any relational database of your choosing.  
There are plenty of guides online regarding how to install docker, containers and running them.  
_*In case you're using mac, [you could check Colima](https://github.com/abiosoft/colima) as a possible alternative to docker._

The following in just **an example** on how to expose the DB along with a pgAdmin container to administer it:
```bash
docker pull postgres
docker run --name <container_name> -d -p 5432:5432 -v <container_name>:/var/lib/postgresql/data -e POSTGRES_DB=<default_db> -e POSTGRES_USER=<db_user> -e POSTGRES_PASSWORD=<db_user_password> postgres

docker pull dpage/pgadmin4
docker run --name <container_name> -d -p 5050:80 -e PGADMIN_DEFAULT_EMAIL=<pgadmin_email> -e PGADMIN_DEFAULT_PASSWORD=<pgadmin_password> pgadmin4
```
_*Above command is to manually start the container; but it could be changed to use docker-compose, which simplifies things a lot._

```bash
mvn clean install
```

### Running the project

```bash
mvn spring-boot:run
```

### Code coverage

We plan to use Jacoco to validate the code coverage; however, that is not yet implemented.  
In order to execute the coverage report you can run the following command:

```bash
TBD
```

## Additional information 

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.4/maven-plugin/reference/html/#build-image)
* [Spring Web Services](https://docs.spring.io/spring-boot/docs/2.7.4/reference/htmlsingle/#io.webservices)

### Guides
The following guides illustrate how to use some features concretely:

* [Producing a SOAP web service](https://spring.io/guides/gs/producing-web-service/)

