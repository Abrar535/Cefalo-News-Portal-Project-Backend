# Restful-CRUD-Application-Cefalo-NewsPortal

# Required Tools:

  - JDK 14
  - Maven / Gradle


# Steps to run the application:

1. Clone the repository.
    
   > git clone https://github.com/Abrar535/Cefalo-News-Portal-Project-Backend.git


2. Setup maven or gradle.

   To install Maven on windows:

     * Visit the Apache Maven site, download the Maven Binary zip file of the latest version, and unzip the file to the folder that you want to use Maven in.

     * Add both M2_HOME and MAVEN_HOME variables to the Windows environment variables (using system properties), and point them to your Maven folder.

     * Update the PATH variable by appending the Maven bin folder – %M2_HOME%\bin. This way, Maven’s commands can be run anywhere.

    To check whether the above steps have been completed successfully, run the following command in the command prompt:
    
    > mvn -version
 
    
3. Go to **Restful-CRUD-Application-Cefalo-Assignment-master\Crud** folder from command prompt and run this command:
   > mvn install
   
4. Go to **Cefalo-News-Portal-Project-Backend-master\target**  folder and hit this command to run the application
   > java -jar docker-backend.jar
   
   Or, you can use this **maven** command to run the application
   > mvn spring-boot:run
   
   Or, you can also use this **gradle** command to tun the application if you want to use gradle
   > gradle bootRun


# Basic Features
 * Story Creation
    * Save as a draft
    * Set a date to publish the story
* Story Update
  * Save as draft
  * Set a date to publish further
* Story deletion
* Get published story
* Get a particular story
* Get story of a particular user
* Create Tag
* Add tags to story
* Get post by a particular tag
* Add comment
* Delete comment
* Get trending topics (tags)
* Search story
* Application level caching ...



# Brief description on the endpoints 

## User 

### POST  `/api/public/register/users`

 ### Request body Scheme: 
 
 
 
    {
    userName*		    string
                    nullable: false
    
    fullName*		    string
                    nullable: false
    
    
    password*		 string
                     nullable: false   
    }

 
 ### Response Status

Status Code | Message
------------ | -------------
201 | Created
409 | Conflict
 
 ### Example
 
 ### Request Body: 
``` 
    {
        "userName": "tech",
        "fullName": "notAbrar",
        "password": "tech"
    }
```

 ### Response Body (409 Conflict) :

```
{
     UserName already Exists! Please select a unique UserName.
}
``` 
 
 ### POST `/api/public/authenticate`
 
 ### Request body Scheme: 
 
 
 
    {
    userName*		    string
                    nullable: false

    password*		 string
                     nullable: false   
    }
 
 
 ### Response Status

Status Code | Message
------------ | -------------
200 | OK
403 | Forbidden

### Example
 
 ### Request Headers: 
 
 ``` 
Content-Type : application/json
``` 
 
 ### Request Body: 
 
 ``` 
{
	"username":"tech",
	"password":"tech"
}
```
 
 
 ### Response Body (200 OK) :

```
{
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwiZXhwIjoxNTk1MjMzMDk3LCJpYXQiOjE1OTUxOTcwOTd9.l5ljj3GKj6-0M--ocabFyMz5ZK7roN_6ZIWiYTRCB0o"
}
``` 
 
 # Story
 
 ### POST  `/api/stories`
 
  ### Request body Scheme: 
 
 
 
    {
    title*		    string
                    nullable: false

    body*		     string                  
                     nullable: false 
                     
   
    drafted*          Boolean
                      nullable: false
    
    scheduled*        Boolean
                         nullable: false             
    
    tags            Array
                     nullable: true
    
    }
 
  ### Response Status

Status Code | Message
------------ | -------------
200 | Ok
403 | Forbidden
401 | unauthorized

 ### Request Headers: 
 
 ``` 
Authorization : Bearer "YOUR TOKEN"
``` 
 
 ### Request Body: 
 
 ``` 
{	
        "title": "Cricket is good",
        "body": "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi, cupiditate eaque eveniet ex incidunt iusto nemo quidem quos. Ea fugiat harum id maxime minima qui quisquam quos similique velit voluptatum!",
        "drafted": false,
        "scheduled":false,
        "tags":[
            {"tagName" : "Cricket"}
           
            
        ]
        
    }
```
 
 
 ### Response Body (200 OK) :

```
{
    "storyId": 8,
    "title": "Cricket is good",
    "body": "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi, cupiditate eaque eveniet ex incidunt iusto nemo quidem quos. Ea fugiat harum id maxime minima qui quisquam quos similique velit voluptatum!",
    "publishedDate": "2020-07-20 04:28:03",
    "scheduledDate": null,
    "drafted": false,
    "scheduled": false,
    "images": "",
    "user": {
        "userId": 6,
        "userName": "tech",
        "fullName": "notAbrar",
        "password": "$2a$10$AKViHh/ChYz3InROkO1CU.uPIURK8YQxzBUY1NVrRRjdyPScMotgK"
    },
    "tags": [
        {
            "tagId": 7,
            "tagName": "cricket",
            "numberOfStories": 0
        }
    ]
}
``` 


