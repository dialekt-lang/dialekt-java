#/bin/sh

# publish API documentation
if [ $TRAVIS_JDK_VERSION == $DIALEKT_PUBLISH_VERSION ] && [ $TRAVIS_BRANCH == $DIALEKT_PUBLISH_BRANCH ] && [ "true" == $TRAVIS_SECURE_ENV_VARS ]; then
    mvn javadoc:javadoc
    ./woodhouse publish --auth-token $WOODHOUSE_TOKEN $TRAVIS_REPO_SLUG target/site/apidocs:artifacts/documentation/api
fi
