# ShaadiClone

APK - https://drive.google.com/file/d/17rsEY8QyiV8-f9hBtEhTPm-D6ebN8CV9/view?usp=sharing

# Tech Stack Used - Kotlin , XML , Retofit, Dagger , LiveData, Coroutines, RoomDb, Glide, MVVM with Clean Architecture

# Functionalities - Fetch matching profiles from Remote Api,

# 1. If Network Available , sync new data with RoomDB , show Profiles

# 2. If Network not available try to fetch cached data via RoomDB, if present show those profiles, if Not show Error Message wih a toast

# 3. Perform action on Profiles - Accept, Decline , update the same in DB

# In case no data available(remote or local) - show a error Message - clickListener added - to retry fetching profiles , also added a swipeRefreshListener for the same

# Handled error scenarios in case of DB , Network Operations by returning data from local Db or an empty List , which will lead to error message for user so that refreshing can be initiated by the user 

![Shaadi1](https://github.com/anirudh797/ShaadiClone/assets/53581159/bc9a7c63-6d7f-412c-a157-228fe5fb9f2a)
![Shaadi2](https://github.com/anirudh797/ShaadiClone/assets/53581159/c38c5be3-468f-467b-b4b8-e21287fe7c39)
