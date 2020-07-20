#Steps to run the whole project:

1. Clone the repository for the Frontend (Vue Js).
      
     > git clone https://github.com/Abrar535/Cefalo-News-Portal--FrontEnd.git
                                                             >
2. Clone the repository for the Backend (Spring Boot).
    
   > git clone https://github.com/Abrar535/Cefalo-News-Portal-Project-Backend.git
                                                               >

3. Copy the file "docker-compose.yml" from the folder "Cefalo-News-Portal-Project-Backend".

4. Put the file "docker-compose.yml" and the two folders "Cefalo-News-Portal--FrontEnd" and "Cefalo-News-Portal-Project-Backend" in the same directory. 

5. Open terminal/command prompt from your machine and change directory to the new project folder. 

6. Run the command 'docker-compose up' or 'docker-compose up -d'(for detached mode running) to run the whole project(Backend,FrontEnd and Database servers) all together. 

7. Run the command 'docker-compose down' to stop the whole project (Backend,FrontEnd and Database servers). 