### PUT  `/api/stories/{storyId}`
  * storyId : (type : number)
  ### Request body Scheme: 
 
 
 
    {
   
    title*		     string
                     max: 250
                     nullable: false

    body*		     string
                     max: 10000
                     nullable: false 
                      
    drafted*        Boolean
                     nullable: true
    }
 
  ### Response Status

Status Code | Message
------------ | -------------
200| OK
403 | Forbidden
404 | Not Found


 ### Request Headers: 
 
 ``` 
Authorization : Bearer "YOUR TOKEN"

``` 
 
 ### Request Body: 
 
 ``` 
{	
        "title": "Hello Java!",
        "body": "Java rocks!",
        "drafted":false
       
}
```
 
 
 ### Response Body (404 Conflict) :

```
{
   No such Story Found to be updated
}
``` 


### DELETE  `api/stories/{storyId}`
 * storyId : (type : number)
 
  ### Response Status

Status Code | Message
------------ | -------------
200 | Ok
403 | Forbidden
404 | Not Found

 ### Request Headers: 
 
 ``` 
Authorization : Bearer "YOUR TOKEN"
``` 
 
 
 ### Response Body (200 OK) :

```
{
    "storyId": 8,
    "title": "Cricket is good",
    "body": "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi, cupiditate eaque eveniet ex incidunt iusto nemo quidem quos. Ea fugiat harum id maxime minima qui quisquam quos similique velit voluptatum!",
    "publishedDate": "2020-07-20 04:28:03",
    "scheduledDate": null,
    "drafted": false,
    "scheduled": false,
    "images": "",
    "user": {
        "userId": 6,
        "userName": "tech",
        "fullName": "notAbrar",
        "password": "$2a$10$AKViHh/ChYz3InROkO1CU.uPIURK8YQxzBUY1NVrRRjdyPScMotgK"
    },
    "tags": [
        {
            "tagId": 7,
            "tagName": "cricket",
            "numberOfStories": 0
        }
    ]
}
``` 

### GET  `api/public/stories?pageNum={pageNum}&pageSize={pageSize}`

  ### Response Status

Status Code | Message
------------ | -------------
200 | OK
400 | Bad Request
401 | Unauthorized

 ### Request Headers: 
 
 ``` 
Authorization : Bearer "YOUR TOKEN"
``` 

 ### Request Params: 
 
 ``` 
pageNum : NUMBER
pageSize : NUMBER
``` 

 
 
 ### Response Body (200 OK) :

```
{
    "totalNumberOfPages": 1,
    "totalNumberOfStories": 3,
    "stories": [
        {
            "storyId": 4,
            "title": "aaaa",
            "body": "aaa",
            "publishedDate": "2020-07-20 03:17:19",
            "scheduledDate": null,
            "drafted": false,
            "scheduled": false,
            "images": "",
            "user": {
                "userId": 1,
                "userName": "aaa",
                "fullName": "aaa",
                "password": "$2a$10$xBE5SyzGFDwKKH64hm5r5uVl9l/T0HBL90Reqsyyu3Ry84P8l35Vy"
            },
            "tags": [
                {
                    "tagId": 3,
                    "tagName": "aa",
                    "numberOfStories": 0
                },
                {
                    "tagId": 2,
                    "tagName": "aaa",
                    "numberOfStories": 0
                }
            ]
        },
        {
            "storyId": 9,
            "title": "Cricket is good",
            "body": "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi, cupiditate eaque eveniet ex incidunt iusto nemo quidem quos. Ea fugiat harum id maxime minima qui quisquam quos similique velit voluptatum!",
            "publishedDate": "2020-07-20 04:28:50",
            "scheduledDate": null,
            "drafted": false,
            "scheduled": false,
            "images": "",
            "user": {
                "userId": 6,
                "userName": "tech",
                "fullName": "notAbrar",
                "password": "$2a$10$AKViHh/ChYz3InROkO1CU.uPIURK8YQxzBUY1NVrRRjdyPScMotgK"
            },
            "tags": [
                {
                    "tagId": 7,
                    "tagName": "cricket",
                    "numberOfStories": 0
                }
            ]
        },
        {
            "storyId": 10,
            "title": "Cricket is good",
            "body": "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi, cupiditate eaque eveniet ex incidunt iusto nemo quidem quos. Ea fugiat harum id maxime minima qui quisquam quos similique velit voluptatum!",
            "publishedDate": "2020-07-20 04:28:55",
            "scheduledDate": null,
            "drafted": false,
            "scheduled": false,
            "images": "",
            "user": {
                "userId": 6,
                "userName": "tech",
                "fullName": "notAbrar",
                "password": "$2a$10$AKViHh/ChYz3InROkO1CU.uPIURK8YQxzBUY1NVrRRjdyPScMotgK"
            },
            "tags": [
                {
                    "tagId": 7,
                    "tagName": "cricket",
                    "numberOfStories": 0
                }
            ]
        }
    ]
}
``` 


