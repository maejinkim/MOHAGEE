# MOHAGEE
Khuveloper
Application
The Mohagee application is a customized scheduling application that recommends the right place to the user based on his / her desired schedule, theme (time, place, location, sub category). It is the goal of the application to ensure that users do not worry much about future schedules.
First, data is collected based on Gangnam Station.

## Detail of Feature
### Basic login & subscription features
<img width="200" alt="메인화면" src="https://user-images.githubusercontent.com/35221733/54988497-ee00ed80-4ff9-11e9-9bbe-01e73c486e65.png">

### Main Screen
Implemented the screen of Instagram. Because of the performance of a server, the quality of images is poor.
It shows the data at the top of the cafe and restaurant at random in the order of the highest rating of the data that the server has. We will provide the name, address, star and photos of each place.

### Search Route
You can get a recommendation by choosing the time, location, the person who you play with, and the major and minor categories.

<img width="200" alt="추천경로 보여주는 place info window" src="https://user-images.githubusercontent.com/35221733/54988601-21dc1300-4ffa-11e9-99f7-b102d2ffa1c8.PNG">


### View by Category
You can see the list of major and minor categories by region. You can see the details of each place by selecting a place for each category. If you only want to set up a location and do not have a place you want to go to, you can use the view by category.

<img width="200" alt="카테고리별 장소 소분류" src="https://user-images.githubusercontent.com/35221733/54988652-3fa97800-4ffa-11e9-912f-646109f237c5.PNG">

### Custom Path
The custom path is the ability to selectively create a route to the desired locations by selecting the list you have added.
If you see the place detail screen, there is a heart button, and when you press that button, the place is added to the custom list.
Select the desired places and press the Create Path button to apply the location and route to the map as you would search for the recommended route.

<img width="200" alt="커스텀 경로 만들기" src="https://user-images.githubusercontent.com/35221733/54988681-4f28c100-4ffa-11e9-9e97-be7bc36ae708.PNG">

### Data Collection
The most important thing in Mohagee is data. There are memory and time limitations in collecting data from all over the country. So we collected most of the places in Gangnam area(in several categories).
First of all, we collected detailed data on the gangnam area, including basic information such as restaurants, places of entertainment, names of cultural elements, address, and classification, theme, atmosphere, purpose of search, and preference for each age group. We collected data using Naver Data Lab, Naver Place, Instagram, and Facebook.
Open source used: Selenium, Jsoup


### Theme Extraction _ Machine learning
Take reviews from each location for each place and extract the Topic for reviews via LDA. At this time, we put the word 'konlpy' into the LDA using a stemming library to prevent the search for 'suffix'.
Afterwards, Word2Vec is used to vectorize the words, and then calculates the distance to the theme word that development team wants. After that, I checked what the most appropriate theme was, and stored it in the DB to provide a place for the theme that the user entered.
Used library: gensim model, konlpy library

### Server
We chose a three-tier model that puts the application server between the client and the database rather than the two-tiered model where the client directly accesses the database. With the application server, the client accesses the application server, making it easy to search and receive the necessary information easily. It also reduces the burden on the client because it first processes the data on the server. SpringBoot was used as a development tool, and RestfulAPI was used to facilitate development convenience by allowing JSON as well as object communication between client and application server.

<img width="350" alt="스크린샷 2019-03-26 오후 7 09 23" src="https://user-images.githubusercontent.com/35221733/54988893-b9d9fc80-4ffa-11e9-86d9-e5f168eeffa0.png">


You can see the demo video on [Youtube](https://youtu.be/WwgLIeJR2jw).




---

README by [joohee](https://github.com/victoriagjh) of mohagee team.


