# US 3001 - As Candidate, I want to be notified in my application when the state of one of my applications changes

## 1. Context

This Us is related to RCOMP, the candidate should be able to receive notifications when applications state changes.

## 2. Requirements

**3001** As Candidate, I want to be notified in my application when the state of one of my applications changes

**Dependencies/References:**<a id="dependencias"></a>

This user story have some dependencies with the following user stories:


| US                                       | Reason                                                                           |
|------------------------------------------|----------------------------------------------------------------------------------|
| [G007](../../SprintB/g007/readme.md)     | Whe need the authentication to ensure the candidate that is logged               |
| [US1015](../../SprintC/us1015/readme.md) | The process of validate the requirements, can change the state of an application |

## 3. Analysis

### What is asked?
- The user must be able to receive notifications in his application when the state of one of his applications changes. The system should be able to receive the notification at every moment.
- The application has three states:
  - Accepted (when the application passes the requirements validation)
  - Rejected (when the application does not pass the requirements validation)
  - Chosen (when the application is in the rank of the job Opening)

- The candidate app should not be directly connected to the database, but should communicate with a server that will be responsible for sending the notifications.



### Domain model





### Client Clarifications

- The candidate should be notified when his "app" is running. Regarding notifications that "happen" when the application is not running, it would be interesting to receive them the next time he runs the application.

- This US is focused on the Candidate perspective. He/she may not be aware of the internal phases of the recruitment process. But he/she is interested in knowing the “external” state of his/her applications. For instance, as a candidate I would like to know if my application was received. Then I would like to know if my application was accepted or not and, finally, if I was selected or not. 

- An application is accepted if the process of verifying requirements passes. It is chosen if after the ranking is within the places of the vacancies for the job opening.


### How is supposed to work?
 - The candidate when starts the application should have a menu to see notifications, where can read the notifications that he received.


### Dependencies to other user stories
- [Dependencies table](#dependencias)

### Impact in the business
  - The candidate will be able to know the state of his applications in real-time. This is a game-changer for the candidate experience.



## 4. Design
This functionality will be implemented as a daemon that listens on a well-known IP address and port using TCP as the transport mechanism. 
The client application will need to establish a TCP connection to this server socket using the provided IP address or DNS name and port number. After the connection is established, the client and server will exchange messages according to the specified format, maintaining the connection for all data exchanges (requests and responses) while the client application is active. Configuration details, such as the server's IP address and port, will be specified in the client's setup properties file.


This implementation is similar to [US 3002](../us3002/readme.md), but the server will be responsible for sending notifications to the client. The client will be responsible for receiving the notifications and displaying them to the user.


### 5. Use case realization


![use case](SD/sd.svg)

### 4.4. Tests


Tests for Notifications class:
- Test if notification message is for candidate
- Test if notification message is for customer 



## 5. Implementation






## 6. Integration/Demonstration

### Integration


### Demonstration



## 7. Observations

