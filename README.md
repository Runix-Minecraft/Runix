# Runix

[![Build Status](https://travis-ci.org/Runix-Minecraft/Runix.svg?branch=master)](https://travis-ci.org/Runix-Minecraft/Runix)

Runix is a mod that is is heavily influence and based on [Runecraft] (http://dev.bukkit.org/bukkit-plugins/runecraft/)

## Contributing
To contribute Runix you must:

    1: Have a basic to working knowledge of Java and the Forge api.
    2: Have a basic to working knowledge of how git works.
    3: A GitHub account
    4: Fork the project

### Setup Java
The Java JDK is used to compile Runix.

1. Download and install the Java JDK.
	* [Windows/Mac download link](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html).  Scroll down, accept the `Oracle Binary Code License Agreement for Java SE`, and download it (if you have a 64-bit OS, please download the 64-bit version).
	* Linux: Installation methods for certain popular flavors of Linux are listed below.  If your distribution is not listed, follow the instructions specific to your package manager or install it manually [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html).
		* Gentoo: `emerge dev-java/oracle-jdk-bin`
		* Archlinux: `pacman -S jdk7-openjdk`
		* Ubuntu/Debian: `apt-get install openjdk-7-jdk`
		* Fedora: `yum install java-1.7.0-openjdk`
2. Windows: Set environment variables for the JDK.
    * Go to `Control Panel\System and Security\System`, and click on `Advanced System Settings` on the left-hand side.
    * Click on `Environment Variables`.
    * Under `System Variables`, click `New`.
    * For `Variable Name`, input `JAVA_HOME`.
    * For `Variable Value`, input something similar to `C:\Program Files\Java\jdk1.7.0_51` exactly as shown (or wherever your Java JDK installation is), and click `Ok`.
    * Scroll down to a variable named `Path`, and double-click on it.
    * Append `;%JAVA_HOME%\bin` EXACTLY AS SHOWN and click `Ok`.  Make sure the location is correct; double-check just to make sure.
3. Open up your command line and run `javac`.  If it spews out a bunch of possible options and the usage, then you're good to go.

### Setup Gradle
Gradle is used to execute the various build tasks when compiling Runix.

1. Download and install Gradle.
	* [Windows/Mac download link](http://www.gradle.org/downloads).  You only need the binaries, but choose whatever flavor you want.
		* Unzip the package and put it wherever you want, eg `C:\Gradle`.
	* Linux: Installation methods for certain popular flavors of Linux are listed below.  If your distribution is not listed, follow the instructions specific to your package manager or install it manually [here](http://www.gradle.org/downloads).
		* Gentoo: `emerge dev-java/gradle-bin`
		* Archlinux: You'll have to install it from the [AUR](https://aur.archlinux.org/packages/gradle).
		* Ubuntu/Debian: `apt-get install gradle`
		* Fedora: Install Gradle manually from its website (see above), as Fedora ships a "broken" version of Gradle.  Use `yum install gradle` only if you know what you're doing.
2. Windows: Set environment variables for Gradle.
	* Go back to `Environment Variables` and then create a new system variable.
	* For `Variable Name`, input `GRADLE_HOME`.
	* For `Variable Value`, input something similar to `C:\Gradle-1.11` exactly as shown (or wherever your Gradle installation is), and click `Ok`.
	* Scroll down to `Path` again, and append `;%GRADLE_HOME%\bin` EXACTLY AS SHOWN and click `Ok`.  Once again, double-check the location.
3. Open up your command line and run `gradle`.  If it says "Welcome to Gradle [version].", then you're good to go.

### Setup Git
Git is used to clone Runix and update your local copy.

1. Download and install Git [here](http://git-scm.com/download/).
	* *Optional*: Download and install a Git GUI client, such as Github for Windows/Mac, SmartGitHg, TortoiseGit, etc.  A nice list is available [here](http://git-scm.com/downloads/guis).

### Setup Runix
This section assumes that you're using the command-line version of Git.

1. Open up your command line.
2. Navigate to a place where you want to download Runix's source (eg `C:\Github\Runix\`) by executing `cd [folder location]`.  This location is known as `WorkingDIR` from now on.
3. Execute `git clone https://github.com/Runix-Minecraft/Runix.git`.  This will download Runix's source into `WorkingDIR`.
4. Right now, you should have a directory that looks something like:

***
	WorkingDIR
	\-Runix
		\-Runix's files (should have `build.gradle`)
***

### Compile Runix
1. Execute `gradle setupCiWorkspace`. This sets up Forge and downloads the necessary libraries to build Runix.  This might take some time, be patient.
	* You will generally only have to do this once until the Forge version in `build.properties` changes.
2. Execute `gradle build`. If you did everything right, `BUILD SUCCESSFUL` will be displayed after it finishes.  This should be relatively quick.
    * If you see `BUILD FAILED`, check the error output (it should be right around `BUILD FAILED`), fix everything (if possible), and try again.
3. Navigate to `WorkingDIR\Runix\build\libs`.
    *  You should see a `.jar` file named `Runix-1.7.10-majorversion.minorversion.local.jar`
		* NOTE: The local in the file name means that the jar was not built on our build server
4. Copy the jar

## Pull Request(PR) Guidelines
A PR MUST meet these standards befor being merged:

    1: The PR MUST be singed off by the author
    2: The coding style MUST be uniform with Runix's(LordIllyohs HATES none uniform styles)
    3: The PR MUST pass a Travis-ci build, if it fails odds are it won't be merged.
        -If you think it should still be merged please give us a GOOD reason to do so.

## Links
[Forum Page] (http://www.minecraftforum.net/topic/2259223-wip-runix-the-block-based-magic-mod-inspired-by-runecraft/)

[Wiki Page] (http://runix.wikia.com/wiki/Runix_Wiki)

[Runix's licence] (http://creativecommons.org/licenses/by-nc-sa/4.0/)

