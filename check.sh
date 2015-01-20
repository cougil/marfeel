#/bin/sh
curl -X POST -d @src/test/resources/sites.json http://localhost:8080/getSites --header "Content-Type:application/json"
