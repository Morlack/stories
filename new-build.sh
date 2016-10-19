#!/usr/bin/env bash

#build everything
mvn clean install

#package it
cd stories-package
mvn package

echo "Take a look at stories-package/target to see your new build!";
