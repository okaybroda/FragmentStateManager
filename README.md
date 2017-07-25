# FragmentStateManager
An Android library that holds fragment states for BottomNavigationView. Saves fragment back stack even after activity rotation. Implementation derived from FragmentStatePagerAdapter.

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
## Integration with BottomNavigationView
Add item selected listener
```java
navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int position = getNavPositionFromMenuItem(item);
        if (position != -1) {
            fragmentStateManager.changeFragment(getNavPositionFromMenuItem(item));
            return true;
        }

        return false;
    }
});
```

(Optional) Add item reselected listener. This will reset the fragment to it's original state
```java
navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        int position = getNavPositionFromMenuItem(item);
        if (position != -1) {
            fragmentStateManager.removeFragment(position);
            fragmentStateManager.changeFragment(position);
        }
    }
});
```
