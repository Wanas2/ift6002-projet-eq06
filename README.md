# ReadMe à supprimer avant la remise

Ce readme sert à vous expliquer la structure de base de votre projet. Remplacez le avant la première remise.

## Structure des modules

Il existe trois [modules maven](https://maven.apache.org/guides/mini/guide-multiple-modules.html) dans le projet : 

* `external-service-api` : ce projet offre un API codé avec Spring Boot. Son utilité sera révélé au cours des _user stories_ de la session.
* `game-api` : le projet que vous développerez. Ici, vous pouvez modifier tout ce qui vous plait. Le code présent n'est pas nécessairement bon/bien placé/selon les normes de votre équipe. À vous de voir!
* `application` : permet de démarrer les 2 API simultanément. Vous pouvez le modifier également, mais c'est rarement nécessaire.
 
## Notes sur la structure courante
 
Vous pouvez modifier tous les fichiers du projet, autre que ceux dans `external-service-api`. Considérez qu'il est hébergé ailleurs; vous ne pouvez en aucun cas le modifier. Lors de la correction, nous écraserons toutes vos modifications à ce projet.

La structure dans `game-api` est à titre d'exemple, vous êtes libre de renommer le module ou d'en faire ce que vous voulez. Vous pouvez modifier `application`, mais ce n'est pas nécessaire. Ne renommer pas les modules `application` et `external-service-api`.

:warning: `external-service-api` **n'est volontairement pas une bonne référence**. Ce type de projet a une utilité (à voir plus tard dans la session), mais n'essayez pas de le copier.

## Notes sur les technologies utilisées

* [Jetty](https://www.eclipse.org/jetty/) est un Servlet Container. Il accepte les requêtes du web et sait comment répondre.
* [Jersey](https://jersey.github.io/) est un Servlet fait pour le développement d'API REST. Il sait comment faire la correspondance entre un API REST et vos méthodes selon la norme JAX-RS.
* [Jackson](https://www.baeldung.com/jackson) sert à sérialiser/désérialiser les objets JSON en POJO.

Si vous n'êtes pas familiers avec ces concepts, il est fortement suggéré d'utiliser google/stackoverflow pour les apprendre rapidement. Ceux-ci peuvent être matière à examen.

:warning: Jetty, Jersey et Jackson sont utilisés dans le projet de base. Vous pouvez choisir d'autres technologies, mais vous devrez analyser les avantages et inconvéniants d'abord. Si vous voulez le faire, venez nous en parler. Entre autre, Spring et Spring Boot sont reconnus pour causer des maux de têtes vers la fin de la session. Dans le passé, certains étudiants ont râtés certaines remises à cause de ce choix.

## Intégration Docker

Un Dockerfile est également fournis si vous désirez essayer de rouler votre code sur les mêmes images docker que nous utiliserons.

Pour ce faire:

```bash
docker build -t application-glo4002 .
docker run -p 8080:8080 -p 8181:8181 application-glo4002
```

## Démarrer le projet

Vous pouvez démarrer soit un des deux serveurs (GamerServer ou VetServer) individuellement ou simultanément.

Il y a trois classes que vous pouvez exécuter dans IntelliJ ou Eclipse pour cela: `GameServer`, `VetServer` ou `ApplicationServer`. Aucun des `main()` ne demande d'argument.

Vous pouvez également rouler le serveur via maven (**c'est ce qui sera utilisé pour corriger votre projet**) : 

```bash
mvn clean install
mvn exec:java -pl application
```

:warning: Ces commandes seront utilisées pour la correction automatique du projet. Elles doivent fonctionner!

Une resource "heartbeat" vous est fournis pour tester que le service démarre bien. Allez à l'URL `http://localhost:8181/heartbeat?token=unique_token` pour le valider. Vous n'êtes pas obligés de garder le heartbeat (mais si vous le supprimez, ne laissez pas de code mort!)

Lors du démarrage de `external-api-service`, vous pourrez également voir sa documentation [ici](http://localhost:8080/swagger-ui/index.html).

## Prochaines étapes

Nous regarderons le projet ensemble pendant les premières séances du cours. Le client sera également présent pour vous le présenter davantage. D'ici là, assurez-vous de pouvoir exécuter le code sur votre machine.

## Besoin d'aide?

Contactez-nous sur Discord à partir de votre channel d'équipe si quelque chose ne fonctionne pas!
