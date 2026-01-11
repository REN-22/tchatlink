TChatLink
========

TChatLink est un mod Minecraft Forge qui Permet l'utilisation de Baritone via un autre client que celui de Baritone lui-même. Il intercepte les commandes d'autre client et les relaye a Baritone via le chat en jeu.

**Requis**
- Minecraft 1.21.1
- Forge 52.1.0
- Baritone 1.11.2

**Installation**
1. Utilisez un launcher supportant plusieurs sessions avec des UUID personnalisés (ex. : Prism Launcher)
2. Le serveur doit fonctionner en mode hors ligne en raison du support UUID personnalisé
3. Placez le fichier JAR du mod dans votre dossier mods
4. Placez le fichier JAR baritone-standalone-forge-1.11.2 dans votre dossier mods

Démarrage rapide
----------------

Méthode 1 Compiler :

    se placer a la racine

    ./gradlew build

Le fichier JAR du mod produit se trouvera dans `build/libs` après une compilation réussie.

Méthode 2 télécharger le JAR dans le dernier release

Structure du projet
-------------------

- Source : `src/main/java`
- Ressources et mixins : `src/main/resources` (voir `mixins.tchatlink.json`)
- Métadonnées du mod : `META-INF/mods.toml`

Fichiers utiles
---------------

- Licence : `LICENSE.txt`
- Crédits : `CREDITS.txt`
- Historique : `changelog.txt`

Contribution
------------

Veuillez ouvrir des issues ou des pull requests. Suivez le style de code existant et gardez les modifications minimales et ciblées.

Contact
-------

Consultez les fichiers du référentiel pour les informations sur l'auteur/responsable.

