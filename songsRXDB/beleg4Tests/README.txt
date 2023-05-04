Praesentation von Beleg 3: Bitte folgende Befehle ausfuehren 

cd songsRX
git log
git status
mvn clean package
cp target/songsRX.war $CATALINA_HOME/webapps
cd beleg3Tests
./getToken.sh GIBS_NICHT_NUTZER (asdfdf blabla)
./getToken.sh IHR_NUTZER (eschuler, mmuster) Den Token, der zurueckgeschickt wird, kopieren
./songsRXTesterJSON.sh TOKEN
./songsRXTesterXML.sh TOKEN

gitHub: Pull Request erstellen