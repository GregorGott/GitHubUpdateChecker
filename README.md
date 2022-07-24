# GitHubUpdateChecker

> GitHubUpdateChecker is a small Java library to check the application version by getting the latest release from
> GitHub.

## Requirements

This library checks if the latest release has a bigger version number than the release that the library is running on.
The repository you want to check needs to have at least one (non-pre-release) release!

## Download GitHubUpdateChecker

Currently, GitHubUpdateChecker is only available as downloadable jar. [Download this jar](https://github.com/GregorGott/GitHubUpdateChecker/releases/download/v1.0.0/guc-1.0.0.jar)
and add it to your project by creating a _lib_ folder in your project root and copy the downloaded jar in there.
Then install the package:

    mvn install:install-file -Dfile=projectroot/lib/guc-1.0.0.jar -DgroupId=com.gregorgott -DartifactId=githubupdatechecker -Dversion=1.0.0 -Dpackaging=jar

Afterwards, you can add the dependency to your _pom.xml_ file:

```xml
<dependency>
    <groupId>com.gregorgott</groupId>
    <artifactId>githubupdatechecker</artifactId>
    <version>1.0.0</version>
</dependency>
```

Last, but not least, add _GitHubUpdateChecker_ to your **module-info.java** file:

    requires com.gregorgott.githubupdatechecker

## Let's run it!

Let's use my [Word-Guesser](https://github.com/GregorGott/Word-Guesser) repository as example. The latest release tag
currently (24.07.2022) is version 1.0.0. Let's say we're running Word Guesser version 0.3.5:

```java
public class MyClass() {
    public class MyClass {
        public MyClass() {
            GitHubUpdateChecker guc = new GitHubUpdateChecker("v0.3.5", "GregorGott", "Word-Guesser", new char[]{'.', 'v'});

            boolean updateAvailable = gitHubUpdateChecker.isUpdateAvailable();

            System.out.println(updateAvailable);
        }
    }
}
```

`v0.3.5` is the tag on which the class is running on.

`GregorGott` is the author name of the repository.

`Word-Guesser` is the repository with the release.

`new char[]{'.', 'v'}` are the ignored characters (all non-number characters).

Run it, and you should see `true` in your terminal, because an update to version 1.0.0 is available.

## Ignored Characters.
It is really important, that you add all characters to ignore to a `chars` array, because _GitHubUpdateChecker_ removes
these characters and converts the rest to an integer. And this integer is used to compare the current and latest tag.
