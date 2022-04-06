# Java17 sample code

Environment  

- Eclipse Temurin for Windows x64 (jdk-17.0.2+8)(LTS)  
- Eclipse IDE 2022-03 (4.23) for Enterprise Java and Web Developers for Windows x86_64  
  
Add .gitignore file to each Java Project.  
- github / gitignore / Global / Eclipse.gitignore  
[https://github.com/github/gitignore/blob/master/Global/Eclipse.gitignore](https://github.com/github/gitignore/blob/master/Global/Eclipse.gitignore)  
  
Project list  
  
1. [Divide list by specified number.](https://github.com/yvafdevnsk/java11/tree/master/java11-split-list)  
2. [Returns (not view) the portion of srcList between the specified start index, inclusive, and end index, exclusive.](https://github.com/yvafdevnsk/java11/tree/master/java11-sub-list)  

# Eclipse EGit settings

## Create Remote Repository

    github.com/yvafdevnsk  
      Repositories  
        New  
          Repository name: java17  
          [x]Initialize this repository with a README  

## Clone a Git repository

Host: github.com  
Repository path: /yvafdevnsk/java17  
Protocol: https  
  
Destination: /home/mizuki/workspace/github/java17  

## Share Project

    java17-stringbuilder-substring  
      Team  
        Share Project...  
          Configure Git Repository Dialog  
            Repository: java17 - /home/mizuki/workspace/github/java17/.git  
            Working tree: /home/mizuki/workspace/github/java17  

## Commit Local Repository

    Window -> Perspective -> Open Perspective -> Other  
      Git  
  
    java17  
      Git Staging  
        Commit  

## Push Commit

    java17  
      History  
        Push Commit  

# Git user settings

Your Identity ([1.6 Getting Started - First-Time Git Setup](https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup))  
  
    $ git config --global user.name "mizuki"  
    $ git config --global user.email mizuki@localhost  
