Welcome to [RSplash]
The project is a draft client of Unsplash API.

[!Disclaimer]
* Since the project hasn't been released, it uses batch of alpha dependencies.
  Also there are no such important production things as Proguard, Keystore with signing configs.
* UX is poor since it is a prototype, on the other side, attributes from theme.xml are used that allow to use both Light and Dark themes.
* The app can behave unexpectedly in Accessibility mode.

[!Important]
* Place your Unsplash Access Key to [key.properties](/key.properties)
* There can be a few solutions that aren't common, the explanation for them are in comments.
* Please use [devDebug] and [prodDebug] build variants.

[!Demo]
Video:
[RSplash-demo.mp4](/outputs/RSplash-demo.mp4)
devDebug *apk:
[app-dev-debug.apk](/outputs/app-dev-debug.apk)

[!TODO]
* Implement paging for Unsplash API.
* Improve UX :) (Even with Jetpack Compose usage)
* HomeFragment hide Fab on RecyclerView scroll.
* Edge to Edge UI.
* Cover at least sensitive codebase by Unit tests.
* Add API error handling.
* ViewModel - add CoroutineExceptionHandler to viewModelScope to handle top level Exceptions.
* Implement Logger.
* Implement ImageLoader which can be provided as dependency.
* Implement Accessibility.
* Add Proguard rules.

[!Known issues:]

[!Tools:]
* MVVM
* Kotlin
* Kotlin Coroutines
* Android Architecture Components
* Navigation Components
* Hilt
* Retrofit 2
* Gson
* Coil
* KtLint

Please feel free to contact author [smetaninvalentyn@gmail.com] in case of any questions.