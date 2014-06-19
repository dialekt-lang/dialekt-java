#/bin/sh

# publish API documentation
if [ "true" == $DIALEKT_PUBLISH ]; then
    mvn javadoc:javadoc
    ./woodhouse publish --auth-token $WOODHOUSE_TOKEN $TRAVIS_REPO_SLUG target/site/apidocs:artifacts/documentation/api
fi
