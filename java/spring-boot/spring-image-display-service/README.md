# Spring Image Display Application

This is a demo application for displaying thumbnail images with captions based on a data format supplied either using a local data file, or
an external service returning the information as text. The format of the data text must be like the example below:
```
# This is an ignored line
# This is a second ignored line
# Data: <URL kommentar>
https://secure.meetupstatic.com/photos/event/d/1/5/1/600_470513585.jpeg React
https://secure.meetupstatic.com/photos/event/d/1/5/1/600_470513585.jpeg <html>

```
The Data section is indicating the table header. The lines below is interpreted as table data. So in this example
we want to load two images with captions and present them on the home web page. 

The page will automatically reload every 30 seconds. When reloading all previous images will be removed. The new ones
will be presented on the page which is partially reloading that section.

## Assumptions

* The image urls that are broken or do not point to an image will be ignored and not presented as a broken link on the web page.

## How to run it
First, you must have JDK 11 installed on your machine.

Then, you have two options:
1. Using maven and command `mvnw.bat spring-boot:run`. This is only if you are using Windows. If you are on Linux then you have to install maven and java 11.
2. java -jar spring-image-display-service-1.0.0-SNAPSHOT.jar if you already have the packaged jar.

Above commands will start a web server listening on http://localhost:8080

### Run using classpath file as data source
Make sure that the property **useExternalRepository** in application.yaml is set to false before starting the application

### Run using external web page as data source.
Make sure that the property **useExternalRepository** in application.yaml is set to false before starting the application.
 

