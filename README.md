# WalE

Application which is designed and developed to get the daily Astronomy using NASA GOV API, The user can able to see the astronomy when there is no internet connection, it will data based on Date and last stored item's

## Getting Started

This project is developed using the MVVM Kotlin and Latest Android JetPack Components
- LiveData 
- ViewModel (Holds the data in configuration changes)
- DataStore Preference (where we can able to store the data like key in a safer manner)
- Hilt (Provides the dependencies specific need all over the project / Activity / ViewModel scopes)
- Glide for rendering the Network image and maintain a cache where internet is switched OFF
- Retrofit for making the API call's
- Room (for stroing the data in local)
- coroutines/Flow - used for launching the Heavy API and Database works


## Project structuring
- Common - where all constants and util classes are maintained
- DI - Where all dependencies are provided according to the scopes
- UI - where all UI functionalities are handled
- Model - All data / Entity classes are stored
- Data - contains classes of both remote and local repositories
- Extension - A single file that holds the extension methods for toast, view, snackbar etc...


## Improve Areas

- Our api keys can be stored in the DataStore preference's
- As it is of Single screen functionlaity, otherwise we can use of the Navigation components
- Unit and Integration testcases can be written for Viewmodel and Util classes
- trade-offs due to lack of time (Will scheduled exceution for every day to store the Astronomy using the workmanager)
