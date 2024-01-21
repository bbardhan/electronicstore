# Getting Started

### How to use the application 

1) In order to build the application, please run the following command from the project base directory:
For example, the base directory can be C:\Projects\electronicstore
  * mvn clean install

2) In order to run the application, please run the following command from the project base directory:

  * java -jar  .\target\electronicstore-0.0.1-SNAPSHOT.jar

3) In order to test the application, please run the following command from the project base directory:

  * mvn test

### How to test the application from Postman

1) The API are secured which requires JWT authentication. There are some pre-defined users which have been granted
   permission. The details can be found in UserConfig.java file. 

2) Once the credentials have been used while calling "/auth" API, the JWT token will get generated.
   And the subsequent calls to other API will be accessed via "Bearer Token" authorization.



