# LwjCommonLib

## 1、Add the JitPack repository to your build file

* 写法一

```java

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

* 写法二

```java
dependencyResolutionManagement {
    
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
        jcenter() // Warning: this repository is going to shut down soon
    }
}
```

## 2、 Add the dependency
```java
dependencies {
    implementation 'com.github.captainGithubAccount:LwjCommonLib:1.0.1'
}
```
