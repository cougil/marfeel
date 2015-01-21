#/bin/sh
curl -X POST -d @src/test/resources/litle.json http://localhost:8080/getSites --header "Content-Type:application/json"
#curl -X POST -d @src/test/resources/sites.json http://localhost:8080/getSites --header "Content-Type:application/json"
