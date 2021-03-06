# TNCITY
The initial framework for the SimCity-style game

## D�pendances

* Junit 4 : [Junit-4.12.jar](https://github.com/junit-team/junit4/releases/download/r4.12/junit-4.12.jar), [Junit-4.12-sources.jar](https://github.com/junit-team/junit4/releases/download/r4.12/junit-4.12-sources.jar), [Junit-4.12-javadoc.jar](https://github.com/junit-team/junit4/releases/download/r4.12/junit-4.12-javadoc.jar)
* Hamcrest-core : [Hamcrest-core.jar](https://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar), [Hamcrest-core-sources.jar](https://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3-sources.jar), [Hamcrest-core-javadoc.jar](https://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3-javadoc.jar)

## R�gles du jeu et fonctionnement

### Les �l�ments du jeu

Le jeu dispose de quatre �l�ments :
* Zone r�sidentielle
* Zone Commerciale
* Zone industrielle
* Centrale �lectrique


Ces �l�ments peuvent �tre dispos�s sur la carte gr�ce � des outils et moyennant une somme d'argent (d�finie dans le code).

Une fois sur la carte, chaque �l�ment poss�de deux �tats :
* En construction : La zone ne produit et  ne consomme aucune ressource
* Construit : La zone produit et consomme des ressources � chaque refresh

#### La zone r�sidentielle

* Condition d'�volution : �tre aliment�e en �nergie
* Consommation courante : �tre aliment�e en �nergie
* production courante :  Population par refresh (jusqu'� un seuil)


#### La zone Commerciale

* Condition d'�volution : �tre aliment�e en �nergie et avoir suffisamment de Population
* Consommation courante : �tre aliment�e en �nergie, avoir suffisamment de Population et de produits
* production courante :  Imp�ts ( de l'argent)


#### La Zone industrielle

* Condition d'�volution : �tre aliment�e en �nergie et avoir suffisamment de Population
* Consommation courante : �tre aliment�e en �nergie et avoir suffisamment de Population
* production courante :  Des produits (jusqu'� un seuil)


#### Centrale �lectrique

* Condition d'�volution : aucune
* Consommation courante : aucune
* production courante :  Une quantit� d'�nergie (jusqu'� un seuil)

## Remerciements et sources

* Elana18 sur [DeviantArt](http://deviantart.com) pour les sprites (http://elana18.deviantart.com/art/Paper-Sim-City-Starter-Pack-126481815 et http://elana18.deviantart.com/art/Paper-Sim-City-Pack-2-156173184)

