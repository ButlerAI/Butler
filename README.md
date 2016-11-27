# Butler
An open source project dedicated to advancing home AI's and pushing the boundaries of in-home computing on low-resource devices. We keep all your data in-house, so that the device that's always listening for you never has to send you data anywhere.

## How to get started

### Windows
* Fork the project and clone it!
* Open the project directory, shift-right click, and click Open command window here
* Run the command ```gradlew.bat fetchModelsForLang<lang>``` where \<lang> is your language (en, cz, de, etc.)
* Run the command ```gradlew.bat <ide>```, where \<ide> is eclipse or idea
* Open the project in your IDE of choice

### OS X/Linux
* Fork the project and clone it!
* Open your terminal and cd to the directory where you cloned the project to
* Run the command ```./gradlew fetchModelsForLang<lang>``` where \<lang> is your language (en, cz, de, etc.)
* Run the command ```./gradlew <ide>```, where \<ide> is eclipse or idea
* Open the project in your IDE of choice

## Developing modules
Anyone can develop modules for Butler! Just add a new folder in the modules directory and give it a build.gradle file. It will automatically be loaded in by Gradle and processed as a module. Make sure that it has the engine as a dependency! Check out the sample.build.gradle file in the modules directory.

## Come chat with us!
We have an IRC channel on freenode, #butlerai. Come join us and chat!