### GET  `api/public/stories/{storyId}`
 
 * storyId : (type : number)
 
 
  ### Response Status

Status Code | Message
------------ | -------------
200 | OK
404 | Not Found


 ### Response Body (200 OK) :

```
   {
       "storyId": 9,
       "title": "Cricket is good",
       "body": "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi, cupiditate eaque eveniet ex incidunt iusto nemo quidem quos. Ea fugiat harum id maxime minima qui quisquam quos similique velit voluptatum!",
       "publishedDate": "2020-07-20 04:28:50",
       "scheduledDate": null,
       "drafted": false,
       "scheduled": false,
       "images": "",
       "user": {
           "userId": 6,
           "userName": "tech",
           "fullName": "notAbrar",
           "password": "$2a$10$AKViHh/ChYz3InROkO1CU.uPIURK8YQxzBUY1NVrRRjdyPScMotgK"
       },
       "tags": [
           {
               "tagId": 7,
               "tagName": "cricket",
               "numberOfStories": 0
           }
       ]
   }
```

### GET  `api/public/{userName}}/stories?pageNum={pageNum}}&pageSize={pageSize}}`
 
 * userName : (type : string)
 
 
  ### Response Status

Status Code | Message
------------ | -------------
200 | OK

 ### Request Params: 
  
  ``` 
 pageNum : NUMBER
 pageSize : NUMBER
 ``` 

 ### Response Body (200 OK) :

```
{
    "totalNumberOfPages": 1,
    "totalNumberOfStories": 2,
    "stories": [
        {
            "storyId": 9,
            "title": "Cricket is good",
            "body": "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi, cupiditate eaque eveniet ex incidunt iusto nemo quidem quos. Ea fugiat harum id maxime minima qui quisquam quos similique velit voluptatum!",
            "publishedDate": "2020-07-20 04:28:50",
            "scheduledDate": null,
            "drafted": false,
            "scheduled": false,
            "images": "",
            "user": {
                "userId": 6,
                "userName": "tech",
                "fullName": "notAbrar",
                "password": "$2a$10$AKViHh/ChYz3InROkO1CU.uPIURK8YQxzBUY1NVrRRjdyPScMotgK"
            },
            "tags": [
                {
                    "tagId": 7,
                    "tagName": "cricket",
                    "numberOfStories": 0
                }
            ]
        },
        {
            "storyId": 10,
            "title": "Cricket is good",
            "body": "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi, cupiditate eaque eveniet ex incidunt iusto nemo quidem quos. Ea fugiat harum id maxime minima qui quisquam quos similique velit voluptatum!",
            "publishedDate": "2020-07-20 04:28:55",
            "scheduledDate": null,
            "drafted": false,
            "scheduled": false,
            "images": "",
            "user": {
                "userId": 6,
                "userName": "tech",
                "fullName": "notAbrar",
                "password": "$2a$10$AKViHh/ChYz3InROkO1CU.uPIURK8YQxzBUY1NVrRRjdyPScMotgK"
            },
            "tags": [
                {
                    "tagId": 7,
                    "tagName": "cricket",
                    "numberOfStories": 0
                }
            ]
        }
    ]
}
```


### GET  `/api/public/tags/{tagName}/story?pageNum={pageNum}&pageSize={pageSize}`

   * tagName : (type : string) 
  
   ### Response Status

Status Code | Message
------------ | -------------
200 | OK


 ### Request Params: 
 
 ``` 
pageNum : NUMBER
pageSize : NUMBER

``` 

 
 
 ### Response Body (200 OK) :

