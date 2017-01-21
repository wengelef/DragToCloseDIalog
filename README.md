# DragToCloseDialog

Drag Dialog to either Top or Bottom to close

<img src="https://cloud.githubusercontent.com/assets/12090174/22176693/46d63a20-e010-11e6-9dfc-950e038d1f80.gif" width=300></img>

## Usage

Extend ``DragDialogFragment`` and override the abstract methods ``View getContentView()`` and ``int getLayoutResId()``.

``getContentView()`` should return the View that will be dragged, usually the first child of your dialog layout.

``getLayoutResId()`` should return the layout id of your dialog.

#### optional
Override ``getCloseDirection()`` if you wish to control in which direction the Dialog needs to be dragged to close.

Possible values are ``TOP``, ``BOTTOM``. Defaults to ``BOTH``

Check the Samples for possible implementations.
## Download

##### Gradle:

```
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

```
dependencies {
    compile 'com.github.wengelef:DragToCloseDialog:0.2.0'
}
```

##### Maven:

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```
<dependency>
    <groupId>com.github.wengelef</groupId>
    <artifactId>DragToCloseDialog</artifactId>
    <version>0.1.0</version>
</dependency>
```

## License

Copyright 2017 Florian Wengelewski

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.