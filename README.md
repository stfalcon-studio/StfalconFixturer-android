# Stfalcon Fixturer

A Utility for developers and QAs which helps minimize time wasting on writing the same data for testing over and over again. 
You can write fixture in XML one time and use it for build testing. The library can autofill EditText with your fixture data.

### Who we are
Need iOS and Android apps, MVP development or prototyping? Contact us via info@stfalcon.com. We develop software since 2009, and we're known experts in this field. Check out our [portfolio](https://stfalcon.com/en/portfolio) and see more libraries from [stfalcon-studio](https://stfalcon.com/en/opensource).

### Download

Download via Gradle:
```gradle
compile 'com.github.stfalcon:stfalcon-fixturer:0.1.0'
```

### Usage

Create xml file in raw directory of app resources. 
Example: 
```xml
<?xml version="1.0" encoding="utf-8"?>
<fixtures>
    <fixture tag="email" group="account">
        <item>user1@user.com</item>
        <item>user2@user.com</item>
        <item>test@test.com</item>
    </fixture>
    <fixture tag="password" group="account">
        <item>qwerty123</item>
        <item>qwertyQWE</item>
        <item>11111111</item>
    </fixture>
    <fixture tag="name">
        <item>John</item>
        <item>Julia</item>
        <item>Bobby</item>
    </fixture>
    <fixture tag="address">
        <item>108 Greenwich Street, 4/F</item>
        <item>156 William Street</item>
        <item>Proskurivska Street, 11</item>
    </fixture>
</fixtures>
```
All `fixture` require `tag` attribute. This tag will be used for binding input fields to fixture. 
Also you can put some fixtures in groups. For example `email` and `password` can be marked as group `account`. This two fixtures must have the same item count. And in this case where we will select one fixture from group, it will automatically put data to all bound EditTexts to the same grouped fixtures.

To initialize library you have to add this line to your Application `onCreate` method:  
```kotlin
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        StfalconFixturer.init(this, R.raw.fixtures)
    }
}
```
First parameter it's your Application context. 
Second - resource ID of you fixtures XML file.
The default behaviour is Fixturer works only for debug builds. But if you want to change this behaviour you can pass Boolean flag as third parameter.

After that you can bind your EditTexts to the fixtures in your activity(fragment) classes.
Kotlin: 
```kotlin
loginEmailEt.setFixtureTag("email")
```
Java:
```java
StfalconFixturer.get().bindEditText(loginEmailEt, "email")
```
Where `loginEmailEt` is EditText and "email" is tag of the fixture.

Run your application and look on magic :) You can call fixtures dialog by triple tap on bound to fixture EditText.

![alt tag](https://i.imgur.com/KoeGW7E.gif)

Take a look at the [sample project](sample) for more information

### License 

```
Copyright 2018 stfalcon.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[sample]: <https://github.com/stfalcon-studio/StfalconFixturer-android/tree/master/sample>
