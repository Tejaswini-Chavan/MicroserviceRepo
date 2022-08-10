# MICROSERVICEREPO
---------------------

REQUIREMENTS
--------------
Latest vesion of Docker desktop. Fot furhter information, visit https://docs.docker.com/desktop/install/windows-install/

STARTING THE APPLICATION
------------------------
If you have Docker installed, following commands are available:

docker-compose up :

 *Copy MicroserviceRepo/docker-compose.yml in your local system.
 
 *Run the above command using cmd prompt to bring up the application. (Note: Use 'docker-compose up -d' to run the file in detached mode)
 
docker-compose down: Use this command to terminate the running application.


USING THE APPLICATION
------------------------

Hit the below urls in order to verify the application using postman:

*To create user:

Method: Post; URL: http://localhost:4000/app/user/save;
  Sample JSON: 
 {
 "id":null,
 "name":"Hemant",
 "balance":300
 }

*To place Order:

Method: Post; URL: http://localhost:4000/app/order/create;
 Sample JSON:
 {
 "id":null,
 "orderAmount":10,
 "status":"CREATED",
 "userId":1
 }

*To view list of all orders:

Method: Get; URL: http://localhost:4000/app/order/all

*To view list of all users:

Method: Get; URL: http://localhost:4000/app/user/all
