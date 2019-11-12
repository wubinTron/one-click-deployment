#!/bin/bash
path=`pwd`
cd src/main/webapp/ && npm install && npm run build
cd $path && mvn clean install
