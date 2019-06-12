# PMS Orange

This Repository contains a Java Vaadin Application that was developed by team Orange. This Project was part of the lecture in Software Engineering and Design at the Bern University of Applied Sciences (BFH). It was used to get used to the Scrum process and agile development.

The Project is about a Patient Management System to manage objectives and reports of patients with mental health desorder.
If offers the possibility to create reports, send messages between doctors and objective management with sub tasks.


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
