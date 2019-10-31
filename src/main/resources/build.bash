#!/usr/bin/env bash

echo "Start Download"
echo $1
pwd
rm -rf java-tron # remove original java-tron dir if it exist
echo "clone repo from github"
git clone -b $1 --single-branch https://github.com/tronprotocol/java-tron.git
cd java-tron/
echo "Build java-tron"
pwd
sleep 1
./gradlew build -x test -x check

echo "Build Finish"
