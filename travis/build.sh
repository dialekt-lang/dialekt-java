#/bin/sh

# run coverage if necessary
if [ $TRAVIS_JDK_VERSION == $DIALEKT_PUBLISH_VERSION ]; then
    mvn test jacoco:report coveralls:jacoco --errors --batch-mode
else
    mvn test --errors --batch-mode
fi
