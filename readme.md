[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/vaadin-flow/Lobby#?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

# Skeleton Starter for Vaadin Flow

This project can be used as a starting point to create your own Vaadin Flow application.
It has the necessary dependencies and files to help you get started.

The best way to use it by via [vaadin.com/start](https://vaadin.com/start) - you can get only the necessary parts and choose the package naming you want to use.
There is also a [getting started tutorial](https://vaadin.com/docs/v10/flow/introduction/tutorial-get-started.html) based on this project.

To access it directly from github, clone the repository and import the project to the IDE of your choice as a Maven project. You need to have Java 8 installed.

Run using `mvn jetty:run` and open [http://localhost:8080](http://localhost:8080) in browser.

For a full Vaadin Flow application example, there is the Beverage Buddy App Starter for Flow available also from [vaadin.com/start](https://vaadin.com/start) page.

Branching information:
* `master` the latest version of the starter, using latest platform snapshot
* `v10` the version for Vaadin 10
* `v11` the version for Vaadin 11
* `v12` the version for Vaadin 12
* `v13` the version for Vaadin 13


Setup MongoDB on Mac OS X
-------------------------

Open terminal 
```
brew update
brew install mongodb
```
Default mongodb file location /data/db/
```
mkdir -p /data/db
```

Handle directory permissions
```
sudo chown -R `id -un` /data/db
```

Start service
```
mongod
```

Access DB in another terminal tab
```
mongo
```

Use MongoDB via terminal
```
help
db.help()
collection.help()
```

Display the content of all collections in a DB
```
show dbs
use <db name>
show collections
db.<collectionName>.find().pretty()
```

PMS Orange Application Setup
----------------------------

This chapter describes how to setup the PMS Orange application. 

First you have to checkout the source code from the repository:
```
git clone https://github.com/thetractor/ch.bfh.bti7081.s2019.orange.git
```

Start the mongoDB service (be sure mongoDB is installed as described above)
```
mongod
```

Generate dummy data by executing the following file:
```
DataGenerator.java
```


Create a new "Run/Debug Configuration" using Maven and define the "Parameters" tab as followed:
```
Working directory:    /path to/ch.bfh.bti7081.s2019.orange
Command line:         jetty:run
```

Then run the project and you'll be good to go!

You'll be able to visit the web-app by accessing your localhost on port 8080 using a browser of your choice. 
```
http://127.0.0.1:8080
```
