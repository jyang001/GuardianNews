# GuardianNews
GuardianNews is an Android Application which allows users to view various newspaper articles from the popular British daily newspaper company 'The Guardian'. This app was created for people who want to quickly browse through the headlines of the recent news in the category of their choice, without too much information being displayed. If they are interested in a specific headline, they have the option to view the full article. The application makes calls to the official 'The Guardian' API and parses the retrieved JSON responses to deliver the various newspaper articles.

## Features
* Display articles that were featured in the front page of 'The Guardian'
* Display articles from a specific category
* Redirect the user to the full article on the web browser by pressing the specific article they wish to view
* Change the color theme of the application

## Prerequisites
* Android Studio
* Emulator or Android device which supports minSdkVersion 17 to run the application

## Installation
* Clone the repository with the command
```
$ git clone https://github.com/jyang001/GuardianNews.git
```
* Or you can download the zip file

## Setup
* To use the app you must sign up for an API Key from 'The Guardian' at https://bonobo.capi.gutools.co.uk/register/developer
* You must then open the file with Android Studio and copy the unique API Key you received in the 'MainActivity.java' file
* The 'MainActivity.java' file will direct where you must paste your unique API Key

## Demo
![1](https://user-images.githubusercontent.com/31452709/76037545-59935080-5f15-11ea-96a0-441d7e11b0df.jpg)
![2](https://user-images.githubusercontent.com/31452709/76037578-67e16c80-5f15-11ea-8161-1a0ab07e0655.jpg)

![3](https://user-images.githubusercontent.com/31452709/76037606-7af43c80-5f15-11ea-9a8d-608c54dd799c.jpg)
![4](https://user-images.githubusercontent.com/31452709/76037645-98c1a180-5f15-11ea-9d41-4490dbfd24f9.jpg)

![88](https://user-images.githubusercontent.com/31452709/76037790-e9d19580-5f15-11ea-9adf-b31d3f738860.jpg)
![5](https://user-images.githubusercontent.com/31452709/76037660-a24b0980-5f15-11ea-8d87-ddd223aa0f02.jpg)

## Possible Features in the Future
* Allow users to see the date that each article was published
* Allow users to view articles between a certain date span
* Search feature to view articles with certain keywords as entered by the user

## Contributing
You can submit bug reports, feature requests, and pull requests at [the issue tracker](https://github.com/jyang001/GuardianNews/issues)
