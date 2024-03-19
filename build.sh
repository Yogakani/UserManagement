echo 'Start building the application.'
echo '------------------------------------------'
mvn clean install
echo '------------------------------------------'
echo 'Removing the fat original jar'
echo '------------------------------------------'
rm target/*.original
echo 'Removed the original jar'
echo '------------------------------------------'
echo 'Starting the application'
echo '------------------------------------------'
java -jar target/*.jar
