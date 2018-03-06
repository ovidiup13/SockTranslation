[![Build Status](https://travis-ci.org/ovidiup13/SockTranslation.svg?branch=master)](https://travis-ci.org/ovidiup13/SockTranslation)

# SockTranslation

Our super awesome Android translation app.

## Contributions guidelines

Make sure you are on branch master by running:

```
git branch
```

When adding new features/functionality, please create your own branch from master:

```
git checkout -b feature/my-awesome-feature
```

Before you push your changes make sure to update your branch with the latest changes with the following commands:

```
git checkout master
git pull origin master
git checkout feature/my-awesome-feature
git rebase master
```

Make sure to fix any remaining conflicts and that your feature works as expected. Once you have your feature ready and the project does not have any errors, push the branch to origin:

```
git push origin HEAD
```

Make a pull request within Github from your branch to master and add other collaborators as reviewers.

In this way everyone participates to the development of new features and we will avoid annoying conflicting issues.
