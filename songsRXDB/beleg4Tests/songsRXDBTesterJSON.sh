#! /bin/sh
# Usage:
# ./songsRXTester.sh auth_token
# Example:
# ./songsRXTester.sh 1234567890poiuytrewq
# 

if [ "$#" -ne 1 ]; then
    echo "Illegal number of parameters"
    echo "Usage: ./songsRXTesterJSON.sh auth_token"
    echo "Example: ./songsRXTesterJSON.sh 1234567890poiuytrewq"
    exit 1
fi

echo "--- REQUESTING ALL SONGLISTS WTH BAD TOKEN: should return 401 --------"
curl -X GET \
     -H "Authorization: 1234567890poiuytrewq" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songLists"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING ALL SONGLISTS WTHOUT TOKEN: should return 401 or 404 --------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songLists"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING ALL SONGLISTS IN JSON (no user or list difined): should return 404 --------"
curl -X GET \
     -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songLists"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING ALL SONGLISTS IN JSON for user 1 --------"
curl -X GET \
     -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songLists?userId=1"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING ALL SONGLISTS IN JSON for user 2 --------"
curl -X GET \
     -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songLists?userId=2"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING SONGLIST 1 IN JSON --------"
curl -X GET \
     -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songLists/1"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING SONGLIST 4 IN JSON: if mmuster as token should return 404 --------"
curl -X GET \
     -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songLists/4"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING NON-EXISTING SONGLIST  100: should return 404 --------"
curl -X GET \
     -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songLists/100"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING NON-EXISTING SONGLIST 'BLA': should return 404 --------"
curl -X GET \
     -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songLists/BLA"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- POSTING A JSON SONGLIST ------------------"
curl -X POST \
     -H "Authorization: $1" \
     -H "Content-Type: application/json" \
     -d "@eineSongList.json" \
     -v "http://localhost:8080/songsRX/rest/songLists"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- POSTING A BAD JSON SONGLIST ------------------"
curl -X POST \
     -H "Authorization: $1" \
     -H "Content-Type: application/json" \
     -d "@keineSongList.json" \
     -v "http://localhost:8080/songsRX/rest/songLists"
echo " "
echo "-------------------------------------------------------------------------------------------------"
echo "--- REQUESTING NEW SONGLIST IN JSON--------"
curl -X GET \
     -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songLists/7"
echo " "

echo "-------------------------------------------------------------------------------------------------"

echo "--- DELETING SONGLIST 7: should return 204 no content--------"
curl -X DELETE \
     -H "Authorization: $1" \
     -v "http://localhost:8080/songsRX/rest/songLists/7"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- DELETING SONGLIST 7 AGAIN: SHOULD PRODUCE 404 --------"
curl -X DELETE \
     -H "Authorization: $1" \
     -v "http://localhost:8080/songsRX/rest/songs/7"
echo " "
echo "-------------------------------------------------------------------------------------------------"

