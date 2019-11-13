#!/bin/bash
path=`pwd`
cd src/main/webapp/ && yarn install && yarn run build
cd $path && mvn clean install
