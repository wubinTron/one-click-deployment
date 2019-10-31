#!/usr/bin/env bash
ssh -p $2 $3@$1 "cd java-tron&& bash start.sh ${4}"