# DragDownSlider

A [CRED](https://cred.club) like DragDownSlider library for Android Jetpack Compose.

### Sample App
A Sample App built with MVVM along with tests can be found at [Sample App](/app)

### Installation
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add the dependency
```
dependencies {
	 implementation 'com.github.madhurgupta10:DragDownSlider:1.0.1'
}
```

### Usage
```
val sliderState = rememberSwipeableState(SliderState.Start)
val isDragEnabled by remember { mutableStateOf(true) }
val responseState by remember { mutableStateOf(OnSlideCompleteState.Loading) }

DragDownSlider(
    compactCardSize = 400.dp,
    sliderState = sliderState,
    onSlideCompleteState = responseState,
    isDragEnabled = isDragEnabled,
    onSlideComplete = {
        ...
    }
)

```
<p float="left">
  <img src="https://user-images.githubusercontent.com/30932899/167276004-572c7b10-4338-4a2f-8368-53027ef36990.gif" width="400" style="margin-right:20px"/>
  <span>
  <img src="https://user-images.githubusercontent.com/30932899/167276003-c6805051-5083-4379-bfc2-1c18b8797d7e.gif" width="400" style="margin-right:20px"/>
</p>

