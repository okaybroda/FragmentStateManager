# FragmentStateManager
An Android library that holds fragment states for BottomNavigationView.

## Installation
Add Jitpack to project level gradle file
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Then add library to module level gradle file
```gradle
dependencies {
  compile 'com.github.okaybroda:FragmentStateManager:1.0.0'
}
```
## Usage
Create a FragmentStateManager instance in the OnCreate function of your Activity
```java
FragmentStateManager fragmentStateManager;

@Override
protected void onCreate(Bundle savedInstanceState) {
    // ... your code ...
    FrameLayout content = findViewById(R.id.content);
    fragmentStateManager = new FragmentStateManager(content, getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new MainFragment();
                    case 1:
                        return new SearchFragment();
                    case 2:
                        return new MeFragment();
                }
            }
        };
}
```
To change current fragment, call
```java
fragmentStateManager.changeFragment(0); // 0 is the index of the fragment
```
Override onSaveInstanceState to save fragment states
```java
@Override
protected void onSaveInstanceState(Bundle outState) {
    outState.putParcelable("fragState", fragmentStateManager.saveState());
    super.onSaveInstanceState(outState);
}
```
Override onRestoreInstanceState to restore fragment states
```java
@Override
protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    fragmentStateManager.restoreState(savedInstanceState.getParcelable("fragState"));
}
```
## Integration with BottomNavigationView
Take a look at the sample cause there's too many things to explain here. Actions speak louder than words ;)
