#/bin/sh

# fix for bug in OpenJDK 7 preventing publishing to Coveralls
# see https://bugs.launchpad.net/ubuntu/+source/openjdk-7/+bug/1006776
if [ "openjdk7" == $TRAVIS_JDK_VERSION ]; then
    sudo sed --in-place 's/security.provider.9/#security.provider.9/g' $JAVA_HOME/jre/lib/security/java.security
fi

# install the tools required to publish build artifacts
if [ $TRAVIS_JDK_VERSION == $DIALEKT_PUBLISH_VERSION ] && [ $TRAVIS_BRANCH == $DIALEKT_PUBLISH_BRANCH ] && [ "true" == $TRAVIS_SECURE_ENV_VARS ]; then
    echo 'Installing PHP ...'
    sudo apt-get update -qq
    sudo apt-get install -qq php5-cli

    echo 'Installing Woodhouse ...'
    wget http://icecavestudios.github.io/woodhouse/woodhouse
    chmod +x ./woodhouse
fi

mvn install --define skipTests=true --errors --batch-mode
