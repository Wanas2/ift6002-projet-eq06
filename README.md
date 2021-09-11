## Structure des modules

Il existe trois [modules maven](https://maven.apache.org/guides/mini/guide-multiple-modules.html) dans le projet :

* `external-service-api` : ce projet offre un API codé avec Spring Boot. Son utilité sera révélé au cours des _user stories_ de la session.
* `game-api` : le projet que vous développerez. Ici, vous pouvez modifier tout ce qui vous plait. Le code présent n'est pas nécessairement bon/bien placé/selon les normes de votre équipe. À vous de voir!
* `application` : permet de démarrer les 2 API simultanément. Vous pouvez le modifier également, mais c'est rarement nécessaire.

## Notes sur les technologies utilisées

* [Jetty](https://www.eclipse.org/jetty/) est un Servlet Container. Il accepte les requêtes du web et sait comment répondre.
* [Jersey](https://jersey.github.io/) est un Servlet fait pour le développement d'API REST. Il sait comment faire la correspondance entre un API REST et vos méthodes selon la norme JAX-RS.
* [Jackson](https://www.baeldung.com/jackson) sert à sérialiser/désérialiser les objets JSON en POJO.

## Intégration Docker

Un Dockerfile est également fournis si vous désirez essayer de rouler votre code sur les mêmes images docker que nous utiliserons.

Pour ce faire:

```bash
docker build -t application-glo4002 .
docker run -p 8080:8080 -p 8181:8181 application-glo4002
```

## Démarrer le projet

Vous pouvez démarrer soit un des deux serveurs (GamerServer ou VetServer) individuellement ou simultanément.

```bash
mvn clean install
mvn exec:java -pl application
```
