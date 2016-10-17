# Stories
Stories is a Jenkins plugin and is a validator for checking the quality of a backlog (checking all user stories). 

We want to apply the same principles we apply to code quality to the input (the product backlog). 
With Stories we are able to determine the "coverage" of a backlog. 

Things we meassure:
* Is the user story format used? (As a ... I want ... So ...)
* Does the story include acceptance criteria (Gherkin)? (Given ... when ... then ...)
* Is the story estimated?
* What is the age of story (with a threshold of x week)
* When was the story mutated?
* Are all stories linked to an Epic?


## Getting started
Currently only Jira is supported.

### Prerequisities
* [Maven](https://maven.apache.org/) - Dependency Management
* [Jira](https://jira.atlassian.com) - Issue & Project tracking

## Build
    mvn clean install

## Usage
    java -jar App.jar [PARAMETERS] 

Parameters
* -f path to the file containing the stories (an export from Jira)
* -df the data format used in the above mentioned file. Choose from: {jirajson, jiracsv}
* -d (optional) The delimiter by which the data is separated
* -url (optional) The url to the Jira api
* -pk (optional) the Jira project key
* -a (optional) the Jira authentication key

### Constraints
At the moment there are some constraints on the data. 
In order to function properly:
* User stories should end with a dot (.), and
* Acceptance criteria should end with a dot (.)