```
{
    "totalNumberOfPages": 1,
    "totalNumberOfStories": 2,
    "stories": [
        {
            "storyId": 9,
            "title": "Cricket is good",
            "body": "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi, cupiditate eaque eveniet ex incidunt iusto nemo quidem quos. Ea fugiat harum id maxime minima qui quisquam quos similique velit voluptatum!",
            "publishedDate": "2020-07-20 04:28:50",
            "scheduledDate": null,
            "drafted": false,
            "scheduled": false,
            "images": "",
            "user": {
                "userId": 6,
                "userName": "tech",
                "fullName": "notAbrar",
                "password": "$2a$10$AKViHh/ChYz3InROkO1CU.uPIURK8YQxzBUY1NVrRRjdyPScMotgK"
            },
            "tags": [
                {
                    "tagId": 7,
                    "tagName": "cricket",
                    "numberOfStories": 0
                }
            ]
        },
        {
            "storyId": 10,
            "title": "Cricket is good",
            "body": "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi, cupiditate eaque eveniet ex incidunt iusto nemo quidem quos. Ea fugiat harum id maxime minima qui quisquam quos similique velit voluptatum!",
            "publishedDate": "2020-07-20 04:28:55",
            "scheduledDate": null,
            "drafted": false,
            "scheduled": false,
            "images": "",
            "user": {
                "userId": 6,
                "userName": "tech",
                "fullName": "notAbrar",
                "password": "$2a$10$AKViHh/ChYz3InROkO1CU.uPIURK8YQxzBUY1NVrRRjdyPScMotgK"
            },
            "tags": [
                {
                    "tagId": 7,
                    "tagName": "cricket",
                    "numberOfStories": 0
                }
            ]
        }
    ]
}
```


# Comment
 
 ### POST  `/api/public/stories/{storyId}/comments`
 * storyId : (type : number)
  ### Request body Scheme: 
  
    {
    userName*		    string
                    nullable: false

    text*		     string                  
                     nullable: false           
    
    }
  
  ### Response Status

Status Code | Message
------------ | -------------
200 | Ok
404 | Not Found


 ### Request Body: 
 
 ``` 
{
    "userName":"TaranComment",
    "text":"This code is ugly!"

}
```
 
 
 ### Response Body (404 Not Found) :

```
{
    No such story found to comment!
}
``` 
### GET  `api/public/stories/{storyId}/comments`

   * storyId : (type : number) 
  
   ### Response Status

Status Code | Message
------------ | -------------
200 | OK
 
  
 ### Response Body (200 OK) :

```
[
    {
        "commentId": 5,
        "userName": "rffff",
        "text": "fff",
        "story": {
            "storyId": 4,
            "title": "aaaa",
            "body": "aaa",
            "publishedDate": "2020-07-20 03:17:19",
            "scheduledDate": null,
            "drafted": false,
            "scheduled": false,
            "images": "",
            "user": {
                "userId": 1,
                "userName": "aaa",
                "fullName": "aaa",
                "password": "$2a$10$xBE5SyzGFDwKKH64hm5r5uVl9l/T0HBL90Reqsyyu3Ry84P8l35Vy"
            },
            "tags": [
                {
                    "tagId": 2,
                    "tagName": "aaa",
                    "numberOfStories": 0
                },
                {
                    "tagId": 3,
                    "tagName": "aa",
                    "numberOfStories": 0
                }
            ]
        }
    },
    {
        "commentId": 11,
        "userName": "sss",
        "text": "sss",
        "story": {
            "storyId": 4,
            "title": "aaaa",
            "body": "aaa",
            "publishedDate": "2020-07-20 03:17:19",
            "scheduledDate": null,
            "drafted": false,
            "scheduled": false,
            "images": "",
            "user": {
                "userId": 1,
                "userName": "aaa",
                "fullName": "aaa",
                "password": "$2a$10$xBE5SyzGFDwKKH64hm5r5uVl9l/T0HBL90Reqsyyu3Ry84P8l35Vy"
            },
            "tags": [
                {
                    "tagId": 2,
                    "tagName": "aaa",
                    "numberOfStories": 0
                },
                {
                    "tagId": 3,
                    "tagName": "aa",
                    "numberOfStories": 0
                }
            ]
        }
    }
]
```

### DELETE  `/api/public/stories/{storyId}}/comments/{commentId}}`
 
  ### Response Status

Status Code | Message
------------ | -------------
200 | Ok
404 | Not Found


 ### Response Body (404 Not Found) :

```
{
    Comment not found!
}
``` 

# Tag
 
 ### GET  `/api/public/tags`
 
  
  ### Response Status

Status Code | Message
------------ | -------------
200 | OK


 
 ### Response Body (200 OK) :

```
[
    {
        "tagId": 2,
        "tagName": "aaa",
        "numberOfStories": 0
    },
    {
        "tagId": 3,
        "tagName": "aa",
        "numberOfStories": 0
    },
    {
        "tagId": 7,
        "tagName": "cricket",
        "numberOfStories": 0
    }
]
``` 


### GET  `/api/public/tags/trending`
 
  
  ### Response Status

Status Code | Message
------------ | -------------
200 | OK

 
 ### Response Body (200 OK) :

```
[
    {
        "tagId": 7,
        "tagName": "cricket",
        "numberOfStories": 2
    },
    {
        "tagId": 3,
        "tagName": "aa",
        "numberOfStories": 1
    },
    {
        "tagId": 2,
        "tagName": "aaa",
        "numberOfStories": 1
    }
]
```

