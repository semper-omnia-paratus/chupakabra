# PowerReviews Project
## Overview
Thank you for your interest in PowerReviews and for taking the time to complete this assignment.  We have provided a Java, spring-boot application that implements initial functionality to allow us to save, update, and view a restaurant.  We would like you to review the project as it currently stands, provide code review feedback on items to improve the quality of the codebase, and extend the application to allow us to collect and retrieve restaurant reviews.
## Code Review Guidelines
Please point out three things that should be improved upon in the existing codebase with a rationale for the feedback.  No updates to this portion of the code base are required as part of this exercise.
## Code Enhancements
### Background
As a SaaS company, I would like to have a web application that can return ratings and reviews for restaurants. A review contains a user's name, comments, a rating and a date. The responses from this application will be consumed by either front-end or server applications.
### Requirements
Provided in this repository are JSON files to represent an initial set of restaurants and users, and code to import these into an in-memory database (H2 has been provided but feel free to use another one if you like).  We would like to add the endpoints to our application:

   - Add a review to a restaurant.
   - Reviews should consist of a rating, comments, user name, and time that the review was submitted. 
   - Return all restaurants sorted from highest to lowest average rating.

Perform data validation on the reviews that are received. Error messages from the application should clearly identify any data constraint/integrity issues:
 - Review comments should not exceed 200 characters and cannot contain any of the following words:
    - lit, hella, chill, bro
 - Ratings should be between 1-5.
 - We should not accept reviews from users with the names Darth Vader and/or AC Slater.
 - Restaurants must exist in the database.
 
## Runtime
The application should support curl requests against any of the API endpoints. Please provide example curl requests with 
the data you used to test/run the application.
Example:

```
bash
curl -X POST "http://localhost:8080/restaurant/1/reviews" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"comments\": \"this is a test review\", \"rating\": 5, \"user\": \"ryan\"}""
curl -X GET "http://localhost:8080/reviews" -H "accept: application/json"
```

Note - we have enabled swagger to auto-generate a testing page.  Assuming the application has been restarted with your changes in place, this can be accessed via http://localhost:8080/swagger-ui.html.

## How to return the project to us
Your code should be version controlled and publicly accessible for us to review (github/bit-bucket/gitlab/etc)
